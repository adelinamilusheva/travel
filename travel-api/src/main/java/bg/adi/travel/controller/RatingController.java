package bg.adi.travel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bg.adi.travel.dto.RatingDTO;
import bg.adi.travel.service.RatingService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ratings")
public class RatingController {
	private final RatingService ratingService;

    @GetMapping("/{publicationId}")
    @ApiOperation(value = "Check if publication is already rated", notes = "Check if publication is already rated by publication id and ip address.")
    public Boolean isAlreadyRated(@PathVariable Integer publicationId, @RequestParam String ipAddress) {
        return ratingService.isAlreadyRated(publicationId, ipAddress);
    }
    
    @GetMapping("/{publicationId}/average")
    @ApiOperation(value = "Get average rating of publication", notes = "Get average rating of publication by publication id")
    public String getAverageRating(@PathVariable Integer publicationId) {
        return ratingService.getAverageRating(publicationId);
    }
    
    @PostMapping("/create")
    @ApiOperation(value = "Create rating", notes = "Create rating")
    public ResponseEntity<Void> create(@RequestBody RatingDTO ratingDTO) {
    	ratingService.create(ratingDTO);
        return ResponseEntity.status(201).build();
    }
}
