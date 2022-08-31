package bg.adi.travel.service.mapper;

import bg.adi.travel.data.entity.pub.Tag;
import bg.adi.travel.data.entity.security.Authority;
import bg.adi.travel.dto.ShortTagDTO;
import bg.adi.travel.dto.auth.AuthorityDTO;

public class AuthorityMapper {

	 public static AuthorityDTO toAuthorityDTO(Authority entity) {
	        return new AuthorityDTO(
	        		entity.getId(),
	                entity.getName()
	        );
	    }
}
