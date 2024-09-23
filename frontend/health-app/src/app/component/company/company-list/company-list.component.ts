import {Component, OnInit, ViewChild} from '@angular/core';
import {Button} from "primeng/button";
import {ConfirmPopupModule} from "primeng/confirmpopup";
import {DatePipe, NgIf} from "@angular/common";
import {DialogModule} from "primeng/dialog";
import {InputTextModule} from "primeng/inputtext";
import {ConfirmationService, PrimeTemplate} from "primeng/api";
import {ReactiveFormsModule} from "@angular/forms";
import {Table, TableModule} from "primeng/table";
import {ToastModule} from "primeng/toast";
import {TooltipModule} from "primeng/tooltip";
import {CompanyDto} from "../company.interface";
import {CompanyService} from "../company.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-company-list',
  standalone: true,
    imports: [
        Button,
        ConfirmPopupModule,
        DatePipe,
        DialogModule,
        InputTextModule,
        NgIf,
        PrimeTemplate,
        ReactiveFormsModule,
        TableModule,
        ToastModule,
        TooltipModule
    ],
  providers:[ConfirmationService],
  templateUrl: './company-list.component.html',
  styleUrl: './company-list.component.scss'
})
export class CompanyListComponent implements OnInit{
  registros: CompanyDto[] = [];
  @ViewChild('dt') dt: Table | undefined;

  constructor(private companyService: CompanyService, private router: Router) {
  }
  ngOnInit(): void {
    this.companyService.listAll().subscribe(registros => {
      this.registros = registros;
    })
  }
    onFilterInputChange(event: Event) {
      const inputElement = event.target as HTMLInputElement;
      if (this.dt) {
        this.dt.filterGlobal(inputElement.value, 'contains');
      }
  }

  updateCompany(registro: CompanyDto) {
      this.router.navigate([`/empresa/${registro.id}`])
  }
}
