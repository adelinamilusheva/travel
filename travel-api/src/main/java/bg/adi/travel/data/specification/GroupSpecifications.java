package bg.adi.travel.data.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import bg.adi.travel.data.entity.pub.Group;

public class GroupSpecifications {

    public static Specification<Group> isActiveTopLevelGroup() {
        return (Specification<Group>) (root, cq, cb) -> {
        	List<Predicate> predicates = new ArrayList<>();
	            
	        predicates.add(cb.equal(root.get("isActive"), true));
	    	predicates.add(cb.isNull(root.get("parent")));
	            
	    	cq.orderBy(cb.asc(root.get("id")));
	        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}
