import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginRequest } from '../models/product';


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private apiUrl = 'https://app-productcatalog-backend-ckh0dsdyaba3deb5.westus3-01.azurewebsites.net/api/auth/login';

  constructor(private http: HttpClient) {}

  login(request: LoginRequest): Observable<string> {
    return this.http.post(this.apiUrl, request, {
      responseType: 'text'
    });
  }
}
