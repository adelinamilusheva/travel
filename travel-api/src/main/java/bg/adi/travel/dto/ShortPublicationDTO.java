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
public class ShortPublicationDTO {
	@Schema(name = "id", example = "3")
    private Integer id;
	
	@Schema(name = "title", example = "Maldives must be seen")
    private String title;
	
	@Schema(name = "subtitle", example = "Maldives are islands.")
    private String subtitle;
	
	@Schema(name = "image", example = "u7bZ&Z")
    private String image;
    
    @Schema(name = "publishedAt", example = "2022-06-06 13:12:22")
    private String publishedAt;
}
