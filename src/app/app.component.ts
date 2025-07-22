import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { MaterialModules } from './material';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from './services/auth.service';

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

  constructor(private authService: AuthService) {
    this.authService.authStatus.subscribe(status => {
      this.isLoggedIn = status;
    });
  }
}
