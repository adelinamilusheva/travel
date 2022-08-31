import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AbstractService } from '../abstract/abstract-service';
import { LoginResponse } from '../data/login-response.model';
import { Login } from '../data/login.model';

const URL_LOGIN = environment.apiUrl + '/auth/a/login';

@Injectable({
  providedIn: 'root'
})
export class AuthService extends AbstractService {

  constructor(private http: HttpClient, public router: Router) {
    super();
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  login(username: string, password: string) {
    var login = new Login(username, password);
    
    return this.http.post<LoginResponse>(URL_LOGIN, login, this.httpOptions).pipe(
      tap(data => {
        localStorage.setItem('auth_token', data.jwtToken);
        localStorage.setItem('roles', data.authorities.join(','));
        localStorage.setItem('username', data.username);

        this.router.navigate(['/home'])
                   .then(() => {
                     window.location.reload();
                   });
      })
    );
  }

  logout() {
    localStorage.removeItem('auth_token');
    localStorage.removeItem('roles');

    this.router.navigate(['/login'])
               .then(() => {
                 window.location.reload();
               });
  }

  getAuthToken(): string {
    const token = localStorage.getItem('auth_token');
    return token ? token : '';
  }

  hasRole(role: string): boolean {
    if(!this.isLoggedIn()) {
      return false;
    }

    let roles =  localStorage.getItem('roles')?.split(',');

    return roles?.includes(role) ? true : false;
  }

  isLoggedIn(): boolean {
   let authToken =  localStorage.getItem('auth_token');
   
   return authToken ? true : false;
  }

  getUsername(): string {
    let username = localStorage.getItem('username');

    return username ? username : '';
  }
}
