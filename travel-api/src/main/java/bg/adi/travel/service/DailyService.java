package bg.adi.travel.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import bg.adi.travel.config.Const;
import bg.adi.travel.data.entity.pub.Reading;
import bg.adi.travel.data.repository.pub.ReadingRepository;
import bg.adi.travel.data.specification.ReadingSpecifications;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyService {
	private final MailingService	mailingService;
	private final ReadingRepository readingRepository;
	
	@Scheduled(cron = Const.EVERYDAY_12_10_AM)
	@Transactional
	public void getAllPublicationsWithReadings() {
		LocalDate currentdate = LocalDate.now();
		LocalDateTime startOfDay = currentdate.atStartOfDay();
		LocalDateTime endOfDay = currentdate.atTime(LocalTime.MAX);
		
		List<Reading> readings = readingRepository.findAll(ReadingSpecifications.isReadingFromToday(startOfDay, endOfDay));
	
		Map<String, List<Reading>> readingsPerPublication = readings.stream()
				  .collect(Collectors.groupingBy(reading -> reading.getPublication().getTitle()));
		
		mailingService.send(List.of(Const.ADMIN_EMAIL), getNotificationSubject(), 
				getNotificationContent(new ArrayList<>(readingsPerPublication.entrySet())));
	}
	
	private String getNotificationSubject() {
        return "Дневна статистика за публикации в Travel.com";
    }

    private String getNotificationContent(ArrayList<Entry<String, List<Reading>>> readingsPerPublication) {
    	StringBuilder builder = new StringBuilder();
    	
    	readingsPerPublication.sort(Comparator.comparingInt(e -> e.getValue().size()));
    	Collections.reverse(readingsPerPublication);
    	
    	for (Map.Entry<String, List<Reading>> entry : readingsPerPublication) {
    	   builder.append(entry.getKey() + ": " + entry.getValue().size());
    	   builder.append("\n");
    	}

        return builder.toString();
    }
}
