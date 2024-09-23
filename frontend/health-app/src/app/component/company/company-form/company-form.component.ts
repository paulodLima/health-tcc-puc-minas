import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {PasswordModule} from "primeng/password";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ToastModule} from "primeng/toast";
import {ActivatedRoute, Router} from "@angular/router";
import {NgxMaskDirective, NgxMaskPipe, provideNgxMask} from "ngx-mask";
import {cnpjValidator} from '../cnpj-validador';
import {CreateUserCommand} from "../../user/usuario.interface";
import {CreateCompanyCommand, UpdateCompanyCommand} from "../company.interface";
import {CompanyService} from "../company.service";
import {MessageService} from "primeng/api";
import {MyMessageService} from "../../../services/MyMessageService";

@Component({
  selector: 'app-company-form',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    PasswordModule,
    ReactiveFormsModule,
    ToastModule,
    NgxMaskDirective,
    NgxMaskPipe
  ], providers: [provideNgxMask(), MyMessageService],
  templateUrl: './company-form.component.html',
  styleUrl: './company-form.component.scss'
})
export class CompanyFormComponent implements OnInit {
  form: FormGroup;
  isupdate = false;

  constructor(private fb: FormBuilder, private router: Router, private companyService: CompanyService, private messageService: MyMessageService, private activatedRoute : ActivatedRoute) {
    this.form = this.fb.group({
      id: [null],
      name: [null, [Validators.required]],
      status: [null, [Validators.required]],
      cnpj: [null, [Validators.required, cnpjValidator]],
    });
  }

  status = [
    {display: 'Ativo', value: true},
    {display: 'Inativo', value: false}
  ];

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => {
      if (params['id']) {
        this.isupdate = true;
        this.companyService.findById(params['id']).subscribe((company) => {
          this.form.patchValue({
            id: company.id,
            name: company.name,
            cnpj: company.cnpj.numero,
            status: company.status,
          });
        });
      }else{
        this.isupdate = false;
      }
    });
  }

  cancelar() {
    this.router.navigate([`/empresa`])
  }

  onSubmit() {
    if (this.form.valid) {
      const company = this.form.value as UpdateCompanyCommand;
      if (this.form.value.id) {
        this.companyService.update(company).subscribe({
          next: (response) => {
            this.messageService.addSucess(['Empresa criada com sucesso!']);
            setTimeout(() => {
              this.router.navigate(['/empresa']);
            }, 2000);
          },
          error: (err) => {
            const errorMessages = err.error.errors || ['Ocorreu um erro inesperado.'];
            this.messageService.addError(errorMessages);
          }
        });
      } else {
        this.companyService.create(company).subscribe({
          next: (response) => {
            this.messageService.addSucess(['Empresa criada com sucesso!']);
            setTimeout(() => {
              this.router.navigate(['/empresa']);
            }, 2000);
          },
          error: (err) => {
            const errorMessages = err.error.errors || ['Ocorreu um erro inesperado.'];
            this.messageService.addError(errorMessages);
          }
        });
      }
    }
  }
}
