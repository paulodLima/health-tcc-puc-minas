import {Component, OnInit, ViewChild} from '@angular/core';
import {ReimbursementResponseDto, UpdateStatus} from "../reimbursement.interface";
import {StatusInfo} from "../reimbursement.interface";
import {ActivatedRoute, Router} from "@angular/router";
import {ReimbursementService} from "../reimbursement.service";
import {Button, ButtonDirective} from "primeng/button";
import {Table, TableModule} from "primeng/table";
import {TagModule} from "primeng/tag";
import {ChipsModule} from "primeng/chips";
import {SplitButtonModule} from "primeng/splitbutton";
import {ConfirmationService, MenuItem, MessageService} from "primeng/api";
import {ToastModule} from "primeng/toast";
import {DropdownModule} from "primeng/dropdown";
import {ConfirmPopupModule} from "primeng/confirmpopup";
import {DialogModule} from "primeng/dialog";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgIf, NgOptimizedImage} from "@angular/common";
import {ProgressSpinnerModule} from "primeng/progressspinner";
import {Observable, switchMap, timer} from "rxjs";
import {TokenService} from "../../../core/token.service";

@Component({
  selector: 'app-reimbursement-list',
  standalone: true,
  imports: [
    Button,
    TableModule,
    TagModule,
    ButtonDirective,
    ChipsModule,
    SplitButtonModule,
    ToastModule,
    DropdownModule,
    ConfirmPopupModule,
    DialogModule,
    FormsModule,
    FormsModule,
    NgOptimizedImage,
    ProgressSpinnerModule,
    NgIf,
    ReactiveFormsModule
  ],
  providers: [
    MessageService,
    ConfirmationService
  ],
  templateUrl: './reimbursement-list.component.html',
  styleUrl: './reimbursement-list.component.scss'
})
export class ReimbursementListComponent implements OnInit {
  reimbursement: ReimbursementResponseDto[] = [];
  updateStatus: UpdateStatus = {
    observation: '',
    status: 0
  };
  display: boolean = false;
  loading: boolean = true;
  imageUrl = ''
  selectedRecord: ReimbursementResponseDto | null = null;
  observation = '';
  @ViewChild('dt') dt: Table | undefined;
  userRoles: any;
  isAdmin: boolean = false;
  id = '';

  constructor(private router: Router, private reimbursementService: ReimbursementService, private _activatedRoute: ActivatedRoute, private messageService: MessageService, private _confirmationService: ConfirmationService, private tokenService: TokenService) {
  }

  ngOnInit(): void {
    this.userRoles = this.tokenService.getRolesUser(localStorage.getItem('access_token') ?? '');
    this.isAdmin = this.checkIfAdminOrManager();
    this._activatedRoute.params.subscribe((params) => {
      if (params['id']) {
        this.reimbursementService.findAllById(params['id']).subscribe((reimbursement) => {
          this.reimbursement = reimbursement;
        });
      } else {
        this.findAllReimbursement();
      }
    });
  }

  checkIfAdminOrManager(): boolean {
    return this.userRoles.includes('admin') || this.userRoles.includes('manager');
  }

  findAllReimbursement() {
    this.reimbursementService.listAll().subscribe(reimbursements => {
      this.reimbursement = reimbursements;
    })
  }

  getStatusInfo(status: string): StatusInfo {
    return this.statusMap[status] || {label: 'Desconhecido', severity: 'default'};
  }

  statusMap: { [key: string]: StatusInfo } = {
    PENDING: {label: 'Pendente', severity: 'warning'},
    APPROVED: {label: 'Aprovado', severity: 'success'},
    REJECTED: {label: 'Rejeitado', severity: 'danger'}
  };

  onFilterInputChange(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    if (this.dt) {
      this.dt.filterGlobal(inputElement.value, 'contains');
    }
  }

  rejected() {
    if (this.selectedRecord) {
      this.updateStatus.status = 3;
      this.updateStatus.observation = this.observation;
      this.reimbursementService.updateStatus(this.selectedRecord.id, this.updateStatus).subscribe(() => {
      })
      this.findAllReimbursement();
      window.location.reload();
      this.messageService.add({
        severity: 'info',
        summary: 'Sucesso',
        detail: 'Status Atualizado com Sucesso',
        life: 3000
      });
      this.visible = false;
    }
  }

  approved(dto: ReimbursementResponseDto) {
    this.updateStatus.status = 2;
    this.updateStatus.observation = this.observation;
    this.reimbursementService.updateStatus(dto.id, this.updateStatus).subscribe(() => {
      dto.status = 'APPROVED';
    })
    this.findAllReimbursement();
    window.location.reload();
    this.messageService.add({
      severity: 'info',
      summary: 'Sucesso',
      detail: 'Status Atualizado com Sucesso',
      life: 3000
    });
    this.visible = false;
  }

  protected readonly onclick = onclick;

  visible: boolean = false;

  showDialog(registro: ReimbursementResponseDto) {
    this.visible = true;
    this.selectedRecord = registro;
  }

  visualizar(url: string) {
    console.log(url)
    this.display = true;
    this.imageUrl = url;
    this.loading = true;
    this.startLoadingTimer().subscribe(() => {
      this.loading = false; // Remove o spinner quando o timer expira
    });
  }

  private readonly MIN_LOADING_TIME = 500;

  private startLoadingTimer(): Observable<void> {
    return timer(this.MIN_LOADING_TIME).pipe(
      switchMap(() => {
        // Simula a verificação de carregamento da imagem
        return new Observable<void>((observer) => {
          const img = new Image();
          img.src = this.imageUrl;
          img.onload = () => observer.next();
          img.onerror = () => observer.error();
        });
      })
    );
  }

  onImageLoad() {
    this.loading = false; // Imagem carregada
  }

  editar(registro: any) {
    this.router.navigate([`/reembolso/${registro.id}`]);
  }
}
