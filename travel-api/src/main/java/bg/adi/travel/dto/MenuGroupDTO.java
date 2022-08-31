package bg.adi.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuGroupDTO {
	@Schema(name = "id", example = "1")
    private Integer id;
	
	@Schema(name = "name", example = "Continent")
    private String name;
	
	@Schema(name = "isActive", example = "true")
	private Boolean isActive;
	
	@Schema(name = "subgroups")
    private List<MenuSubGroupDTO> subgroups;
}
