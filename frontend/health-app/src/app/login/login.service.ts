import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {LoginRecord} from "./login.interfaces";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  constructor(private http: HttpClient) {
  }

  login(record: LoginRecord): Observable<any> {
    return this.http.post<any>(`http://localhost:8080/api/user/login`, record, { responseType: 'text' as 'json' });
  }
}
