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
public class ShortTagDTO {
	@Schema(name = "id", example = "6")
	private Integer id;
	
	@Schema(name = "name", example = "China")
    private String name;
	
	@Schema(name = "description", example = "Capital of China is Beijing")
    private String description;
}
