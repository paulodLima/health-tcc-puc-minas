import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";

export interface User {
  roles: string[];
}

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  private currentUserSubject: BehaviorSubject<User | null>;
  private _authenticated = new BehaviorSubject<boolean>(false);
  roles: string[] = []; // Mude para um array se estiver lidando com múltiplas roles
  token: string = '';

  constructor(private router: Router, private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User | null>(null);
    const token = localStorage.getItem('access_token') ?? '';
    this._authenticated.next(!!token);
    if(token)
      this.getRoles(token);
  }

  setRoles(roles: string[]): void {
    this.roles = roles;
  }

  hasRole(role: string): boolean {
    return this.roles.includes(role);
  }

  setToken(token: string): void {
    this.token = token;
  }

  getToken(): string {
    return this.token;
  }

  isAuthenticated(): boolean {
    return this._authenticated.getValue();
  }

  setAuthenticated(authenticated: boolean): void {
    this._authenticated.next(authenticated);
  }
  logout() {
    localStorage.removeItem('access_token');
    this._authenticated.next(false);
    this.router.navigate(['/login']).then(() => {
      window.location.reload();
    });
  }
  private getRoles(token: string): void {
    const tokenPayload = JSON.parse(atob(token.split('.')[1]));
    const role = tokenPayload.resource_access?.['health-api']?.roles || '';
    this.setRoles(role);
  }
  public getRolesUser(token: string): string[] {
    this.getRoles(token)
    return this.roles;
  }

}
