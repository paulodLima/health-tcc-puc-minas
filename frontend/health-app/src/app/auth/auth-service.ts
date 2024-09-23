import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable, tap} from 'rxjs';
import {OidcSecurityService} from "angular-auth-oidc-client";
import {CustomOidcSecurityService} from "./custom-oidc-security-service";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private authUrl = 'http://localhost:7080/realms/health/protocol/openid-connect/token'; // URL do seu endpoint de autenticação

  constructor(private http: HttpClient,private customOidcSecurityService: CustomOidcSecurityService, private router: Router) {}

  login(username: string, password: string): Observable<any> {
    const body = new URLSearchParams();
    body.set('grant_type', 'password');
    body.set('client_id', 'health-api');
    body.set('client_secret', 'MuyShRtqmjBHiurKULLdvOi58cInnnRe');
    body.set('username', username);
    body.set('password', password);
    return this.http.post(this.authUrl, body.toString(), {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    }).pipe(
      tap((response: any) => {
        this.customOidcSecurityService.setAccessToken(response.access_token, response.refresh_token);
        this.router.navigate(['/inicio']);
      })
    );
  }
  logout() {
    // Lógica de logout
    // Exemplo: chamar o endpoint de logout se necessário
  }
}
