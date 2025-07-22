export const environment = {
  production: false,
  apiBaseUrl: 'http://localhost:8080', // URL da API local
  authConfig: {
    loginUrl: '/auth/login',
    tokenStorageKey: 'auth-token',
    userInfoStorageKey: 'user-info',
    protectedRoutes: ['/dashboard', '/perfil', '/admin'],
    publicRoutes: ['/login', '/cadastro', '/recuperar-senha']
  },
  features: {
    enableDebug: true,
    logRequests: true
  }
};
