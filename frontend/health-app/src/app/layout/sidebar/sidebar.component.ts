import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {NgClass, NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {Button, ButtonDirective} from "primeng/button";
import {RouterLink, RouterOutlet} from "@angular/router";
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
export class SidebarComponent implements OnInit{
  @Output() toggleMinimize = new EventEmitter<void>();
  sidebarVisible: boolean = true;
  sidebarMinimized: boolean = false;

  constructor(private _menuService: MenuService,private tokenService: TokenService) {
  }

  ngOnInit(): void {
    const token = localStorage.getItem('access_token') ?? ''
    this._menuService.listar(token).subscribe(menus => {
      this.menus = this.filterMenu(menus.map(menu => this.toMenuItem(menu)));
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
  private filterMenu(menuItems: MenuItem[]): MenuItem[] {
    return menuItems
      .filter(item => this.isMenuItemVisible(item))
      .map(item => ({
        ...item,
        items: item.items ? this.filterMenu(item.items) : []
      }));
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

  toggleSidebarMinimize() {
    this.sidebarMinimized = !this.sidebarMinimized;
  }

  toggleSubMenu(item: any
  ) {
    if (item.items) {
      item.open = !item.open;
    }
  }
  private isMenuItemVisible(menuItem: MenuItem): boolean {
    const userRoles = this.tokenService.getRolesUser();
    if (menuItem.routerLink) {
      const routeRoles = this.getRouteRoles(menuItem.routerLink);
      return this.hasRequiredRole(routeRoles, userRoles);
    }
    return true; // Exibe o item se não houver rota ou roles especificadas
  }

  private getRouteRoles(route: string): string[] {
    const routeRolesMap: { [key: string]: string[] } = {
      '/home': ['user', 'admin'],
      '/cadastro-usuario': ['manager']
    };
    return routeRolesMap[route] || [];
  }

  private hasRequiredRole(requiredRoles: string[], userRoles: string[]): boolean {
    console.log('route role maps',requiredRoles, userRoles);
    if (requiredRoles.length === 0) return true; // Se não houver roles exigidas, exibe o item
    return requiredRoles.some(role => userRoles.includes(role));
  }
}
