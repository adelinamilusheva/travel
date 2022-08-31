package bg.adi.travel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import bg.adi.travel.data.entity.security.User;
import bg.adi.travel.data.repository.security.UserRepository;
import bg.adi.travel.dto.auth.AuthorityDTO;
import bg.adi.travel.dto.auth.UserPrincipal;
import bg.adi.travel.service.mapper.AuthorityMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
	private UserRepository userRepository;
	
	@Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<AuthorityDTO> authorities = user.getAuthorities()
        		.stream()
        		.map(a -> AuthorityMapper.toAuthorityDTO(a))
        		.collect(Collectors.toList());
        return new UserPrincipal(user.getUsername(), user.getPassword(), authorities);
    }
	
	public List<UserDetails> findAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDetails> mappedUsers = new ArrayList<>();
		
		users.stream()
			 .forEach(u -> mappedUsers.add(new UserPrincipal(u.getUsername(), null, 
				u.getAuthorities().stream()
				.map(a -> AuthorityMapper.toAuthorityDTO(a))
        		.collect(Collectors.toList()))));
		
		return mappedUsers;
	}
}