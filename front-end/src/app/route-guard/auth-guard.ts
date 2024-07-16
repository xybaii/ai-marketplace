import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from "@angular/router";
import { AuthorizationService } from "../services/authorization.service";


  export const authGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean => {

    const authService = inject(AuthorizationService)
    const router = inject(Router)

    if (authService.isLoggedIn()){
        return true
    }
    router.navigate(['/login'])
    return false

  }