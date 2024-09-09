import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputSwitchModule } from 'primeng/inputswitch';
import { DialogService, DynamicDialogComponent, DynamicDialogRef } from 'primeng/dynamicdialog';
import { MenuService } from '../menu.service';
import { AlterarMenuCommand, CriarMenuCommand } from '../menu.interfaces';

@Component({
  selector: 'app-form-modal-menu',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, ButtonModule, InputTextModule, InputSwitchModule],
  templateUrl: './form-modal-menu.component.html',
  styleUrl: './form-modal-menu.component.scss'
})
export class FormModalMenuComponent implements OnInit {
  instance: DynamicDialogComponent | undefined

  form: FormGroup = new FormGroup({
    id: new FormControl<string | null>(null),
    titulo: new FormControl<string | null>(null, [Validators.required]),
    icone: new FormControl<string | null>(null, []),
    url: new FormControl<string | null>(null),
    descricao: new FormControl<string | null>(null),
    menuSuperiorId: new FormControl<string | null>(null),
  });

  constructor(
    private _ref: DynamicDialogRef,
    private _dialogService: DialogService,
    private _menuService: MenuService
  ) {
    this.instance = this._dialogService.getInstance(this._ref);
  }

  
  ngOnInit(): void {
    if (this.instance && this.instance.data) {
      if (this.instance.data.id) {
        this._menuService.pesquisar(this.instance.data.id).subscribe(menu => {
          this.form.patchValue(menu);
        });
      } else {
        this.form.patchValue({menuSuperiorId: this.instance.data.menuSuperiorId});
      }
    }
  }

  salvar() {
    if (this.form.valid) {
      if (this.form.value.id) {
        this.alterar();
      } else {
        this.criar();
      }
    }
  }

  criar() {
    var command = this.form.value as CriarMenuCommand;
    this._menuService.criar(command).subscribe(() => {
      this._ref.close();
    });
  }

  alterar() {
    var command = this.form.value as AlterarMenuCommand;
    this._menuService.alterar(command.id, command).subscribe(() => {
      this._ref.close();
    });
  }
}
