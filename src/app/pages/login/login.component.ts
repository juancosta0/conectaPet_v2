import { Component } from '@angular/core';
import { DefaultLoginLayoutComponent } from '../../components/default-login-layout/default-login-layout.component';
import { FormControl, FormGroup, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';
import { PrimaryInputComponent } from '../../components/primary-input/primary-input.component';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { ToastrService } from 'ngx-toastr';
import { MatIcon } from '@angular/material/icon';
import { SecundaryButtonComponent } from '../../components/secundary-button/secundary-button.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    PrimaryInputComponent,
    SecundaryButtonComponent,
    MatIcon
  ],
  providers: [
    LoginService
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginForm!: FormGroup;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private toast: ToastrService
  ){
    this.loginForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)])
    })
  }

 submit(){
    if (this.loginForm.valid) {
        this.loginService.login(this.loginForm.value.email as string, this.loginForm.value.password as string).subscribe({
            next: () => this.router.navigate(["feed"]),
            error: (err) => {
        {
                console.error("Erro no login do Angular:", err);
                this.toast.error("Email ou senha incorretos!");
      };
            }
        })
    } else {
        console.warn('Formulário inválido. Não enviando.');
    }
}

  navigate(){
    this.router.navigate(["cadastro"])
  }

  resetForm(form: NgForm) {
    form.resetForm();
  }


}
