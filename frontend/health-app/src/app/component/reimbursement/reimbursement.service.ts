import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {CreateReimbursementCommand, ReimbursementResponseDto, UpdateStatus} from "./reimbursement.interface";
import {Observable, take} from "rxjs";
import {TokenService} from "../../core/token.service";

@Injectable({
  providedIn: 'root'
})
export class ReimbursementService {

  constructor(private http: HttpClient, private tokenService: TokenService) {
  }

  listAll(): Observable<ReimbursementResponseDto[]> {
    return this.http.get<ReimbursementResponseDto[]>(`http://localhost:8080/api/reimbursement`,).pipe(take(1));
  }

  updateStatus(id: string, updateStatus: UpdateStatus): Observable<void> {
    return this.http.put<void>(`http://localhost:8080/api/reimbursement/update-status/${id}`, updateStatus,).pipe(take(1));
  }

  create(command: FormData): Observable<string> {
    return this.http.post<string>(`http://localhost:8080/api/reimbursement`, command,).pipe(take(1));
  }

  findById(id: string): Observable<ReimbursementResponseDto> {
    return this.http.get<ReimbursementResponseDto>(`http://localhost:8080/api/reimbursement/${id}`,).pipe(take(1));
  }

  update(id: string,command: FormData): Observable<string> {
    return this.http.put<string>(`http://localhost:8080/api/reimbursement/${id}`, command,).pipe(take(1));
  }

  findAllById(id: string): Observable<ReimbursementResponseDto[]>  {
    return this.http.get<ReimbursementResponseDto[]>(`http://localhost:8080/api/reimbursement/find-user/${id}`,).pipe(take(1));
  }
}
