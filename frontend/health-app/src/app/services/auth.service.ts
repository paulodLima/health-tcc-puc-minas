import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, take} from "rxjs";
import {UpdateUserPasswordCommand} from "../login/reset-password/reset-password.interface";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/user';

  constructor(private http: HttpClient) {
  }

  resetPassword(id: string, newPassword: string): Observable<any> {
    const command = {
      password: newPassword,
      id: id
    };
    return this.http.put<void>(`${this.apiUrl}/reset-password`, command).pipe(take(1));
  }
}
