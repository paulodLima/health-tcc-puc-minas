import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UsuarioService} from "./usuario.service";
import {ConfirmationService} from "primeng/api";
import {UsuarioDto} from "./usuario.interface";

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [],
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss'
})
export class UserComponent implements OnInit {
  registros: UsuarioDto[] = [];
  constructor(
    private _confirmationService: ConfirmationService,
    private _usuarioService: UsuarioService,
    private _router: Router
  ) { }

  ngOnInit(): void {
    this._usuarioService.listar().subscribe({
      next: (registros) => this.registros = registros
    });
  }
}
