package bg.adi.travel.data.specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import bg.adi.travel.data.entity.pub.Reading;

public class ReadingSpecifications {
	  public static Specification<Reading> isReadingFromToday(LocalDateTime startOfDay, LocalDateTime endOfDate) {
	        return (Specification<Reading>) (root, cq, cb) -> {
	            List<Predicate> predicates = new ArrayList<>();
	            
	            predicates.add(cb.lessThanOrEqualTo(root.get("readAt").as(LocalDateTime.class), endOfDate));
	    		predicates.add(cb.greaterThanOrEqualTo(root.get("readAt").as(LocalDateTime.class), startOfDay));

	            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	        };
	    }
}
