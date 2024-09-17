import {AbstractControl} from "@angular/forms";

export interface UpdateUsuarioCommand {
  id: string,
  nome: string,
  email: string,
  password: string,
  status: boolean
}

export interface CreateUsuarioCommand {
  login: string,
  name: string,
  email: string,
  password: string
  perfil: string
}

export interface UpdateUserPasswordCommand {
  id: string
  password: string,
  email: string,
  login: string,
}

export interface UserDto {
  id: string,
  login: string,
  nome: string,
  email: string,
  status: boolean,
  inclusionDate: Date
}

export function noSpecialCharsAndSpaces(control: AbstractControl) {
  const value = control.value || '';
  const forbidden = /[\s!@#$%^&*(),.?":{}|<>]/; // Regex para caracteres especiais e espaços
  return forbidden.test(value) ? {forbiddenChar: true} : null;
}
