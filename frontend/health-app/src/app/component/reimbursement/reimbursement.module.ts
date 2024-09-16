import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {reimbursementRoutes} from "./reimbursement.routes";

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(reimbursementRoutes),
  ],
  providers: []
})
export class ReimbursementListModule { }
