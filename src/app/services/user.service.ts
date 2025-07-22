import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap, catchError, of } from 'rxjs';
import { UserProfile } from '../types/user.type';
import { environment } from '../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = `${environment.apiBaseUrl}/api/users`;
  private currentUserSubject = new BehaviorSubject<UserProfile | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  // Mock user para desenvolvimento
  private mockUser: UserProfile = {
    id: 1,
    name: 'João Silva',
    email: 'joao@email.com',
    userType: 'adotante',
    createdAt: new Date(),
    favoritesPets: [1, 4],
    adoptedPets: [],
    registeredPets: [],
    token: '',
    message: ''
  };

  constructor(private http: HttpClient) {
    this.loadCurrentUserFromToken();
  }

  private loadCurrentUserFromToken(): void {
    const token = sessionStorage.getItem('auth-token');
    if (token) {
      this.http.get<UserProfile>(`${this.apiUrl}/profile`).pipe(
        catchError((error) => {
          console.error('Erro ao carregar perfil, usando dados mock:', error);
          // Usar dados do sessionStorage se disponível
          const mockUserFromSession = {
            ...this.mockUser,
            name: sessionStorage.getItem('username') || this.mockUser.name,
            email: sessionStorage.getItem('user-email') || this.mockUser.email,
            userType: sessionStorage.getItem('user-type') as 'adotante' | 'ong' || this.mockUser.userType
          };
          return of(mockUserFromSession);
        })
      ).subscribe({
        next: (user) => this.currentUserSubject.next(user),
        error: () => this.currentUserSubject.next(null)
      });
    }
  }

  getCurrentUser(): UserProfile | null {
    return this.currentUserSubject.value;
  }

  updateProfile(userData: Partial<UserProfile>): Observable<UserProfile | null> {
    return this.http.put<UserProfile>(`${this.apiUrl}/profile`, userData)
      .pipe(
        tap((updatedUser: UserProfile) => this.currentUserSubject.next(updatedUser)),
        catchError((error) => {
          console.error('Erro ao atualizar perfil:', error);
          throw error;
        })
      );
  }

  getUserById(id: number): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${this.apiUrl}/${id}`).pipe(
      catchError((error) => {
        console.error('Erro ao buscar usuário por ID:', error);
        return of(this.mockUser);
      })
    );
  }
}
