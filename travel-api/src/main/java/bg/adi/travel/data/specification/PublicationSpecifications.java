package bg.adi.travel.data.specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import bg.adi.travel.data.entity.pub.Publication;


public class PublicationSpecifications {
	  public static Specification<Publication> isReadingFromToday(LocalDateTime startOfDay, LocalDateTime endOfDate) {
	        return (Specification<Publication>) (root, cq, cb) -> {
	            List<Predicate> predicates = new ArrayList<>();
	            Join<?, ?> publicationReadingsJoin = root.join("readings", JoinType.LEFT);
	            
	            predicates.add(cb.lessThanOrEqualTo(publicationReadingsJoin.get("readAt").as(LocalDateTime.class), endOfDate));
	    		predicates.add(cb.greaterThanOrEqualTo(publicationReadingsJoin.get("readAt").as(LocalDateTime.class), startOfDay));
	            
	            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	        };
	    }
}
