package bg.adi.travel.service.mapper;

import java.util.stream.Collectors;

import bg.adi.travel.data.entity.pub.Group;
import bg.adi.travel.dto.GroupDTO;
import bg.adi.travel.dto.ListGroupDTO;
import bg.adi.travel.dto.MenuGroupDTO;
import bg.adi.travel.dto.MenuSubGroupDTO;
import bg.adi.travel.dto.ShortGroupDTO;

public class GroupMapper {

    public static GroupDTO toGroupDTO(Group entity) {
    	return entity == null ? null : new GroupDTO(
        		entity.getId(),
                entity.getName(),
                entity.getDescription(),
                GroupMapper.toGroupDTO(entity.getParent()),
                entity.getSubgroups().stream().map(sub -> toMenuSubGroupDTO(sub)).collect(Collectors.toList())
        );
    }
    
    public static ListGroupDTO toListGroupDTO(Group entity) {
    	return entity == null ? null : new ListGroupDTO(
        		entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getIsActive()
        );
    } 

    public static MenuGroupDTO toMenuGroupDTO(Group entity) {
        return new MenuGroupDTO(
                entity.getId(),
                entity.getName(),
                entity.getIsActive(),
                entity.getSubgroups().stream().map(sub -> toMenuSubGroupDTO(sub)).collect(Collectors.toList())
        );
    }

    public static ShortGroupDTO toShortGroupDTO(Group entity) {
        return new ShortGroupDTO(
        		entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }

    private static MenuSubGroupDTO toMenuSubGroupDTO(Group entity) {
        return new MenuSubGroupDTO(
                entity.getId(),
                entity.getName()
        );
    }
    
    public static Group toIdOnlyEntity(Integer id) {
		return id == null ? null : Group.builder().id(id).build();
	}
}
