package bg.adi.travel.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import bg.adi.travel.dto.WeatherDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherService {
	private final RestTemplate restTemplate;
	
	@Value("${weatherApiKey}")
    private String apiKey;
	
	private String openWeatherUrl = "http://api.openweathermap.org/data/2.5/weather";
	
    public WeatherDTO getByCityName(String name, String lang) {
    	try {
    		HttpHeaders headers = new HttpHeaders();
    		headers.set("Accept", "application/json");
    		 
    		 UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(openWeatherUrl)
    			        .queryParam("q", name)
    			        .queryParam("lang", lang)
    			        .queryParam("APPID", apiKey)
    			        .queryParam("units", "metric");
    		 
    		 HttpEntity<?> entity = new HttpEntity<Object>(headers);
    		 ResponseEntity<WeatherDTO> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, WeatherDTO.class);

           if (response.getStatusCodeValue() == 200) {
               return response.getBody();
           } else {
               return null;
           }
    	} catch(Exception e) {
    		return null;
    	}

    }
}
