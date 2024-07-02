import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private readonly http: HttpClient) { }

  logout(){
    return this.http.post('/api/logout', {}, { withCredentials: true })
  }

  testin(){
    return this.http.post<any>('/api/testin',{}, { withCredentials: true })
  }

  loadProfilePage(): Observable<User> {
    return this.http.get<any>('/api/profile', { withCredentials: true })
  }

  updateProfile(id: string, updatedProfile: any): Observable<any> {
    return this.http.put<any>(`/api/profile/edit/${id}`, updatedProfile, { withCredentials: true })
  }
}
