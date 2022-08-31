package bg.adi.travel.dto.auth;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse implements Serializable {
	private static final long serialVersionUID = 5022733775368733609L;

	private String username;
	private List<String> authorities;
	private String jwtToken;
}
