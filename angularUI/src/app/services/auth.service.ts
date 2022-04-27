import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from "../model/user";

const AUTH_API = 'http://localhost:8080/api/auth/';
const ROLES_API_BASE_URL = 'http://localhost:8080/api/roles/';

const httpOptions: Object = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
  observe: 'response'
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
  }

  login(credentials): Observable<any> {

    return this.http.post(AUTH_API + 'login', {
      username: credentials.username,
      password: credentials.password
    }, httpOptions)
  }

  register(user: User): Observable<any> {
    const captchaResponse = sessionStorage.getItem("captchaResponse");

    return this.http.post(AUTH_API + 'registration?recaptcha-response=' + captchaResponse, {
      username: user.username,
      password: user.password,
      email: user.email,
      firstName: user.firstName,
      lastName: user.lastName,
      birthday: user.birthday

    }, httpOptions);
  }

  getAllRoles(): Observable<any> {

    return this.http.get(ROLES_API_BASE_URL, httpOptions);
  }
}
