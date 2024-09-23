import {Component, inject, OnInit} from '@angular/core';
import {NavigationEnd, Router, RouterOutlet} from '@angular/router';
import {SidebarComponent} from "./layout/sidebar/sidebar.component";
import {LoginComponent} from "./login/login.component";
import {UserFormComponent} from "./component/user/user-form/user-form.component";
import {ButtonDirective} from "primeng/button";
import {ResetPasswordComponent} from "./login/reset-password/reset-password.component";
import {NgIf} from "@angular/common";
import {HomeComponent} from "./component/home/home.component";
import {AvatarModule} from "primeng/avatar";
import {OidcSecurityService} from "angular-auth-oidc-client";
import {MessageService} from "primeng/api";
import {NgxMaskDirective} from "ngx-mask";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ButtonDirective, NgIf, AvatarModule,NgxMaskDirective],
  providers: [MessageService],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'health-app';
  roles: string[] = []
  uuid = '';

  private readonly oidcSecurityService = inject(OidcSecurityService);

  constructor(private router: Router) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.checkIfLoginOrResetPage();
      }
    });
  }

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(({isAuthenticated}) => {
      const token = localStorage.getItem('access_token');
      if (token)
      this.extractRolesFromToken(token);
    });
  }

  private extractRolesFromToken(token: string): void {
    const tokenPayload = JSON.parse(atob(token.split('.')[1]));
    this.roles = tokenPayload.resource_access?.['health-api']?.roles || tokenPayload.realm_access.roles;
  }

  isLoginOrResetPage(): boolean {
    const url = this.router.url;
    return url.includes('/login') || url.includes('/reset-password');
  }

  checkIfLoginOrResetPage() {
    // Custom logic for checking URL or other conditions
  }

  logout() {
    this.oidcSecurityService.logoff().subscribe((result) => console.log(result));
    localStorage.removeItem('access_token');
    this.router.navigate(['/login']);
  }
}
