export interface MenuDto {
  id: string;
  titulo: string;
  icone: string;
  url: string;
  descricao: string;
  subMenus: MenuDto[];
  open?: boolean;
}

export interface CriarMenuCommand {
  titulo: string;
  icone: string;
  url: string;
  descricao: string;
  menuPaiId: string;
}

export interface AlterarMenuCommand {
  id: string;
  titulo: string;
  icone: string;
  url: string;
  descricao: string;
  menuPaiId: string;
}
