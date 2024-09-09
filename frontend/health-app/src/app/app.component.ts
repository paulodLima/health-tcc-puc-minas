import { Component } from '@angular/core';
import {NavigationEnd, Router, RouterOutlet} from '@angular/router';
import {SidebarComponent} from "./layout/sidebar/sidebar.component";
import {LoginComponent} from "./login/login.component";
import {CreateUserComponent} from "./component/user/user-form/create-user.component";
import {ButtonDirective} from "primeng/button";
import {ResetPasswordComponent} from "./login/reset-password/reset-password.component";
import {NgIf} from "@angular/common";
import {HomeComponent} from "./component/home/home.component";
import {AvatarModule} from "primeng/avatar";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ButtonDirective, NgIf, AvatarModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'health-app';

  constructor(private router: Router) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.checkIfLoginOrResetPage();
      }
    });
  }

  isLoginOrResetPage(): boolean {
    const url = this.router.url;
    return url.includes('/login') || url.includes('/reset-password');
  }

  checkIfLoginOrResetPage() {
    // Custom logic for checking URL or other conditions
  }
}
