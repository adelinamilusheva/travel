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
public class ListTagDTO {
	@Schema(name = "id", example = "2")
	private Integer id;
	
	@Schema(name = "name", example = "Japan")
    private String name;
	
	@Schema(name = "description", example = "Japan is a big country")
    private String description;
	
	@Schema(name = "isActive", example = "true")
    private Boolean isActive;
}
