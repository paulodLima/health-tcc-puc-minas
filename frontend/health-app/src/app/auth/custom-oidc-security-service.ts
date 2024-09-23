import { Injectable } from '@angular/core';
import {BehaviorSubject, map, Observable, of, tap} from 'rxjs';
import {OidcSecurityService} from "angular-auth-oidc-client";

@Injectable({
  providedIn: 'root'
})
export class CustomOidcSecurityService {
  private accessTokenSubject: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(null);

  constructor(private oidcSecurityService: OidcSecurityService) {}

  setAccessToken(token: string, refreshtoken: string) {
    localStorage.setItem('access_token', token);
    this.accessTokenSubject.next(token);
  }

  getAccessToken(): Observable<string | null> {
    const token = localStorage.getItem('access_token');
    this.accessTokenSubject.next(token);
    return of(token);
  }

  get isAuthenticated$(): Observable<boolean> {
    return this.accessTokenSubject.asObservable().pipe(
      map(token => !!token)
    );
  }
}
