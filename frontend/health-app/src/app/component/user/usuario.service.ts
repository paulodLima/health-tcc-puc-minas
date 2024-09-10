import { Injectable } from '@angular/core';
import { AlterarUsuarioCommand, CriarUsuarioCommand, UsuarioDto } from './usuario.interface';
import { HttpClient } from '@angular/common/http';
import { Observable, take } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http: HttpClient) { }

  alterar(command: AlterarUsuarioCommand): Observable<void> {
    return this.http.put<void>(`http://localhost:8081/api/usuarios/${command.id}`, command).pipe(take(1));
  }

  criar(command: CriarUsuarioCommand): Observable<void> {
    return this.http.post<void>(`http://localhost:8081/api/usuarios`, command).pipe(take(1));
  }

  pesquisar(id: string): Observable<UsuarioDto> {
    return this.http.get<UsuarioDto>(`http://localhost:8080/api/user/${id}`).pipe(take(1));
  }

  listar(): Observable<UsuarioDto[]> {
    return this.http.get<UsuarioDto[]>(`http://localhost:8081/api/usuarios`).pipe(take(1));
  }

  desativar(id: string): Observable<void> {
    return this.http.put<void>(`http://localhost:8081/api/usuarios/${id}/desativar`, null).pipe(take(1));
  }

  ativar(id: string): Observable<void> {
    return this.http.put<void>(`http://localhost:8081/api/usuarios/${id}/ativar`, null).pipe(take(1));
  }
}
