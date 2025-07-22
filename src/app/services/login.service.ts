import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { AuthService } from './auth.service';
import { LoginResponse } from '../types/login-response';
import { environment } from '../environment/environment';

interface LoginRequest {
  email: string;
  password: string;
}

interface RegisterRequest {
  name: string;
  email: string;
  password: string;
  userType: string;
  cnpj?: string;
  description?: string;
}

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private apiUrl = `${environment.apiBaseUrl}/auth`;

  constructor(
    private httpClient: HttpClient,
    private authService: AuthService
  ) { }

  login(email: string, password: string): Observable<LoginResponse> {
    const loginData: LoginRequest = { email, password };
    
    return this.httpClient.post<LoginResponse>(`${this.apiUrl}/login`, loginData).pipe(
      tap((response) => {
        sessionStorage.setItem("auth-token", response.token);
        sessionStorage.setItem("username", response.name);
        sessionStorage.setItem("user-email", response.email);
        sessionStorage.setItem("user-type", response.userType);
        this.authService.login();
      })
    );
  }

  register(userData: RegisterRequest): Observable<LoginResponse> {
    return this.httpClient.post<LoginResponse>(`${this.apiUrl}/register`, userData).pipe(
      tap((response) => {
        sessionStorage.setItem("auth-token", response.token);
        sessionStorage.setItem("username", response.name);
        sessionStorage.setItem("user-email", response.email);
        sessionStorage.setItem("user-type", response.userType);
        this.authService.login();
      })
    );
  }

  logout(): void {
    sessionStorage.removeItem("auth-token");
    sessionStorage.removeItem("username");
    sessionStorage.removeItem("user-email");
    sessionStorage.removeItem("user-type");
    this.authService.logout();
  }

  getUserRole(): string | null {
    return sessionStorage.getItem("user-type");
  }

  getUserEmail(): string | null {
    return sessionStorage.getItem("user-email");
  }

  getUsername(): string | null {
    return sessionStorage.getItem("username");
  }

  isLoggedIn(): boolean {
    return !!sessionStorage.getItem("auth-token");
  }

  hasRole(role: string): boolean {
    const userRole = this.getUserRole();
    return userRole === role;
  }
}
