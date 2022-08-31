package bg.adi.travel.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import bg.adi.travel.data.entity.pub.Group;
import bg.adi.travel.data.entity.pub.Publication;
import bg.adi.travel.data.entity.pub.Reading;
import bg.adi.travel.data.entity.pub.Tag;
import bg.adi.travel.data.repository.pub.GroupRepository;
import bg.adi.travel.data.repository.pub.PublicationRepository;
import bg.adi.travel.data.repository.pub.TagRepository;
import bg.adi.travel.dto.StatisticsGroupDTO;
import bg.adi.travel.dto.StatisticsPublicationDTO;
import bg.adi.travel.dto.StatisticsReadingCountDTO;
import bg.adi.travel.dto.StatisticsTagDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticsService {
	  private final TagRepository tagRepository;
	  private final GroupRepository groupRepository;
	  private final PublicationRepository publicationRepository;
	  
	  @Transactional
	  public StatisticsTagDTO getAllStatisticsTags() {
	        List<Tag> tags = tagRepository.findAll();
	        
	        StatisticsTagDTO statisticsTag = new StatisticsTagDTO();
	        
	        List<LocalDate> dates = getDatesRange(7);
	        statisticsTag.setDates(dates.stream().map(d -> d.toString()).collect(Collectors.toList()));
	        
	        List<StatisticsReadingCountDTO> tagsReadings = new ArrayList<>();
	        for(Tag tag : tags) {
	        	StatisticsReadingCountDTO tagReading = new StatisticsReadingCountDTO();
	        	tagReading.setLabel(tag.getName());
	        	
	        	List<Long> countReadings = new ArrayList<>();
	        	for(LocalDate date : dates) {
		        	List<Reading> readings = new ArrayList<>();
		        	tag.getPublications().stream().map(p -> p.getReadings()).forEach(r -> readings.addAll(r));
		        	
		        	countReadings.add(readings.stream().filter(r -> r.getReadAt().toLocalDate().isEqual(date)).count());
	        		
	        	}
	        	tagReading.setReadingsCount(countReadings);
	        	tagsReadings.add(tagReading);
	        }
	        
	        statisticsTag.setTagsReadings(tagsReadings);
	        
	        return statisticsTag;
	  }
	  
	  @Transactional
	  public StatisticsPublicationDTO getAllStatisticsPublications() {
	        List<Publication> publications = publicationRepository.findAll();
	        
	        StatisticsPublicationDTO statisticsPublication = new StatisticsPublicationDTO();
	        
	        List<LocalDate> dates = getDatesRange(7);
	        statisticsPublication.setDates(dates.stream().map(d -> d.toString()).collect(Collectors.toList()));
	        
	        List<StatisticsReadingCountDTO> publicationsReadings = new ArrayList<>();
	        for(Publication publication : publications) {
	        	StatisticsReadingCountDTO publicationReading = new StatisticsReadingCountDTO();
	        	publicationReading.setLabel(publication.getTitle());
	        	
	        	List<Long> countReadings = new ArrayList<>();
	        	for(LocalDate date : dates) {
		        	countReadings.add(publication.getReadings().stream().filter(r -> r.getReadAt().toLocalDate().isEqual(date)).count());
	        		
	        	}
	        	publicationReading.setReadingsCount(countReadings);
	        	publicationsReadings.add(publicationReading);
	        }
	        
	        statisticsPublication.setPublicationsReadings(publicationsReadings);
	        
	        return statisticsPublication;
	  }
	  
	  @Transactional
	  public StatisticsGroupDTO getAllStatisticsGroups() {
	        List<Group> groups = groupRepository.findAll();
	        
	        StatisticsGroupDTO statisticsGroup = new StatisticsGroupDTO();
	        
	        List<LocalDate> dates = getDatesRange(7);
	        statisticsGroup.setDates(dates.stream().map(d -> d.toString()).collect(Collectors.toList()));
	        
	        List<StatisticsReadingCountDTO> groupsReadings = new ArrayList<>();
	        for(Group group : groups) {
	        	StatisticsReadingCountDTO groupReading = new StatisticsReadingCountDTO();
	        	groupReading.setLabel(group.getName());
	        	
	        	List<Long> countReadings = new ArrayList<>();
	        	for(LocalDate date : dates) {
		        	List<Reading> readings = new ArrayList<>();
		        	group.getPublications().stream().map(p -> p.getReadings()).forEach(r -> readings.addAll(r));
		        	
		        	countReadings.add(readings.stream().filter(r -> r.getReadAt().toLocalDate().isEqual(date)).count());
	        	}
	        	groupReading.setReadingsCount(countReadings);
	        	groupsReadings.add(groupReading);
	        }
	        
	        statisticsGroup.setGroupsReadings(groupsReadings);
	        
	        return statisticsGroup;
	  }
	  
	  private List<LocalDate> getDatesRange(int days) {
		  LocalDate weekBeforeToday = LocalDate.now().minusDays(days);
	        
	      return IntStream.rangeClosed(1, days)
						  .mapToObj(weekBeforeToday::plusDays)
						  .collect(Collectors.toList());
	  }
}
