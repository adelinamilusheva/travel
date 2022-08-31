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
public class GroupDTO {
	@Schema(name = "id", example = "1", required = false)
	private Integer id;
	
	@Schema(name = "name", example = "Islands", required = true)
    private String name;
	
	@Schema(name = "description", example = "Islands are beautiful.", required = true)
    private String description;
	
	@Schema(name = "parent")
    private GroupDTO parent;
	
	@Schema(name = "subgroups")
    private List<MenuSubGroupDTO> subgroups;
}
