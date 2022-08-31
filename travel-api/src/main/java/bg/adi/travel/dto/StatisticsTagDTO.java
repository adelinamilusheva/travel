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
public class StatisticsTagDTO {
	@Schema(name = "dates")
	private List<String> dates;
	
	@Schema(name = "tagsReadings")
	private List<StatisticsReadingCountDTO> tagsReadings;
}
