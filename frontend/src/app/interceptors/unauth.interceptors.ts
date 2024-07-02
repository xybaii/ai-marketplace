import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { EMPTY, Observable, catchError, throwError } from "rxjs";

@Injectable()
export class UnauthInterceptor 
//implements HttpInterceptor 
{

  // constructor(private router: Router) {}

  // intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
  //   return next.handle(req).pipe(
  //     catchError((error: HttpErrorResponse) => {
  //       if (error.status === 401) {
          
  //         this.router.navigate(['/login']);
  //       }
        
  //       console.error('HTTP Error', error);
  //       return EMPTY;
  //     })
  //   );
  // }
}