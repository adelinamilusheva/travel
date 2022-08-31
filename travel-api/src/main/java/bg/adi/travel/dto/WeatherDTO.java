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
public class WeatherDTO {
	@Schema(name = "name", example = "Sofia", required = true)
    private String name;
	
	@Schema(name = "weather")
    private List<WeatherInfoDTO> weather;
	
	@Schema(name = "main")
    private WeatherMainDTO main;
	
	@Schema(name = "wind")
    private WeatherWindDTO wind;
}
