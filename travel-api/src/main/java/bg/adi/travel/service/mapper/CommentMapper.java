package bg.adi.travel.service.mapper;

import bg.adi.travel.data.entity.pub.Comment;
import bg.adi.travel.dto.ReadCommentDTO;
import bg.adi.travel.util.DateUtil;

public class CommentMapper {

    public static ReadCommentDTO toReadCommentDTO(Comment entity) {
        return new ReadCommentDTO(
                entity.getId(),
                entity.getContent(),
                entity.getPublishedAt().format(DateUtil.DATE_TIME_FORMATTER),
                entity.getIpAddress(),
                entity.getPublication().getId()
                
        );
    }
}
