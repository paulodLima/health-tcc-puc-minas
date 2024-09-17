import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {PaginatorModule} from "primeng/paginator";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {PasswordModule} from "primeng/password";
import {CreateUserCommand, noSpecialCharsAndSpaces, UpdateUserCommand} from "../usuario.interface";
import {ActivatedRoute, Router} from "@angular/router";
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
  isupdate = false;
  form : FormGroup = new FormGroup({
    id: new FormControl(),
    name: new FormControl<string | null>(null, [Validators.required]),
    email: new FormControl<string | null>(null, [Validators.required, Validators.email]),
    login: new FormControl<string | null>(null, [Validators.required, noSpecialCharsAndSpaces]),
    password: new FormControl<string | null>(null, ),
    status: new FormControl<string | null>(null, ),
    perfil: new FormControl<string | null>(null, [Validators.required]),
  });

  roles = [
    { display: 'Usuário', value: 'user' },
    { display: 'Gerente', value: 'manager' },
    { display: 'Administrador', value: 'admin' }
  ];
  status = [
    { display: 'Ativo', value: true },
    { display: 'Inativo', value: false }
  ];
  constructor(private route: Router, private userService: UserService,private _activatedRoute: ActivatedRoute,private messageService: MyMessageService) {
  }
  ngOnInit(): void {
    this._activatedRoute.params.subscribe((params) => {
      if (params['id']) {
        this.isupdate = true;
        this.userService.findById(params['id']).subscribe((user) => {
          this.form.patchValue({
            id: user.id,
            name: user.name,
            email: user.email,
            login: user.login,
            perfil: user.perfil,
            status: user.status
          });
          console.log(user)
        });
      }else{
        this.isupdate = false;
      }
    });
  }

  onSubmit() {
    if(this.form.valid) {
      const user = this.form.value as CreateUserCommand;
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
    this.route.navigate(['/usuario']);
  }

  update() {
    if(this.form.valid) {
      console.log(this.form.value)
      const user = this.form.value as UpdateUserCommand;
      console.log(user)
      this.userService.update(user).subscribe({
        next: (response) => {
          this.messageService.addSucess(['Usuário atualizado com sucesso!']);
          this.route.navigate(['/usuario']);
        },
        error: (err) => {
          const errorMessages = err.error.errors || ['Ocorreu um erro inesperado.'];
          this.messageService.addError(errorMessages);
        }
      });
    }
  }
}
