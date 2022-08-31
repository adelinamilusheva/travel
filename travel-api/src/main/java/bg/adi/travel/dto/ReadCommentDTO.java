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
public class ReadCommentDTO {
	@Schema(name = "id", example = "13")
	private Long id;
	
	@Schema(name = "content", example = "Beautiful nature!")
	private String content;
	
	@Schema(name = "publishedAt", example = "2022-05-03 14:12:22")
	private String publishedAt;
	
	@Schema(name = "ipAddress", example = "11.11.11.11")
	private String ipAddress;
	
	@Schema(name = "publicationId", example = "5")
	private Integer publicationId;
}
