import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {NgClass, NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {Button, ButtonDirective} from "primeng/button";
import {Router, RouterLink, RouterOutlet} from "@angular/router";
import {Sidebar, SidebarModule} from "primeng/sidebar";
import {HomeComponent} from "../../component/home/home.component";
import {AvatarModule} from "primeng/avatar";
import {Ripple} from "primeng/ripple";
import {StyleClassModule} from "primeng/styleclass";
import {LoginComponent} from "../../login/login.component";
import {MenuService} from "../../component/menu/menu.service";
import {MenuItem} from "primeng/api";
import {MenuDto} from "../../component/menu/menu.interfaces";
import {TokenService} from "../../core/token.service";

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    NgIf,
    Button,
    RouterOutlet,
    SidebarModule,
    HomeComponent,
    AvatarModule,
    Ripple,
    StyleClassModule,
    NgOptimizedImage,
    NgClass,
    NgForOf,
    ButtonDirective,
    RouterLink
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent implements OnInit {
  @Output() toggleMinimize = new EventEmitter<void>();
  sidebarVisible: boolean = true;
  sidebarMinimized: boolean = false;
  userId = '';
  constructor(private _menuService: MenuService, private tokenService: TokenService, private router : Router) {
  }

  ngOnInit(): void {
    const token = localStorage.getItem('access_token') ?? '';
    this.userId = this.tokenService.getIdUser(token);
    const userRoles = this.tokenService.getRolesUser(token);
    this._menuService.listar(token, userRoles).subscribe(menus => {
      this.menus = menus.map(menu => {
        if (userRoles.includes('user')) {
          menu.subMenus = menu.subMenus.filter(sub => sub.titulo !== 'Lista');
        }
        return this.toMenuItem(menu);
      });
    });

  }
  menus: any[] = [];

  private toMenuItem(menu: MenuDto): MenuItem {
    return {
      label: menu.titulo,
      icon: menu.icone,
      routerLink: menu.url,
      items: menu.subMenus.map(subMenu => this.toMenuItem(subMenu)),
      open: false,
    };
  }

  logout() {
    this.tokenService.logout();
  }

  toggleSidebar() {
    this.sidebarVisible = !this.sidebarVisible;
    this.sidebarMinimized = !this.sidebarMinimized; // Toggle minimize state
  }

  @ViewChild('sidebarRef')
  sidebarRef! : Sidebar;

  toggleSubMenu(item: any
  ) {
    if (item.items) {
      item.open = !item.open;
    }
  }
}
