import {Routes} from "@angular/router";

export const reimbursementRoutes: Routes = [
  {
    path: '',
    loadComponent: () => import('./reimbursement-list/reimbursement-list.component').then((m) => m.ReimbursementListComponent)
  },
  {
    path: 'new',
    loadComponent: () => import('./reimbursement-form/reimbursement-form.component').then((m) => m.ReimbursementFormComponent)
  },
  {
    path: ':id',
    loadComponent: () => import('./reimbursement-form/reimbursement-form.component').then((m) => m.ReimbursementFormComponent)
  }
];
