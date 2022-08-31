import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AbstractService } from '../abstract/abstract-service';
import { Rating } from '../data/rating.model';

const URL_RATINGS = environment.apiUrl + '/ratings';
const URL_CREATE_RATING = URL_RATINGS + '/create';

@Injectable({
  providedIn: 'root'
})
export class RatingService extends AbstractService{

  constructor(private http: HttpClient) {
    super();
  }

  isAlreadyRated(id: number, ipAddress: string): Observable<boolean> {   
    let requestParams = new HttpParams();
    requestParams = requestParams.set("ipAddress", ipAddress);

    return this.http.get<boolean>(`${URL_RATINGS}/${id}`, {params: requestParams});
  }

  getAverageRating(id: number): Observable<number> {
    return this.http.get<number>(`${URL_RATINGS}/${id}/average`);
  }

  createRating(rating: Rating): Observable<any> {
    return this.http.post(URL_CREATE_RATING, rating, this.httpOptions).pipe(
      catchError(this.handleError<any>('createRating'))
    );
  }
}
