import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgIf, NgOptimizedImage} from "@angular/common";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {ButtonDirective} from "primeng/button";
import {InputTextModule} from "primeng/inputtext";
import {Ripple} from "primeng/ripple";
import {PasswordModule} from "primeng/password";
import {CheckboxModule} from "primeng/checkbox";
import { jwtDecode } from "jwt-decode";
import {UpdateUserPasswordCommand} from "./reset-password.interface";

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf,
    ButtonDirective,
    InputTextModule,
    RouterLink,
    Ripple,
    PasswordModule,
    FormsModule,
    CheckboxModule,
    NgOptimizedImage
  ],
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.scss'
})
export class ResetPasswordComponent implements OnInit{
  resetForm: FormGroup;
  successMessage!: string;
  errorMessage!: string;
  token!: string;
  id!: string;
  password: any;
  Confirmpassword: any;
  login = '/login'

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.resetForm = this.fb.group({
      confirmPassword: ['', Validators.required],
      newPassword: ['', [Validators.required, Validators.minLength(6)]]
    }, { validator: this.passwordMatchValidator });
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.token = params['token'];
      if (this.token) {
        this.decodeToken(this.token);
      }
    });
  }

  onSubmit(): void {
    if (this.resetForm.invalid) {
      return;
    }

    const { newPassword, confirmPassword } = this.resetForm.value;
    const command: UpdateUserPasswordCommand = {
      password: confirmPassword,
      id: this.id,
    };
    this.authService.resetPassword(command).subscribe({
      next: () => {
        this.successMessage = 'Senha redefinida com sucesso!';
        setTimeout(() => this.router.navigate(['/login']), 3000);
      },
      error: err => this.errorMessage = err.error.message || 'Erro ao redefinir a senha'
    });
  }

  decodeToken(token: string): void {
    try {
      const decoded: any = jwtDecode(token);
      this.id = decoded.sub;
    } catch (error) {
      this.router.navigate(['/login']);
      console.error('Erro ao decodificar o token:', error);
    }
  }
  passwordMatchValidator(frm: FormGroup) {
    return frm.get('newPassword')?.value === frm.get('confirmPassword')?.value
      ? null : { mismatch: true };
  }
}
