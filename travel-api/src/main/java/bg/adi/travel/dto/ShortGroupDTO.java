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
public class ShortGroupDTO {
	@Schema(name = "id", example = "5")
	private Integer id;
	
	@Schema(name = "name", example = "Italy")
    private String name;
	
	@Schema(name = "description", example = "The capital of Italy is Rome", required = true)
    private String description;
}
