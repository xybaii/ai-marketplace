import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { loginRequest } from '../models/models';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private readonly http: HttpClient) { }


  processLogin(loginRequest: loginRequest): Observable<any> {
    return this.http.post('/api/login/process', loginRequest, { withCredentials: true })
  }

  
}
