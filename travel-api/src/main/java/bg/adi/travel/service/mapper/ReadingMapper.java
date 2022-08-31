package bg.adi.travel.service.mapper;

import bg.adi.travel.data.entity.pub.Reading;
import bg.adi.travel.dto.ReadingDTO;
import bg.adi.travel.util.DateUtil;

public class ReadingMapper {

    public static ReadingDTO toReadingDTO(Reading entity) {
        return new ReadingDTO(
                entity.getReaderIp(),
                entity.getReadAt().format(DateUtil.DATE_TIME_FORMATTER)
        );
    }
}
