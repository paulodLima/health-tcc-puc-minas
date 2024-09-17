import { Injectable } from '@angular/core';
import {UpdateUserCommand, CreateUserCommand, UserDto, UpdateUserPasswordCommand} from './usuario.interface';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable, take } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  update(command: UpdateUserCommand): Observable<void> {
    const headers = this.getOptionsHeaders();
    console.log(command.id)
    return this.http.put<void>(`http://localhost:8080/api/user/${command.id}`, command,{headers}).pipe(take(1));
  }


  findById(id: string): Observable<UserDto> {
    const headers = this.getOptionsHeaders();
    return this.http.get<UserDto>(`http://localhost:8080/api/user/${id}`,{headers}).pipe(take(1));
  }

  findAll(): Observable<UserDto[]> {
    const headers = this.getOptionsHeaders();
    return this.http.get<UserDto[]>(`http://localhost:8080/api/user`,{headers}).pipe(take(1));
  }

  disabled(id: string): Observable<void> {
    return this.http.put<void>(`http://localhost:8080/api/user/${id}/desativar`, null).pipe(take(1));
  }

  enabled(id: string): Observable<void> {
    return this.http.put<void>(`http://localhost:8080/api/user/${id}/ativar`, null).pipe(take(1));
  }
  create(command: CreateUserCommand): Observable<string> {
    const headers = this.getOptionsHeaders();
    return this.http.post<string>(`http://localhost:8080/api/user`, command,{headers}).pipe(take(1));
  }
  private getOptionsHeaders() {
    const token = localStorage.getItem('access_token') ?? '';
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  resetPassword(command: UpdateUserPasswordCommand): Observable<string> {
    const headers = this.getOptionsHeaders();
    return this.http.put<string>(`http://localhost:8080/api/user`, command,{headers}).pipe(take(1));
  }
}
