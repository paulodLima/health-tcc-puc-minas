import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, take} from "rxjs";
import {TokenService} from "../../core/token.service";
import {CompanyDto, CreateCompanyCommand, UpdateCompanyCommand} from "./company.interface";

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private http: HttpClient,private tokenService: TokenService) { }

  listAll() : Observable<CompanyDto[]>{
    return this.http.get<CompanyDto[]>(`http://localhost:8080/api/company`,).pipe(take(1));
  }
  listAllActive() : Observable<CompanyDto[]>{
    return this.http.get<CompanyDto[]>(`http://localhost:8080/api/company/active`,).pipe(take(1));
  }

  create(command: CreateCompanyCommand): Observable<string> {
    return this.http.post<string>(`http://localhost:8080/api/company`, command,).pipe(take(1));
  }

  findById(id: string): Observable<CompanyDto> {
    return this.http.get<CompanyDto>(`http://localhost:8080/api/company/${id}`,).pipe(take(1));
  }

  update(command: UpdateCompanyCommand): Observable<CompanyDto> {
    return this.http.put<CompanyDto>(`http://localhost:8080/api/company/${command.id}`,command,).pipe(take(1));
  }
}
