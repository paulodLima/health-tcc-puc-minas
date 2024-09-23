import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {LoginService} from "./login.service";
import {Router} from "@angular/router";
import {LoginResponse, OidcSecurityService} from "angular-auth-oidc-client";
import {HttpClient} from "@angular/common/http";
import {Button} from "primeng/button";
import {TokenService} from "../core/token.service";
import {NgOptimizedImage} from "@angular/common";
import {AuthService} from "../auth/auth-service";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    Button,
    NgOptimizedImage
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private fb: FormBuilder,private _loginService: LoginService, private router : Router,private http: HttpClient,private oidcSecurityService: OidcSecurityService,private tokenService: TokenService,private authService: AuthService) {
    this.loginForm = this.fb.group({
      login: ['', [Validators.required]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const { login, password } = this.loginForm.value;
      this.http.post('http://localhost:7080/realms/health/protocol/openid-connect/token', new URLSearchParams({
        client_id: 'health-api',
        client_secret:'MuyShRtqmjBHiurKULLdvOi58cInnnRe', //'5oj3ruNQKn4ShqsHu4rq0vTcfp8TsRZC',
        grant_type: 'password',
        username: login,
        password: password,
        scope: 'openid profile offline_access'
      }).toString(), {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }).subscribe(
        (response: any) => {

          this.oidcSecurityService.isAuthenticated$.subscribe(({isAuthenticated}) => {
            const token = localStorage.getItem('access_token');
          });
          localStorage.setItem('access_token', response.access_token);
          this.tokenService.setAuthenticated(true);
          this.router.navigate(['/inicio']);
        },
        (error) => {
          console.error('Login failed', error);
          this.router.navigate(['/login']);
        }
      );
    }
  }
  login() {
    if (this.loginForm.valid) {
      const { login, password } = this.loginForm.value;
      this.authService.login(login, password).subscribe(
        (response) => {
          this.router.navigate(['/inicio']);
        },
        (error) => {
          console.error('Erro no login', error);
        }
      );
    }
  }
  logout() {
    console.log('passando no logout')
    this.oidcSecurityService.logoff();
  }
}
