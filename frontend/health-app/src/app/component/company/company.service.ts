import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, take} from "rxjs";
import {TokenService} from "../../core/token.service";
import {CompanyDto} from "./company.interface";

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private http: HttpClient,private tokenService: TokenService) { }

  listAll() : Observable<CompanyDto[]>{
    const token = localStorage.getItem('access_token') ?? '';
    const headers = this.getOptionsHeaders(token);
    return this.http.get<CompanyDto[]>(`http://localhost:8080/api/company`,{headers}).pipe(take(1));
  }

  private getOptionsHeaders(token: string) {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Authorization': `Bearer ${token}`
    });
  }
}
