import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ConfirmationService, MenuItem, TreeNode } from 'primeng/api';
import { TreeTableModule } from 'primeng/treetable';
import { MenuService } from './menu.service';
import { ButtonModule } from 'primeng/button';
import { DialogService, DynamicDialogModule } from 'primeng/dynamicdialog';
import { FormModalMenuComponent } from './form-modal-menu/form-modal-menu.component';
import { MenuDto } from './menu.interfaces';
import { ConfirmDialog } from 'primeng/confirmdialog';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, TreeTableModule, ButtonModule],
  providers: [DialogService],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit {
  menus: TreeNode[] = [];

  constructor(
    private _menuService: MenuService,
    private _dialogService: DialogService,
    private _confirmationService: ConfirmationService, 
  ) { }
  
  private montarMenu(item: MenuDto): TreeNode {
    return {
      label: item.titulo,
      icon: item.icone,
      data: item,
      children: item.subMenus ? item.subMenus.map(x => this.montarMenu(x)) : []
    };
  } 

  ngOnInit(): void {
    this._menuService.listar().subscribe(itens => {        
      this.menus = itens.map(item => this.montarMenu(item));
    });
  }

  novo(menu: MenuDto | null) {
    var ref = this._dialogService.open(FormModalMenuComponent, { header: 'Cadastro de menu', data: { menuSuperiorId: menu?.id } });
  }

  alterar(item: MenuDto) {
    var ref = this._dialogService.open(FormModalMenuComponent, { header: 'Cadastro de menu', data: { id: item.id } });
  }

  excluir(item: MenuDto) {
    this._confirmationService.confirm({
      header: 'Confirmação',
      message: 'Deseja realmente excluir o registro?',
      acceptButtonStyleClass: 'bg-primary',
      rejectButtonStyleClass: 'bg-secondary',
      accept: () => {
        this._menuService.excluir(item.id).subscribe({
          next: () => {
            this.menus = this.menus.filter(x => x !== item);
          }
        });
      }
    });
  }
}
