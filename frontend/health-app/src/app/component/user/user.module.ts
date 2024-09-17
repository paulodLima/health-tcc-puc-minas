import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {reimbursementRoutes} from "../reimbursement/reimbursement.routes";
import {userRoutes} from "./user.routes";

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(userRoutes),
  ],
  providers: []
})
export class UserModuleListModule { }
