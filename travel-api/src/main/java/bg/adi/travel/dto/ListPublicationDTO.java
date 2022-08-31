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
public class ListPublicationDTO {
	@Schema(name = "id", example = "2")
	private Integer id;
	
	@Schema(name = "title", example = "The chinese wall")
	private String title;
	
	@Schema(name = "isActive", example = "true")
	private Boolean isActive;
	
	@Schema(name = "publishedAt", example = "2022-05-05 13:12:22")
	private String publishedAt;
	
	@Schema(name = "createdBy", example = "travel")
	private String createdBy;
}
