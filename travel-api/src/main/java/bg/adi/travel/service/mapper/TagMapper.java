package bg.adi.travel.service.mapper;

import bg.adi.travel.data.entity.pub.Tag;
import bg.adi.travel.dto.ListTagDTO;
import bg.adi.travel.dto.MenuTagDTO;
import bg.adi.travel.dto.ShortTagDTO;

public class TagMapper {

    public static ShortTagDTO toShortTagDTO(Tag entity) {
        return new ShortTagDTO(
        		entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }
    
    public static ListTagDTO toListTagDTO(Tag entity) {
        return new ListTagDTO(
        		entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getIsActive()
        );
    }

    public static MenuTagDTO toMenuTagDTO(Tag entity) {
        return new MenuTagDTO(
                entity.getId(),
                entity.getName()
        );
    }
}
