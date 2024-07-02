// src/app/guards/auth.guard.ts
import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthorizationService } from '../services/authorization.service';



export const AuthGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthorizationService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    return true;
  } else {
    router.navigate(['/home']); 
    return false;
  }
};