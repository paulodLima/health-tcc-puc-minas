import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AlterarMenuCommand, CriarMenuCommand, MenuDto} from './menu.interfaces';

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  desiredRoles: string[] = ["manager", "user", "admin"];
  constructor(private http: HttpClient) {
  }

  listar(token: string, userRoles: string[]): Observable<MenuDto[]> {
    let role = this.getMatchingRole(userRoles);
    console.log(token)
    const headers = this.getOptionsHeaders(token);
    return this.http.get<MenuDto[]>(`http://localhost:8080/api/menu/byRoleName/${role}`, {headers});
  }

  pesquisar(id: string): Observable<MenuDto> {
    return this.http.get<MenuDto>(`http://localhost:8081/api/menus/${id}`);
  }

  criar(command: CriarMenuCommand): Observable<string> {
    return this.http.post<string>(`http://localhost:8081/api/menus`, command);
  }

  alterar(id: string, command: AlterarMenuCommand): Observable<void> {
    return this.http.put<void>(`http://localhost:8081/api/menus/${id}`, command);
  }

  excluir(id: string): Observable<void> {
    return this.http.delete<void>(`http://localhost:8081/api/menus/${id}`);
  }

  getMatchingRole(userRoles: string[]): string {
    return userRoles.find(role => this.desiredRoles.includes(role)) || '';
  }
  getOptionsHeaders(token: string): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'GET',
      'Authorization': `Bearer ${token}`
    });
  }
}
