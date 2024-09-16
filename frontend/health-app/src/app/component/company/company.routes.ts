import {Routes} from "@angular/router";

export const companyRoutes: Routes = [
  {
    path: '',
    loadComponent: () => import('./company-list/company-list.component').then((m) => m.CompanyListComponent)
  },
  {
    path: 'new',
    loadComponent: () => import('./company-form/company-form.component').then((m) => m.CompanyFormComponent)
  },
  {
    path: ':id',
    loadComponent: () => import('./company-form/company-form.component').then((m) => m.CompanyFormComponent)
  }
];
