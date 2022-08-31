package bg.adi.travel.data.repository.pub;

import bg.adi.travel.data.entity.pub.Reading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadingRepository extends JpaRepository<Reading, Integer>, JpaSpecificationExecutor<Reading> {
    List<Reading> findAllByPublicationId(Integer publicationId);
}
