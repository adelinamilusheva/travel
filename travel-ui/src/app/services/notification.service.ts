import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AbstractService } from '../abstract/abstract-service';

const URL_NOTIFICATIONS = environment.apiUrl + '/notifications';
const URL_NOTIFICATIONS_SUBSCRIBE = URL_NOTIFICATIONS + '/subscribe';

@Injectable({
  providedIn: 'root'
})
export class NotificationService extends AbstractService {

  constructor(private http: HttpClient) {
    super();
  }

  subscribe(email: string): Observable<any> {
    return this.http.post(URL_NOTIFICATIONS_SUBSCRIBE, email, this.httpOptions).pipe(
      catchError(this.handleError<any>('subscribe'))
    );
  }

}
