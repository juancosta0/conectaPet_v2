import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from '../services/login.service';

export const authGuard: CanActivateFn = (route, state) => {
  const loginService = inject(LoginService);
  const router = inject(Router);

  const requiredRoles = route.data?.['roles'] as Array<string>;
  const userRole = loginService.getUserRole();

  if (!userRole) {
    return router.createUrlTree(['/login']);
  }

  if (requiredRoles?.length > 0 && !requiredRoles.includes(userRole)) {
    return router.createUrlTree(['/home']);
  }

  return true;
};
