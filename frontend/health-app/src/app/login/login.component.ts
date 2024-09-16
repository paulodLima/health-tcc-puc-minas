import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {LoginService} from "./login.service";
import {Router} from "@angular/router";
import {LoginResponse, OidcSecurityService} from "angular-auth-oidc-client";
import {HttpClient} from "@angular/common/http";
import {Button} from "primeng/button";
import {TokenService} from "../core/token.service";
import {NgOptimizedImage} from "@angular/common";

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

  constructor(private fb: FormBuilder,private _loginService: LoginService, private router : Router,private http: HttpClient,private oidcSecurityService: OidcSecurityService,private tokenService: TokenService) {
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
        client_secret: 'MuyShRtqmjBHiurKULLdvOi58cInnnRe',
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
    this.oidcSecurityService.authorize();
  }
}
