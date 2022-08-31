package bg.adi.travel.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class WeatherControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void testGetWeatherReturnsCorrectStatusCode() throws Exception {
		this.mockMvc.perform(get("/weather/bg/Sofia")).
			andExpect(status().isOk());
	}
	
	@Test
	void testsReturnsCorrectName() throws Exception {
	    this.mockMvc.
	        perform(get("/weather/bg/Sofia")).
	        andExpect(status().isOk()).
	        andExpect(jsonPath("$.[0].name").value("Sofia"));
	}
}
