import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";

import { Observable } from "rxjs";
import { AuthorizationService } from "../services/authorization.service";


@Injectable()
export class AuthRequestInterceptor 
// implements HttpInterceptor 
{

//   constructor(private authService: AuthorizationService) {}

//   intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
//     const token = this.authService.getToken();
//     if (token) {
//       request = request.clone({
//         setHeaders: {
//           Authorization: `Bearer ${token}`
//         }
//       });
//     }
//     return next.handle(request);
//   }
}