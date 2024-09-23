import { AbstractControl, ValidationErrors } from '@angular/forms';

export function cnpjValidator(control: AbstractControl): ValidationErrors | null {
  const cnpj = control.value;

  if (!cnpj) return null;

  const cleanedCnpj = cnpj.replace(/[^\d]+/g, '');

  if (cleanedCnpj.length !== 14) return { invalidCnpj: true };

  let sum = 0;
  for (let i = 0; i < 12; i++) {
    sum += parseInt(cleanedCnpj.charAt(i), 10) * ((i < 4) ? (5 - i) : (13 - i));
  }
  const remainder = sum % 11;
  const firstDigit = remainder < 2 ? 0 : 11 - remainder;
  if (parseInt(cleanedCnpj.charAt(12), 10) !== firstDigit) return { invalidCnpj: true };

  sum = 0;
  for (let i = 0; i < 13; i++) {
    sum += parseInt(cleanedCnpj.charAt(i), 10) * ((i < 5) ? (6 - i) : (14 - i));
  }
  const secondRemainder = sum % 11;
  const secondDigit = secondRemainder < 2 ? 0 : 11 - secondRemainder;
  if (parseInt(cleanedCnpj.charAt(13), 10) !== secondDigit) return { invalidCnpj: true };

  return null;
}
