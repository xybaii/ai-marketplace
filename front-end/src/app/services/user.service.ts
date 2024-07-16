import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models/models';
import { Store } from '@ngrx/store';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  
  constructor(private readonly http: HttpClient, private readonly store: Store<{ user: any }>) { }

  private userId: string | null = null;


  logout(){
    return this.http.post('/api/logout', {}, { withCredentials: true })
  }

  loadProfilePage(): Observable<User> {
    return this.http.get<any>('/api/profile', { withCredentials: true })
  }

  updateProfile(id: string, updatedProfile: any): Observable<any> {
    return this.http.put<any>(`/api/profile/edit/${id}`, updatedProfile, { withCredentials: true })
  }

}
