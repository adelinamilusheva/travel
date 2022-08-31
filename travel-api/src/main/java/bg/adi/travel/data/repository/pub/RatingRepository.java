package bg.adi.travel.data.repository.pub;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import bg.adi.travel.data.entity.pub.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer>, JpaSpecificationExecutor<Rating> {
	Optional<Rating> findByPublicationIdAndIpAddress(Integer publicationId, String ipAddress);
	List<Rating> findAllByPublicationId(Integer publicationId);
}
