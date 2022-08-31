package bg.adi.travel.data.repository.pub;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import bg.adi.travel.data.entity.pub.Group;
import bg.adi.travel.data.entity.pub.Publication;
import bg.adi.travel.data.entity.pub.Tag;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Integer>, JpaSpecificationExecutor<Publication> {
    List<Publication> findAllByGroupsIdOrderByPublishedAtDesc(Integer groupId);
    Page<Publication> findAllByGroupsIdOrderByPublishedAtDesc(Integer groupId, PageRequest pageable);
    
    List<Publication> findAllByTagsIdOrderByPublishedAtDesc(Integer tagId);
    Page<Publication> findAllByTagsIdOrderByPublishedAtDesc(Integer tagId, PageRequest pageable);

    List<Publication> findTop3ByTagsInOrderByPublishedAtDesc(List<Tag> tags);
    List<Publication> findTop3ByGroupsInOrderByPublishedAtDesc(List<Group> groups);
    List<Publication> findTop3ByOrderByPublishedAtDesc();
    
    List<Publication> findAllByOrderByIdDesc();
}
