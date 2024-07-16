import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { registerRequest } from '../models/models';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  
  constructor(private readonly http: HttpClient) { }


  processRegistration(registerRequest: registerRequest): Observable<any> {
    return this.http.post('/api/register/process', registerRequest, { withCredentials: true })
  }
}
