import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {PaginatorModule} from "primeng/paginator";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {PasswordModule} from "primeng/password";
import {CreateUsuarioCommand, noSpecialCharsAndSpaces} from "../usuario.interface";
import {Router} from "@angular/router";
import {UserService} from "../user.service";
import {ToastModule} from "primeng/toast";
import {MessageService} from "primeng/api";
import {MyMessageService} from "../../../services/MyMessageService";

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    PaginatorModule,
    ReactiveFormsModule,
    PasswordModule,
    ToastModule
  ],
  providers: [MyMessageService],
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.scss'
})
export class UserFormComponent implements OnInit{
  form : FormGroup = new FormGroup({
    name: new FormControl<string | null>(null, [Validators.required]),
    email: new FormControl<string | null>(null, [Validators.required, Validators.email]),
    login: new FormControl<string | null>(null, [Validators.required, noSpecialCharsAndSpaces]),
    password: new FormControl<string | null>(null, [Validators.required]),
    perfil: new FormControl<string | null>(null, [Validators.required]),
  });

  roles = [
    { display: 'Usuário', value: 'user' },
    { display: 'Gerente', value: 'manager' },
    { display: 'Administrador', value: 'admin' }
  ];
  constructor(private route: Router, private userService: UserService,private messageService: MyMessageService) {
  }
  ngOnInit(): void {
  }

  onSubmit() {
    if(this.form.valid) {
      const user = this.form.value as CreateUsuarioCommand;
      this.userService.create(user).subscribe({
        next: (response) => {
          this.messageService.addSucess(['Usuário criado com sucesso!']);
          this.route.navigate(['/cadastro-usuario']);
        },
        error: (err) => {
          const errorMessages = err.error.errors || ['Ocorreu um erro inesperado.'];
          this.messageService.addError(errorMessages);
        }
      });
    }

  }

  cancelar() {
    this.route.navigate(['/cadastro-usuario']);
  }
}
