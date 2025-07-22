export const environment = {
  production: true,
  apiBaseUrl: 'https://api.conectapet.com', // URL da API em produção
  authConfig: {
    loginUrl: '/auth/login',
    tokenStorageKey: 'auth-token',
    userInfoStorageKey: 'user-info',
    protectedRoutes: ['/dashboard', '/perfil', '/admin'],
    publicRoutes: ['/', '/login', '/cadastro', '/recuperar-senha']
  },
  features: {
    enableDebug: false,
    logRequests: false
  }
};
