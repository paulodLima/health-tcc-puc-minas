import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import {switchMap} from "rxjs";

export const requestInterceptor: HttpInterceptorFn = (req, next) => {
  const oidcSecurityService = inject(OidcSecurityService);

  oidcSecurityService.isAuthenticated$.subscribe(({ isAuthenticated }) => {

    const token = localStorage.getItem('access_token');

    if (token) {
      return oidcSecurityService.getAccessToken().pipe(
        switchMap(token => {
          const clonedReq = req.clone({
            setHeaders: {
              Authorization: `Bearer ${token}`
            }
          });
          return next(clonedReq);
        })
      );
    } else {
      return next(req);
    }
  });

  return next(req);
};
