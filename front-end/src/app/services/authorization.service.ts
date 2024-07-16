import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  private tokenKey = 'jwtToken';
  private sessionIdKey = 'sessionId';


  constructor(private cookieService: CookieService) {}

  removeToken(): void {
    this.cookieService.delete(this.tokenKey, '/', 'graceful-unity-production.up.railway.app', true, 'Strict');  
    this.cookieService.delete(this.sessionIdKey, '/');
    this.cookieService.deleteAll('/', 'graceful-unity-production.up.railway.app', true, 'Strict');
  }  

  getToken(): string | undefined {
    return this.cookieService.get(this.sessionIdKey);
  }

  isLoggedIn(): boolean {
    return !!this.getToken(); 
  }

  

}
