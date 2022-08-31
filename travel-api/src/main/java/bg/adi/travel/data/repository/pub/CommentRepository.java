package bg.adi.travel.data.repository.pub;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import bg.adi.travel.data.entity.pub.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
	List<Comment> findAllByPublicationIdAndIsActiveOrderByPublishedAtDesc(Integer publicationId, Boolean isActive);
}
