import {inject, Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router, RouterStateSnapshot} from '@angular/router';
import {TokenService} from "./token.service";
import {catchError, map, Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private tokenService: TokenService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    const roles = route.data['roles'] as string[] | undefined;
    console.log(roles)
    if (this.tokenService.isAuthenticated()) {
      if (Array.isArray(roles) && roles.length > 0) {
        if (roles.every(role => this.tokenService.hasRole(role))) {
          return true;
        } else {
          this.router.navigate(['/access-denied']);
          return false;
        }
      } else {
        return true; // Não requer roles específicas
      }
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
