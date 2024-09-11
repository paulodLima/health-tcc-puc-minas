import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AlterarMenuCommand, CriarMenuCommand, MenuDto} from './menu.interfaces';

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  constructor(private http: HttpClient) {
  }

  listar(token: string, userRoles: string[]): Observable<MenuDto[]> {
    const roleName = userRoles.length > 0 ? userRoles[0] : 'defaultRoleName';
    const headers = this.getOptionsHeaders(token);
    console.log(roleName);
    return this.http.get<MenuDto[]>(`http://localhost:8080/api/menu/byRoleName/${roleName}`, {headers});
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

  getOptionsHeaders(token: string): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'GET',
      'Authorization': `Bearer ${token}`
    });
  }
}
