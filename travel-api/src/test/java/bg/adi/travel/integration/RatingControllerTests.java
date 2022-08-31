package bg.adi.travel.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import bg.adi.travel.data.entity.pub.Publication;
import bg.adi.travel.data.entity.pub.Rating;
import bg.adi.travel.data.repository.pub.RatingRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class RatingControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private RatingRepository ratingRepository;
	
	private Rating rating;
	
	@BeforeEach
	public void setUp() {
		rating = new Rating();
		rating.setValue(2);
		rating.setIpAddress("0.0.0.0");
		
		Publication pub = new Publication();
		pub.setId(1);
		rating.setPublication(pub);
		rating = ratingRepository.save(rating);
	}

	@AfterEach
	public void tearDown() {
		ratingRepository.deleteAll();
	}
	
	@Test
	void testIsAlreadyRated() throws Exception {
		this.mockMvc.perform(get("/ratings/1")).
			andExpect(status().isOk());
	}
	
	@Test
	void testGetAverageRating() throws Exception {
	    this.mockMvc.
	        perform(get("/weather/bg/Sofia")).
	        andExpect(status().isOk());
	}
}
