package bg.adi.travel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bg.adi.travel.data.repository.security.AuthorityRepository;
import bg.adi.travel.dto.auth.AuthorityDTO;
import bg.adi.travel.service.mapper.AuthorityMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthorityService {
	private AuthorityRepository authorityRepository;

	public List<AuthorityDTO> findAll() {
		return authorityRepository
				.findAll()
				.stream()
				.map(a -> AuthorityMapper.toAuthorityDTO(a))
				.collect(Collectors.toList());
	}

}
