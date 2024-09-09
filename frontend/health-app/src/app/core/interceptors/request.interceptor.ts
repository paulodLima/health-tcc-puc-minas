import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';

export const requestInterceptor: HttpInterceptorFn = (req, next) => {
  const oidcSecurityService = inject(OidcSecurityService);

  oidcSecurityService.isAuthenticated$.subscribe(({ isAuthenticated }) => {

    if (isAuthenticated) {
      oidcSecurityService.getAccessToken().subscribe((token) => {
        req = req.clone({
          headers: req.headers.set('Authorization', `Bearer ${token}`)
        });
      });
    }
  });

  return next(req);
};
