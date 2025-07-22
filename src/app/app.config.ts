import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideToastr } from 'ngx-toastr';
import { AuthInterceptor } from './interceptors/auth.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideAnimationsAsync(),
    provideHttpClient(
      withInterceptors([
        AuthInterceptor
      ])
    ),
    provideToastr({
      timeOut: 10000, // Tempo de exibição padrão do toast
      positionClass: 'toast-bottom-center', // Posição
      preventDuplicates: true, // Evita toasts duplicados
    }),
  ]
};
