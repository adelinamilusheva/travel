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
public class WeatherInfoDTO {
	@Schema(name = "main", example = "Clear", required = true)
    private String main;
	
	@Schema(name = "description", example = "Clear sky", required = true)
    private String description;
	
	@Schema(name = "icon", example = "01d", required = true)
    private String icon;
}
