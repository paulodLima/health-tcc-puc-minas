import {Routes} from '@angular/router';
import {AppComponent} from "./app.component";
import {HomeComponent} from "./component/home/home.component";

export const routes: Routes = [
    {
      path: '',
      loadComponent: () => import('./layout/layout.component').then(m => m.LayoutComponent),
      children: [
        {
          path: 'inicio',
          loadComponent: () => import('./component/home/home.component').then(m => m.HomeComponent)
        },{
          path: 'cadastro-usuario',
          loadComponent: () => import('./component/user/user-form/create-user.component').then(m => m.CreateUserComponent)
        },
        {path: '', redirectTo: 'inicio', pathMatch: 'full'}
      ]
    },
    {
      path: 'reset-password',
      loadComponent: () => import('./login/reset-password/reset-password.component').then(m => m.ResetPasswordComponent),
    },
    {
      path: 'login',
      loadComponent: () => import('./login/login.component').then(m => m.LoginComponent),
    }
  ]
;
