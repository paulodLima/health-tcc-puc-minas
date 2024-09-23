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
    return this.http.put<void>(`http://localhost:8080/api/user/${command.id}`, command).pipe(take(1));
  }

  findById(id: string): Observable<UserDto> {
    return this.http.get<UserDto>(`http://localhost:8080/api/user/${id}`).pipe(take(1));
  }

  findAll(): Observable<UserDto[]> {
    return this.http.get<UserDto[]>(`http://localhost:8080/api/user`).pipe(take(1));
  }

  disabled(id: string): Observable<void> {
    return this.http.put<void>(`http://localhost:8080/api/user/${id}/desativar`, null).pipe(take(1));
  }

  enabled(id: string): Observable<void> {
    return this.http.put<void>(`http://localhost:8080/api/user/${id}/ativar`, null).pipe(take(1));
  }
  create(command: CreateUserCommand): Observable<string> {
    console.log(command)
    return this.http.post<string>(`http://localhost:8080/api/user`, command).pipe(take(1));
  }

  resetPassword(command: UpdateUserPasswordCommand): Observable<string> {
    return this.http.put<string>(`http://localhost:8080/api/user`, command).pipe(take(1));
  }
}
