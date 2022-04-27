import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from "../model/user";
import {TokenStorageService} from "./token-storage.service";

const API_URL = 'http://localhost:8080/api/admin/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) {
  }

  getAllUsers(): Observable<User[]> {
    const httpOptions = {
      headers: new HttpHeaders({'token': this.tokenStorage.getToken()})
    };

    return this.http.get<User[]>(API_URL + 'users', httpOptions);
  }

  deleteUsers(id: number): Observable<Object> {
    const httpOptions = {
      headers: new HttpHeaders({'token': this.tokenStorage.getToken()})
    };

    return this.http.delete(API_URL + 'user/' + id, httpOptions)
  }

  createUser(user: User): Observable<Object> {
    const httpOptions = {
      headers: new HttpHeaders({'token': this.tokenStorage.getToken()})
    };
    return this.http.post(API_URL + 'user/', {
      roleName: user.role.name,
      username: user.username,
      password: user.password,
      email: user.email,
      firstName: user.firstName,
      lastName: user.lastName,
      birthday: user.birthday
    }, httpOptions);
  }

  editUser(user: User): Observable<Object> {
    const httpOptions = {
      headers: new HttpHeaders({'token': this.tokenStorage.getToken()})
    };

    return this.http.put(API_URL + 'user/', {
      id: user.id,
      roleName: user.role.name,
      username: user.username,
      password: user.password,
      email: user.email,
      firstName: user.firstName,
      lastName: user.lastName,
      birthday: user.birthday
    }, httpOptions);
  }
}
