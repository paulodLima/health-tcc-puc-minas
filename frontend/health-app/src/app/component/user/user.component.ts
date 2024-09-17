import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "./user.service";
import {ConfirmationService} from "primeng/api";
import {UserDto} from "./usuario.interface";

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [],
  templateUrl: './user-list/user.component.html',
  styleUrl: './user-list/user.component.scss'
})
export class UserComponent implements OnInit {
  registros: UserDto[] = [];
  constructor(
    private _confirmationService: ConfirmationService,
    private _usuarioService: UserService,
    private _router: Router
  ) { }

  ngOnInit(): void {
    this._usuarioService.findAll().subscribe({
      next: (registros) => this.registros = registros
    });
  }
}
