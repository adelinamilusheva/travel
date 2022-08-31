package bg.adi.travel.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import bg.adi.travel.data.entity.pub.Comment;
import bg.adi.travel.data.entity.pub.Publication;
import bg.adi.travel.data.repository.pub.CommentRepository;
import bg.adi.travel.data.repository.pub.PublicationRepository;
import bg.adi.travel.dto.CommentDTO;
import bg.adi.travel.dto.ReadCommentDTO;
import bg.adi.travel.service.mapper.CommentMapper;
import bg.adi.travel.util.ValidationUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final PublicationRepository publicationRepository;
	
	public List<ReadCommentDTO> getAllActiveCommentsByPublication(Integer publicationId) {
		List<Comment> comments = commentRepository.findAllByPublicationIdAndIsActiveOrderByPublishedAtDesc(publicationId, true);
		
		return comments.stream().map (c -> CommentMapper.toReadCommentDTO(c) ).collect(Collectors.toList());
	}

	@Transactional
	public void create(CommentDTO commentDTO) {
		Optional<Publication> publication = publicationRepository.findById(commentDTO.getPublicationId());
		
		commentRepository.save(
                Comment.builder()
                   .content(commentDTO.getContent())
                   .publishedAt(LocalDateTime.now())
                   .ipAddress(commentDTO.getIpAddress())
                   .publication(publication.get())
                   .isActive(true)
                   .build()
        );
	}
	
	@Transactional
    public void update(Long id, CommentDTO updatedComment) {
    	Optional<Comment> comment = commentRepository.findById(id);
        ValidationUtil.validate(comment);

        comment.get().setContent(updatedComment.getContent());
    }

	@Transactional
	public void delete(Long id) {
		Optional<Comment> comment = commentRepository.findById(id);
        ValidationUtil.validate(comment);

        comment.get().setIsActive(false);
	}
}
