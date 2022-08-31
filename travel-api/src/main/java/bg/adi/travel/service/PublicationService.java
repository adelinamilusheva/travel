package bg.adi.travel.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import bg.adi.travel.data.entity.pub.Group;
import bg.adi.travel.data.entity.pub.Publication;
import bg.adi.travel.data.entity.pub.Reading;
import bg.adi.travel.data.entity.pub.Tag;
import bg.adi.travel.data.entity.security.User;
import bg.adi.travel.data.repository.pub.GroupRepository;
import bg.adi.travel.data.repository.pub.PublicationRepository;
import bg.adi.travel.data.repository.pub.ReadingRepository;
import bg.adi.travel.data.repository.pub.TagRepository;
import bg.adi.travel.data.repository.security.UserRepository;
import bg.adi.travel.dto.ListPublicationDTO;
import bg.adi.travel.dto.LitePage;
import bg.adi.travel.dto.ManipulatePublicationDTO;
import bg.adi.travel.dto.PublicationDTO;
import bg.adi.travel.dto.ReadingDTO;
import bg.adi.travel.dto.ReadingStatsDTO;
import bg.adi.travel.dto.ShortPublicationDTO;
import bg.adi.travel.service.mapper.PublicationMapper;
import bg.adi.travel.service.mapper.ReadingMapper;
import bg.adi.travel.util.ValidationUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicationService {
    private final NotificationService notificationService;
    private final PublicationRepository publicationRepository;
    private final ReadingRepository readingRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final TagRepository tagRepository;

    public PublicationDTO getById(Integer id) {
        Optional<Publication> publication = publicationRepository.findById(id);
        ValidationUtil.validate(publication);

        return PublicationMapper.toPublicationDTO(publication.get());
    }

    public List<ShortPublicationDTO> getLatest(Integer size) {
        Page<Publication> publications = publicationRepository.
                findAll(PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "publishedAt")));

        return publications.getContent().stream()
                .map(p -> PublicationMapper.toShortPublicationDTO(p)).collect(Collectors.toList());
    }

    public ReadingStatsDTO getReadings(Integer id) {
        List<ReadingDTO> readings = readingRepository.findAllByPublicationId(id).stream()
                .map (r -> ReadingMapper.toReadingDTO(r)).collect(Collectors.toList());

        ReadingStatsDTO stats = new ReadingStatsDTO(
                readings.size(),
                readings.stream().map(r -> r.getReaderIp()).distinct().collect(Collectors.toList()).size(),
                readings
        );

        return stats;
    }
    
    public List<ListPublicationDTO> getAllPublications() {
        List<Publication> publications = publicationRepository.findAllByOrderByIdDesc();

        return publications.stream().map (g -> PublicationMapper.toListPublicationDTO(g)).collect(Collectors.toList());
    }

    @Transactional
    public void create(ManipulatePublicationDTO publication) {
        Optional<User> createdBy = userRepository.findById(1); //TODO: security

        List<Group> groups = publication.getGroups().stream()
                .map(g -> groupRepository.findById(g.getId()).get()).collect(Collectors.toList());

        List<Tag> tags = publication.getTags().stream()
                .map(t -> tagRepository.findById(t.getId()).get()).collect(Collectors.toList());

        ValidationUtil.validate(createdBy);

        Publication newPublication = publicationRepository.save(
                Publication.builder()
                            .title(publication.getTitle())
                            .subtitle(publication.getSubtitle())
                            .mainImage(publication.getImage())
                            .content(publication.getContent())
                            .publishedAt(LocalDateTime.now())
                            .createdBy(createdBy.get())
                            .groups(groups)
                            .tags(tags)
                            .build()
        );

//        notificationService.sendNewPublication(
//                new ShortPublicationDTO(
//                        newPublication.getId(),
//                        newPublication.getTitle(),
//                        newPublication.getSubtitle(),
//                        newPublication.getMainImage(),
//                        newPublication.getPublishedAt().format(DateUtil.DATE_TIME_FORMATTER)
//                )
//        );
    }

    @Transactional
    public void update(Integer id, ManipulatePublicationDTO updatedPublication) {
        Optional<Publication> publication = publicationRepository.findById(id);
        ValidationUtil.validate(publication);
        
        List<Group> groups = updatedPublication.getGroups().stream()
                .map(g -> groupRepository.findById(g.getId()).get()).collect(Collectors.toList());

        List<Tag> tags = updatedPublication.getTags().stream()
                .map(t -> tagRepository.findById(t.getId()).get()).collect(Collectors.toList());

        publication.get().setTitle(updatedPublication.getTitle());
        publication.get().setSubtitle(updatedPublication.getSubtitle());
        publication.get().setMainImage(updatedPublication.getImage());
        publication.get().setContent(updatedPublication.getContent());
        publication.get().setGroups(groups);
        publication.get().setTags(tags);
    }

    public List<ShortPublicationDTO> getSimilarById(Integer id) {
        Optional<Publication> publication = publicationRepository.findById(id);
        ValidationUtil.validate(publication);

        List<Publication> similar = getSimilarByTags(publication.get());
        if (similar == null) {
            similar = getSimilarByGroups(publication.get());

            if (similar == null)
                similar = getRandomSimilar();
        } else if (similar.size() < 3) {
            List<Publication> similarByGroup = getSimilarByGroups(publication.get());
            if (similarByGroup != null) {}
                //similar = similar.union(similarByGroup); // TODO: union
        }

        if (similar.size() <= 3) {
           return similar.stream()
                   .map(p -> PublicationMapper.toShortPublicationDTO(p)).collect(Collectors.toList());
        } else {
            return similar.stream()
                    .map(p -> PublicationMapper.toShortPublicationDTO(p)).collect(Collectors.toList()).subList(0, 3);
        }
    }

    public List<ShortPublicationDTO> getByGroupId(Integer groupId) {
        List<Publication> publications = publicationRepository.findAllByGroupsIdOrderByPublishedAtDesc(groupId);

        return publications.stream()
                .map(p -> PublicationMapper.toShortPublicationDTO(p)).collect(Collectors.toList());
    }

    public List<ShortPublicationDTO> getByTagId(Integer tagId) {
        List<Publication> publications = publicationRepository.findAllByTagsIdOrderByPublishedAtDesc(tagId);

        return publications.stream()
                .map(p -> PublicationMapper.toShortPublicationDTO(p)).collect(Collectors.toList());
    }

    @Transactional
    public void addReading(Integer id, String ip) {
        Optional<Publication> publication = publicationRepository.findById(id);
        ValidationUtil.validate(publication);

        readingRepository.save(
                Reading.builder()
                        .publication(publication.get())
                        .readerIp(ip)
                        .readAt(LocalDateTime.now())
                        .build()
        );
    }

    private List<Publication> getSimilarByTags(Publication publication) {
        return publicationRepository.findTop3ByTagsInOrderByPublishedAtDesc(publication.getTags());
    }

    private List<Publication> getSimilarByGroups(Publication publication) {
        return publicationRepository.findTop3ByGroupsInOrderByPublishedAtDesc(publication.getGroups());
    }

    private List<Publication> getRandomSimilar() {
        return publicationRepository.findTop3ByOrderByPublishedAtDesc();
    }
    
    @Transactional
    public void updateIsActive(Integer id, Boolean isActive) {
    	Optional<Publication> publication = publicationRepository.findById(id);
        ValidationUtil.validate(publication);

        publication.get().setIsActive(isActive);
    }

	public LitePage<ListPublicationDTO> findAllPageable(PageRequest pageRequest) {
		Page<Publication> publications = publicationRepository.findAll(pageRequest);

		return LitePage.<ListPublicationDTO>builder()
				.pageIndex(publications.getNumber())
				.pageSize(publications.getSize())
				.totalElements(publications.getTotalElements())
				.results(
						publications.getContent().stream().map (p -> PublicationMapper.toListPublicationDTO(p)).collect(Collectors.toList())
				)
				.build();
	}

	public LitePage<ShortPublicationDTO> findAllPageableByGroup(Integer groupId, PageRequest pageRequest) {
		Page<Publication> publications = publicationRepository.findAllByGroupsIdOrderByPublishedAtDesc(groupId, pageRequest);

		return LitePage.<ShortPublicationDTO>builder()
				.pageIndex(publications.getNumber())
				.pageSize(publications.getSize())
				.totalElements(publications.getTotalElements())
				.results(
						publications.getContent().stream().map (p -> PublicationMapper.toShortPublicationDTO(p)).collect(Collectors.toList())
				)
				.build();
	}
	
	public LitePage<ShortPublicationDTO> findAllPageableByTag(Integer tagId, PageRequest pageRequest) {
		Page<Publication> publications = publicationRepository.findAllByTagsIdOrderByPublishedAtDesc(tagId, pageRequest);

		return LitePage.<ShortPublicationDTO>builder()
				.pageIndex(publications.getNumber())
				.pageSize(publications.getSize())
				.totalElements(publications.getTotalElements())
				.results(
						publications.getContent().stream().map (p -> PublicationMapper.toShortPublicationDTO(p)).collect(Collectors.toList())
				)
				.build();
	}
}
