import {ApplicationConfig, NgModule} from '@angular/core';
import {provideRouter, RouterModule} from '@angular/router';

import {routes} from './app.routes';
import {HttpClientModule, provideHttpClient, withInterceptors} from "@angular/common/http";
import {provideAnimations} from "@angular/platform-browser/animations";
import {authConfig} from "./auth/auth.config";
import {provideAuth} from "angular-auth-oidc-client";
import {requestInterceptor} from "./core/interceptors/request.interceptor";
import {provideEnvironmentNgxMask} from "ngx-mask";

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes), provideAnimations(), provideHttpClient(), provideAuth(authConfig), provideHttpClient(withInterceptors([requestInterceptor])), provideEnvironmentNgxMask(),
  ]
};

@NgModule({
  imports: [RouterModule.forRoot(routes), HttpClientModule],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
