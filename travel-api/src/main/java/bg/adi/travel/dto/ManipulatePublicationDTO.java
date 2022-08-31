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
public class ManipulatePublicationDTO {
	@Schema(name = "title", example = "Bali - The island of dreams", required = true)
    private String title;
	
	@Schema(name = "subtitle", example = "The island of dreams is here", required = false)
    private String subtitle;
	
	@Schema(name = "image", example = "u7bZ&Z", required = true)
    private String image;
    
    @Schema(name = "content", example = "You should visit Bali. It is amazing!", required = true)
    private String content;

    @Schema(name = "groups")
    private List<GroupDTO> groups;
    
    @Schema(name = "tags")
    private List<ShortTagDTO> tags;
}
