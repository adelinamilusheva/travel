package bg.adi.travel.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LitePage<T> {
	private List<T> results;
	private Integer pageSize;
	private Integer pageIndex;
	private Long totalElements;
}
