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

  constructor(private _menuService: MenuService) {
  }

  ngOnInit(): void {
    this._menuService.listar().subscribe(menus => {
      this.menus = menus.map(menu => this.toMenuItem(menu))
      console.log('menus', this.menus);
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

  menuItems = [
    {
      label: 'Team', iconClass: 'pi-users', route: '/login', subMenu: [
        {label: 'Create Team', route: '/team/create', iconClass: 'pi pi-plus'},
        {label: 'View Teams', route: '/team/view', iconClass: 'pi pi-eye'}
      ],
      open: false
    }
    ,
    {label: 'Messages', iconClass: 'pi-comments', route: '/reset-password'},
    {
      label: 'Settings', iconClass:
        'pi-cog'
    }
    ,
  ]
  ;

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
}
