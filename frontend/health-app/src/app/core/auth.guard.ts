import {inject, Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router, RouterStateSnapshot} from '@angular/router';
import {TokenService} from "./token.service";
import {catchError, map, Observable, of, take} from "rxjs";
import {OidcSecurityService} from "angular-auth-oidc-client";
import {switchMap} from "rxjs/operators";
import {CustomOidcSecurityService} from "../auth/custom-oidc-security-service";

/*@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private tokenService: TokenService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    const roles = route.data['roles'] as string[] | undefined;
    if (this.tokenService.isAuthenticated()) {
      if (Array.isArray(roles) && roles.length > 0) {
        if (roles.some(role => this.tokenService.hasRole(role))) {
          return true;
        } else {
          this.router.navigate(['/inicio']);
          return false;
        }
      } else {
        return true;
      }
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}*/

export const AuthGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const customOidcSecurityService = inject(CustomOidcSecurityService);
  const router = inject(Router);

  return customOidcSecurityService.isAuthenticated$.pipe(
    take(1),
    map((isAuthenticated) => {
      if (isAuthenticated) {
        return true;
      }
      router.navigate(['/login']);
      return false;
    })
  );

};
