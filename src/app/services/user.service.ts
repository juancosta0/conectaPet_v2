import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { UserProfile } from '../types/user.type';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users';
  private currentUserSubject = new BehaviorSubject<UserProfile | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadCurrentUserFromToken();
  }

  private loadCurrentUserFromToken(): void {
    const token = sessionStorage.getItem('auth-token');
    if (token) {
      this.http.get<UserProfile>(`${this.apiUrl}/profile`)
        .subscribe({
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
        tap((updatedUser: UserProfile) => this.currentUserSubject.next(updatedUser))
      );
  }

  getUserById(id: number): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${this.apiUrl}/${id}`);
  }
}
