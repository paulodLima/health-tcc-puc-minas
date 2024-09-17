import { Injectable } from '@angular/core';
import { MessageService } from 'primeng/api'; // Exemplo com PrimeNG

@Injectable({
  providedIn: 'root'
})
export class MyMessageService {
  constructor(private messageService: MessageService) {}

  addError(messages: string[]) {
    messages.forEach(message => {
      this.messageService.add({ severity: 'error', summary: 'Erro', detail: message });
    });
  }
  addSucess(messages: string[]) {
    messages.forEach(message => {
      this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: message });

    });
  }
}
