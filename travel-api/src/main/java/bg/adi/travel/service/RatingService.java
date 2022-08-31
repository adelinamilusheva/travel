package bg.adi.travel.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import bg.adi.travel.data.entity.pub.Publication;
import bg.adi.travel.data.entity.pub.Rating;
import bg.adi.travel.data.repository.pub.PublicationRepository;
import bg.adi.travel.data.repository.pub.RatingRepository;
import bg.adi.travel.dto.RatingDTO;
import bg.adi.travel.util.DoubleUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingService {
	private final RatingRepository ratingRepository;
	private final PublicationRepository publicationRepository;
	
	public Boolean isAlreadyRated(Integer publicationId, String ipAddress) {
		Optional<Rating> rating = ratingRepository.findByPublicationIdAndIpAddress(publicationId, ipAddress);
		
		return !rating.isEmpty();
	}

	public String getAverageRating(Integer publicationId) {
		List<Rating> ratings = ratingRepository.findAllByPublicationId(publicationId);
		
		if(ratings.isEmpty()) {
			return "0.0";
		}
	
		Double averageRating = 0.0;
		
		for(Rating rating : ratings) {
			averageRating += rating.getValue();
		}
		
		return averageRating == 0.0 ? "0.0" : DoubleUtil.DF.format(averageRating  / ratings.size());
	}

	@Transactional
	public void create(RatingDTO ratingDTO) {
		Optional<Publication> publication = publicationRepository.findById(ratingDTO.getPublicationId());
		
		ratingRepository.save(
                Rating.builder()
                   .value(ratingDTO.getValue())
                   .ipAddress(ratingDTO.getIpAddress())
                   .publication(publication.get())
                   .build()
        );
	}
}
