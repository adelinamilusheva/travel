package bg.adi.travel.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsPublicationDTO {
	@Schema(name = "dates")
	private List<String> dates;
	
	@Schema(name = "publicationsReadings")
	private List<StatisticsReadingCountDTO> publicationsReadings;
}
