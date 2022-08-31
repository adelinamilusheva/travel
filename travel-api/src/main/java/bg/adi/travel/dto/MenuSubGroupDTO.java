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
public class MenuSubGroupDTO {
	@Schema(name = "id", example = "1")
    private Integer id;
    
    @Schema(name = "name", example = "Country")
    private String name;
}
