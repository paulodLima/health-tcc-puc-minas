import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {companyRoutes} from "./company.routes";

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(companyRoutes),
  ],
  providers: []
})
export class CompanyListModule { }
