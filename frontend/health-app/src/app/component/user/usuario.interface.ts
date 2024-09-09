export interface AlterarUsuarioCommand {
    id: string,
    nome: string,
    email: string,
    password: string,
    status: boolean
}

export interface CriarUsuarioCommand {
    login: string,
    nome: string,
    email: string,
    password:string
}

export interface UsuarioDto {
    id: string,
    login: string,
    nome: string,
    email: string,
    status: boolean,
    inclusionDate: Date
}
