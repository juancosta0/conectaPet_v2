import { Component } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { Router } from '@angular/router';
import { IUsuario } from '../../model/usuario';

@Component({
  selector: 'app-usuario-toolbar-info',
  standalone: true,
  imports: [],
  templateUrl: './usuario-toolbar-info.component.html',
  styleUrl: './usuario-toolbar-info.component.scss'
})
export class UsuarioToolbarInfoComponent {
  usuario?: IUsuario;

  constructor(
    private loginService: LoginService,
    private router: Router
  ) {}

  ngOnInit() {
    const id = this.loginService.getUsuarioId();
    if (id) {
      this.loginService.carregarUsuario(id).subscribe({
        next: (usuario) => this.usuario = usuario,
        error: () => console.warn('Usuário não encontrado')
      });
    }
  }

  logout() {
    this.loginService.logout();
    window.location.reload(); // recarrega para forçar atualização do app.component
  }

}
