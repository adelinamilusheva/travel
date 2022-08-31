package bg.adi.travel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.adi.travel.dto.WeatherDTO;
import bg.adi.travel.service.WeatherService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {
	private final WeatherService weatherService;
	
    @GetMapping("{lang}/{name}")
    @ApiOperation(value = "Get weather by city", notes = "Get weather by city and language")
    public WeatherDTO getByCityName(@PathVariable String lang, @PathVariable String name) {
        return weatherService.getByCityName(name, lang);
    }
}
