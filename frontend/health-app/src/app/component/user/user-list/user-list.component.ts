import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../user.service";
import {ConfirmationService, MessageService, PrimeTemplate} from "primeng/api";
import {UpdateUserPasswordCommand, UserDto} from "../usuario.interface";
import {Button} from "primeng/button";
import {ConfirmPopupModule} from "primeng/confirmpopup";
import {DialogModule} from "primeng/dialog";
import {FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {InputTextModule} from "primeng/inputtext";
import {DatePipe, NgIf} from "@angular/common";
import {ProgressSpinnerModule} from "primeng/progressspinner";
import {Table, TableModule} from "primeng/table";
import {TagModule} from "primeng/tag";
import {ToastModule} from "primeng/toast";
import {TooltipModule} from "primeng/tooltip";
import {User} from "../../../core/token.service";
import {AuthService} from "../../../services/auth.service";

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [
    Button,
    ConfirmPopupModule,
    DialogModule,
    FormsModule,
    InputTextModule,
    NgIf,
    PrimeTemplate,
    ProgressSpinnerModule,
    TableModule,
    TagModule,
    ToastModule,
    TooltipModule,
    DatePipe,
    ReactiveFormsModule
  ], providers: [ConfirmationService],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.scss'
})
export class UserListComponent implements OnInit {
  registros: UserDto[] = [];
  userDto !: UserDto;
  @ViewChild('dt') dt: Table | undefined;
  password = '';
  newPassword = '';
  resetForm: FormGroup;

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private messageService: MessageService
  ) {
    this.resetForm = this.fb.group({
      confirmPassword: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]]
    }, {validator: this.passwordMatchValidator});
  }

  ngOnInit(): void {
    this.userService.findAll().subscribe(registros => {
      this.registros = registros;
    })
  }

  onFilterInputChange(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    if (this.dt) {
      this.dt.filterGlobal(inputElement.value, 'contains');
    }
  }

  visible: boolean = false;

  showDialog(dto: UserDto) {
    this.visible = true;
    this.userDto = dto;
    console.log(this.userDto)
  }

  ativar(dto: UserDto) {

  }

  inativar(dto: UserDto) {
    console.log(dto)
  }

  resetarSenha() {
    this.visible = false
    if (this.resetForm.invalid) {
      return;
    }

    const {newPassword, confirmPassword} = this.resetForm.value;

    const command: UpdateUserPasswordCommand = {
      password: confirmPassword,
      id: this.userDto.id,
      email: this.userDto.email,
      login: this.userDto.login,
    };
    this.authService.resetPassword(command).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'info',
          summary: 'Sucesso',
          detail: 'Senha atualizada com sucesso',
          life: 3000
        });
      },
      error: (error) => {
        console.log('error',error)
        const errorMessage = error.error?.detail || 'Ocorreu um erro resetar a senha.';
        this.messageService.add({severity: 'warn', summary: 'Atenção', detail: errorMessage});
      }
    });
  }

  passwordMatchValidator(frm: FormGroup) {
    return frm.get('password')?.value === frm.get('confirmPassword')?.value
      ? null : {mismatch: true};
  }

  cancelarReset() {
    this.visible = false
    this.resetForm.reset();
  }
}
