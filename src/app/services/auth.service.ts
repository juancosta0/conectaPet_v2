import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _isAuthenticated = new BehaviorSubject<boolean>(false);
  public isAuthenticated$ = this._isAuthenticated.asObservable();

  constructor(private router: Router) {
    // Verifica o token no sessionStorage quando o serviço é inicializado
    const token = sessionStorage.getItem('auth-token');
    this._isAuthenticated.next(!!token);
  }

  login() {
    // lógica de login
    this._isAuthenticated.next(true);
  }

  logout(): void {
    sessionStorage.removeItem('auth-token');
    sessionStorage.removeItem('username');
    this.router.navigate(['/login']);
    this._isAuthenticated.next(false);
  }

  isAuthenticated(): boolean {
    return this._isAuthenticated.value;
  }
}
