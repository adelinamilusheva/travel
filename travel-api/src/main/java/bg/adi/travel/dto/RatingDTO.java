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
public class RatingDTO {
	@Schema(name = "value", example = "4", required = true)
	private Integer value;
	
	@Schema(name = "ipAddress", example = "11.11.11.11", required = true)
	private String ipAddress;
	
	@Schema(name = "publicationId", example = "2", required = true)
	private Integer publicationId;
}
