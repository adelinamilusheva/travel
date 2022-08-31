import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AbstractService } from '../abstract/abstract-service';
import { Weather } from '../data/weather.model';

const URL_WEATHER = environment.apiUrl + '/weather';

@Injectable({
  providedIn: 'root'
})
export class WeatherService extends AbstractService {

  constructor(private http: HttpClient) {
    super();
  }

  getByCityName(name: string, lang: string | null): Observable<Weather> { 
    let langParam = lang ? lang : 'bg';
    return this.http.get<Weather>(`${URL_WEATHER}/${langParam}/${name}`);
  }
}
