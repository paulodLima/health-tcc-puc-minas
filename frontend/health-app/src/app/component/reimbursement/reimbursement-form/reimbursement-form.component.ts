import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {InputMaskModule} from "primeng/inputmask";
import {Button} from "primeng/button";
import {TooltipModule} from "primeng/tooltip";
import {InputTextModule} from "primeng/inputtext";
import {CompanyService} from "../../company/company.service";
import {CreateReimbursementCommand, ReimbursementResponseDto} from "../reimbursement.interface";
import {CompanyDto} from "../../company/company.interface";
import {NgForOf, NgIf} from "@angular/common";
import {ReimbursementService} from "../reimbursement.service";
import {ActivatedRoute, Router} from "@angular/router";
import {TokenService} from "../../../core/token.service";
import {loadEsmModuleFromMemory} from "@angular-devkit/build-angular/src/utils/server-rendering/load-esm-from-memory";

@Component({
  selector: 'app-reimbursement-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    InputMaskModule,
    Button,
    TooltipModule,
    InputTextModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './reimbursement-form.component.html',
  styleUrl: './reimbursement-form.component.scss'
})
export class ReimbursementFormComponent implements OnInit {
  companyDto: CompanyDto[] = [];
  files: { [key: string]: File | null } = {};
  id = '';
  form : FormGroup = new FormGroup({
    id: new FormControl<string | null>(null),
    user: new FormControl<string | null>(null),
    company: new FormControl<string | null>(null, [Validators.required]),
    amount: new FormControl<string | null>(null, [Validators.required, Validators.pattern(/^\d+(\.\d{1,2})?$/)]),
    medicalUrl: new FormControl<string | null>(null, [Validators.required]),
    invoiceUrl: new FormControl<string | null>(null, [Validators.required]),
  });

  constructor(private fb: FormBuilder, private companyService: CompanyService, private reimbursementService: ReimbursementService, private _router: Router,private tokenService: TokenService,private _activatedRoute: ActivatedRoute,) {
  }

  ngOnInit(): void {
    this.companyService.listAll().subscribe(result => {
      this.companyDto = result;
    });
    this._activatedRoute.params.subscribe((params) => {
      if (params['id']) {
        this.reimbursementService.findById(params['id']).subscribe((reimbursement) => {
          console.log(reimbursement)
          this.id = reimbursement.id;
          this.form.patchValue({
            id: reimbursement.id,
            userName: reimbursement.user,
            amount: reimbursement.amount,
            companyName: reimbursement.company,
            medicalUrl: reimbursement.medicalUrl,
            invoiceUrl: reimbursement.invoiceUrl,
          });
        });
      }
    });
  }

  onSubmit() {
    if (this.form.valid) {
      const formData = new FormData();
      const uuid = this.tokenService.getIdUser(localStorage.getItem('access_token') ?? '');
      formData.append('user', uuid);
      formData.append('company', this.form.get('company')!.value);
      formData.append('amount', this.form.get('amount')!.value);
      formData.append('medicalUrl', this.files['medicalUrl']!, this.files['medicalUrl']!.name);
      formData.append('invoiceUrl', this.files['invoiceUrl']!, this.files['invoiceUrl']!.name);

      console.log(this.id)
      if (this.id) {
        this.update(formData);
      } else {
        this.create(formData);
      }
    }
  }

  onFileChange(event: any, key: string) {
    const file = event.target.files[0];
    if (file) {
      this.files[key] = file;
    }
  }

  private create(formData: FormData) {
    this.reimbursementService.create(formData).subscribe({
      next: () => {
        this._router.navigate(['/reembolso']);
      },
      error: (error) => {
      }
    })
  }

  cancelar() {
    this._router.navigate(['/reembolso']);
  }

  private update(formData: FormData) {
    var command = this.form.value as ReimbursementResponseDto;
    this.reimbursementService.update(command.id,formData).subscribe({
      next: () => {
        this._router.navigate(['/reembolso']);
      },
      error: (error) => {
      }
    })
    this._router.navigate(['/reembolso']);
  }
}
