import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { MaterialModules } from './material';
import { HttpClientModule } from '@angular/common/http';
import { LoginService } from './services/login.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, MaterialModules, HttpClientModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'conectaPet';
  isLoggedIn = false;

  constructor(private loginService: LoginService) {
    this.isLoggedIn = this.loginService.isLoggedIn();
  }
}
