package bg.adi.travel.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import bg.adi.travel.data.entity.pub.Tag;
import bg.adi.travel.data.repository.pub.TagRepository;
import bg.adi.travel.dto.ListTagDTO;
import bg.adi.travel.dto.LitePage;
import bg.adi.travel.dto.ShortTagDTO;
import bg.adi.travel.service.mapper.TagMapper;
import bg.adi.travel.util.ValidationUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public ShortTagDTO getById(Integer id) {
        Optional<Tag> tag = tagRepository.findById(id);
        ValidationUtil.validate(tag);

        return TagMapper.toShortTagDTO(tag.get());
    }
    
    public List<ListTagDTO> getAllTags() {
        List<Tag> tags = tagRepository.findAllByOrderByIdDesc();

        return tags.stream().map (t -> TagMapper.toListTagDTO(t)).collect(Collectors.toList());
    }
    
    public List<ShortTagDTO> getAllValidTags() {
        List<Tag> tags = tagRepository.findAllByIsActive(true);

        return tags.stream().map (t -> TagMapper.toShortTagDTO(t)).collect(Collectors.toList());
    }
    
    @Transactional
    public void create(ShortTagDTO tag) {
        tagRepository.save(
                Tag.builder()
                   .name(tag.getName())
                   .description(tag.getDescription())
                   .build()
        );
    }
    
    @Transactional
    public void update(Integer id, ShortTagDTO updatedTag) {
    	Optional<Tag> tag = tagRepository.findById(id);
        ValidationUtil.validate(tag);

        tag.get().setName(updatedTag.getName());
        tag.get().setDescription(updatedTag.getDescription());
    }
    
    @Transactional
    public void updateIsActive(Integer id, Boolean isActive) {
    	Optional<Tag> tag = tagRepository.findById(id);
        ValidationUtil.validate(tag);

        tag.get().setIsActive(isActive);
    }
    
    public LitePage<ListTagDTO> findAllPageable(PageRequest pageRequest) {
		Page<Tag> tags = tagRepository.findAll(pageRequest);

		return LitePage.<ListTagDTO>builder()
				.pageIndex(tags.getNumber())
				.pageSize(tags.getSize())
				.totalElements(tags.getTotalElements())
				.results(
						tags.getContent().stream().map (t -> TagMapper.toListTagDTO(t)).collect(Collectors.toList())
				)
				.build();
	}
    
}
