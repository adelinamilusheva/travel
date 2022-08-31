import { WeatherInfo } from "./weather-info.model";
import { WeatherMain } from "./weather-main.model";
import { WeatherWind } from "./weather-wind.model";

export class Weather {
    name: string;
    weather: WeatherInfo[];
    main: WeatherMain;
    wind: WeatherWind;
}