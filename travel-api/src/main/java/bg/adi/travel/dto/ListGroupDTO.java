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
public class ListGroupDTO {
	@Schema(name = "id", example = "2")
	private Integer id;
	
	@Schema(name = "name", example = "Mountains")
    private String name;
	
	@Schema(name = "description", example = "The mountains are high!")
    private String description;
	
	@Schema(name = "isActive", example = "true")
    private Boolean isActive;
}
