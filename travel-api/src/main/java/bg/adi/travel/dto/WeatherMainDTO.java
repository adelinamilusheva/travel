package bg.adi.travel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherMainDTO {
	@Schema(name = "temp", example = "35", required = true)
    private String temp;
	
	@Schema(name = "feels_like", example = "36", required = true)
    private String feels_like;
	
	@Schema(name = "pressure", example = "29.78", required = true)
    private String pressure;
    
	@Schema(name = "humidity", example = "40", required = true)
    private String humidity;
}
