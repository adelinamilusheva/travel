package bg.adi.travel.data.repository.pub;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import bg.adi.travel.data.entity.pub.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer>, JpaSpecificationExecutor<Tag> {
    List<Tag> findAllByIsActive(Boolean isActive);
    List<Tag> findAllByOrderByIdDesc();
}
