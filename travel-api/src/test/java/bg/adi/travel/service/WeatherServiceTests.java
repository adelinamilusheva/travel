package bg.adi.travel.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import bg.adi.travel.dto.WeatherDTO;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTests {
	private WeatherService service;

	@BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new WeatherService(new RestTemplate());
    }
	
	@Test
	public void testGetWeatherForCity() {
		WeatherDTO response = service.getByCityName("Sofia", "bg");

	    Assertions.assertEquals("София", response.getName());
	    Assertions.assertNotNull(response.getMain());
	    Assertions.assertNotNull(response.getWeather());
	    Assertions.assertNotNull(response.getWind());
	}
	
	@Test
	public void testGetWeatherForCityWrongLang() {
		WeatherDTO response = service.getByCityName("Sofia", "bg");

	    Assertions.assertNotEquals("Sofia", response.getName());
	    Assertions.assertNotNull(response.getMain());
	    Assertions.assertNotNull(response.getWeather());
	    Assertions.assertNotNull(response.getWind());
	}
	
}
