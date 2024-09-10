import { LogLevel, PassedInitialConfig } from 'angular-auth-oidc-client';

export const authConfig: PassedInitialConfig = {
  config: {
              authority: 'http://localhost:7080/realms/health',
              redirectUrl: 'http://localhost:4200/login',
              postLogoutRedirectUri: 'http://localhost:4200/login',
              clientId: 'health-app',
              scope: 'openid profile offline_access email roles', // 'openid profile offline_access ' + your scopes
              responseType: 'code',
              silentRenew: true,
              useRefreshToken: true,
              renewTimeBeforeTokenExpiresInSeconds: 30,
              logLevel: LogLevel.Debug,
          }
}
