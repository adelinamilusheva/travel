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
public class ReadingStatsDTO {
	@Schema(name = "total", example = "20")
    private Integer total;
	
	@Schema(name = "unique", example = "3")
    private Integer unique;
    
	@Schema(name = "readings")
    private List<ReadingDTO> readings;
}
