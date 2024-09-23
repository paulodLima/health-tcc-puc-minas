import {Routes} from '@angular/router';
import {AuthGuard} from "./core/auth.guard";

export const routes: Routes = [
    {
      path: '',
      loadComponent: () => import('./layout/layout.component').then(m => m.LayoutComponent),
      canActivate: [AuthGuard],
      children: [
        {
          path: 'inicio',
          loadComponent: () => import('./component/home/home.component').then(m => m.HomeComponent),
          data: { roles: ['admin','manager','user'] }
        },{
          path: 'usuario',
          loadChildren: () => import('./component/user/user.module').then((m) => m.UserModuleListModule),
          //loadComponent: () => import('./component/user/user-form/user-form.component').then(m => m.UserFormComponent),
          data: { roles: ['admin','manager'] }
        },
        {
          path: 'reembolso',
          loadChildren: () => import('./component/reimbursement/reimbursement.module').then((m) => m.ReimbursementListModule),
          //loadComponent: () => import('./component/reimbursement/reimbursement-list/reimbursement-list.component').then(m => m.ReimbursementListComponent),
          data: { roles: ['admin','manager','user'] }
        },
        {
          path: 'empresa',
          loadChildren: () => import('./component/company/company.module').then((m) => m.CompanyListModule),
          data: { roles: ['admin','manager'] }
        },
        {path: '', redirectTo: 'login', pathMatch: 'full'}
      ]
    },
    {
      path: 'reset/:token',
      loadComponent: () => import('./login/reset-password/reset-password.component').then(m => m.ResetPasswordComponent),
    },
    {
      path: 'reset',
      loadComponent: () => import('./login/reset-password/reset-password.component').then(m => m.ResetPasswordComponent),
    },
    {
      path: 'login',
      loadComponent: () => import('./login/login.component').then(m => m.LoginComponent),
    }
  ]
;
