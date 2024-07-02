import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, tap } from "rxjs";
import { AuthorizationService } from "../services/authorization.service";



@Injectable()
export class AuthInterceptor 
    //implements HttpInterceptor 
    {

    // constructor(private authService: AuthorizationService){}

    // intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    //     return next.handle(request).pipe(
    //       tap(event => {
    //         if (event instanceof HttpResponse) {
    //           const authHeader = event.headers.get('Authorization');
    //           if (authHeader) {
    //             const token = authHeader.replace('Bearer ', '');
    //             this.authService.setToken(token);
    //           }
    //         }
    //       })
    //     );
    //   }

}