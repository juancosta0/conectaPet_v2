import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginService } from './login.service';
import { catchError, Observable, throwError } from 'rxjs';
import { IUsuario } from '../model/usuario';
import { environment } from '../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = `${environment.apiBaseUrl}/usuarios`;

  constructor(
    private http: HttpClient,
    private loginService: LoginService
  ) { }

   private getAuthHeaders(): HttpHeaders {
      const token = sessionStorage.getItem('auth-token');
      return new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      });
  }

  getUsuario(id: number): Observable<IUsuario> {
    return this.http.get<IUsuario>(`${this.apiUrl}/${id}`, {
      headers: this.getAuthHeaders()
    }).pipe(
      catchError(this.handleError)
    );
  }

   private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Erro ao buscar usuário';

    if (error.status === 401) {
      errorMessage = 'Sessão expirada. Faça login novamente.';
    } else if (error.status === 403) {
      errorMessage = 'Você não tem permissão para acessar este recurso';
    } else if (error.status === 404) {
      errorMessage = 'Usuário não encontrado';
    } else if (error.error instanceof ErrorEvent) {
      errorMessage = `Erro: ${error.error.message}`;
    } else {
      errorMessage = `Código do erro: ${error.status}\nMensagem: ${error.message}`;
    }

    return throwError(() => new Error(errorMessage));
  }
}
