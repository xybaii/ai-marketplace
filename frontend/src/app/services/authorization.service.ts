import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {


  private tokenKey = 'jwtToken';

  // getToken(): string | null {
  //   return localStorage.getItem(this.tokenKey);
  // }

  // setToken(token: string): void {
  //   localStorage.setItem(this.tokenKey, token);
  // }

  // removeToken(): void {
  //   localStorage.removeItem(this.tokenKey);
  // }

  // isLoggedIn(): boolean {
  //   return this.getToken() !== null;
  // }

  constructor(private cookieService: CookieService) {}

  getToken(): string | null {
    return this.cookieService.get(this.tokenKey);
  }

  removeToken(): void {
    this.cookieService.delete(this.tokenKey, '/api', 'localhost', true, 'Strict');  
  }

  isLoggedIn(): boolean {
    return this.getToken() !== '';
  }
}
