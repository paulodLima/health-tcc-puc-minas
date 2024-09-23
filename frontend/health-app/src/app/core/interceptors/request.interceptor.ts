import { inject } from '@angular/core';
import { HttpInterceptorFn } from '@angular/common/http';
import { switchMap, take } from 'rxjs/operators';
import {CustomOidcSecurityService} from "../../auth/custom-oidc-security-service";

export const requestInterceptor: HttpInterceptorFn = (req, next) => {
  const customOidcSecurityService = inject(CustomOidcSecurityService);

  return customOidcSecurityService.isAuthenticated$.pipe(
    take(1),
    switchMap((isAuthenticated) => {
      if (isAuthenticated) {
        return customOidcSecurityService.getAccessToken().pipe(
          switchMap((token) => {
            if (token) {
              req = req.clone({
                headers: req.headers.set('Authorization', `Bearer ${token}`)
              });
            }
            return next(req);
          })
        );
      } else {
        return next(req);
      }
    })
  );
};
