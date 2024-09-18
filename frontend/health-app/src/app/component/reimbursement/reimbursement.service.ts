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
    const headers = this.getOptionsHeaders();
    return this.http.get<ReimbursementResponseDto[]>(`http://localhost:8080/api/reimbursement`, {headers}).pipe(take(1));
  }

  updateStatus(id: string, updateStatus: UpdateStatus): Observable<void> {
    const headers = this.getOptionsHeaders();
    return this.http.put<void>(`http://localhost:8080/api/reimbursement/update-status/${id}`, updateStatus, {headers}).pipe(take(1));
  }

  create(command: FormData): Observable<string> {
    const headers = this.getOptionsHeadersUpload();
    return this.http.post<string>(`http://localhost:8080/api/reimbursement`, command, {headers}).pipe(take(1));
  }

  private getOptionsHeaders() {
    const token = localStorage.getItem('access_token') ?? '';
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Authorization': `Bearer ${token}`
    });
  }

  private getOptionsHeadersUpload() {
    const token = localStorage.getItem('access_token') ?? '';
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  findById(id: string): Observable<ReimbursementResponseDto> {
    const headers = this.getOptionsHeaders();
    return this.http.get<ReimbursementResponseDto>(`http://localhost:8080/api/reimbursement/${id}`, {headers}).pipe(take(1));
  }

  update(id: string,command: FormData): Observable<string> {
    const headers = this.getOptionsHeadersUpload();
    return this.http.put<string>(`http://localhost:8080/api/reimbursement/${id}`, command, {headers}).pipe(take(1));
  }

  findAllById(id: string): Observable<ReimbursementResponseDto[]>  {
    const headers = this.getOptionsHeaders();
    return this.http.get<ReimbursementResponseDto[]>(`http://localhost:8080/api/reimbursement/find-user/${id}`, {headers}).pipe(take(1));
  }
}
