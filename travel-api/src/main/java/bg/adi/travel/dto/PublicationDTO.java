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
public class PublicationDTO {
	@Schema(name = "id", example = "1", required = false)
    private Integer id;
	
	@Schema(name = "title", example = "Sofia - the capital of Bulgaria", required = true)
    private String title;
	
	@Schema(name = "subtitle", example = "Sofia is a big city", required = false)
    private String subtitle;
	
	@Schema(name = "content", example = "Sofia is the most populated city in Bulgaria.", required = true)
    private String content;
	
	@Schema(name = "image", example = "u7bZ&Z")
    private String image;
    
    @Schema(name = "publishedAt", example = "2022-05-05 13:12:22", required = true)
    private String publishedAt;
    
	@Schema(name = "createdBy", example = "travel")
    private String createdBy;
    
    @Schema(name = "city", example = "Sofia", required = true)
    private String city;

    @Schema(name = "groups")
    private List<MenuGroupDTO> groups;
    
    @Schema(name = "tags")
    private List<MenuTagDTO> tags;
}
