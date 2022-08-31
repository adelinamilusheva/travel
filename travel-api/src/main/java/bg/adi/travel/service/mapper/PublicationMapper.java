package bg.adi.travel.service.mapper;

import java.util.stream.Collectors;

import bg.adi.travel.data.entity.pub.Publication;
import bg.adi.travel.dto.ListPublicationDTO;
import bg.adi.travel.dto.PublicationDTO;
import bg.adi.travel.dto.ShortPublicationDTO;
import bg.adi.travel.util.DateUtil;

public class PublicationMapper {

    public static ShortPublicationDTO toShortPublicationDTO(Publication entity) {
        return new ShortPublicationDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getSubtitle(),
                entity.getMainImage(),
                entity.getPublishedAt().format(DateUtil.DATE_TIME_FORMATTER)
        );
    }

    public static PublicationDTO toPublicationDTO(Publication entity) {
        return new PublicationDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getSubtitle(),
                entity.getContent(),
                entity.getMainImage(),
                entity.getPublishedAt().format(DateUtil.DATE_TIME_FORMATTER),
                entity.getCreatedBy().getUsername(),
                entity.getCity() != null ? entity.getCity().getName() : null,
                entity.getGroups().stream().map (g -> GroupMapper.toMenuGroupDTO(g)).collect(Collectors.toList()),
                entity.getTags().stream().map (t -> TagMapper.toMenuTagDTO(t) ).collect(Collectors.toList())
        );
    }
    
    public static ListPublicationDTO toListPublicationDTO(Publication entity) {
    	return entity == null ? null : new ListPublicationDTO(
        		entity.getId(),
                entity.getTitle(),
                entity.getIsActive(),
                entity.getPublishedAt().format(DateUtil.DATE_TIME_FORMATTER),
                entity.getCreatedBy().getUsername()
        );
    } 
}
