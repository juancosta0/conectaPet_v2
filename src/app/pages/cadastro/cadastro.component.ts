import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PrimaryInputComponent } from '../../components/primary-input/primary-input.component';
import { SecundaryButtonComponent } from '../../components/secundary-button/secundary-button.component';
import { LoginService } from '../../services/login.service';
import { ToastrService } from 'ngx-toastr';
import { MatIcon } from '@angular/material/icon';

interface RegisterForm {
  name: FormControl<string | null>;
  email: FormControl<string | null>;
  password: FormControl<string | null>;
  userType: FormControl<string | null>;
  cnpj: FormControl<string | null>;
  description: FormControl<string | null>;
}

@Component({
  selector: 'app-cadastro',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    PrimaryInputComponent,
    SecundaryButtonComponent,
    MatIcon
  ],
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.scss']
})
export class CadastroComponent {
  registerForm: FormGroup<RegisterForm>;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private toastr: ToastrService
  ) {
    this.registerForm = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.minLength(3)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
      userType: new FormControl('', [Validators.required]),
      cnpj: new FormControl(''),
      description: new FormControl('')
    });

    // Adicionar validações condicionais para ONG
    this.registerForm.get('userType')?.valueChanges.subscribe(userType => {
      const cnpjControl = this.registerForm.get('cnpj');
      const descriptionControl = this.registerForm.get('description');

      if (userType === 'ong') {
        cnpjControl?.setValidators([Validators.required]);
        descriptionControl?.setValidators([Validators.required, Validators.minLength(20)]);
      } else {
        cnpjControl?.clearValidators();
        descriptionControl?.clearValidators();
      }

      cnpjControl?.updateValueAndValidity();
      descriptionControl?.updateValueAndValidity();
    });
  }

  get isONG(): boolean {
    return this.registerForm.get('userType')?.value === 'ong';
  }

  submit(): void {
    if (this.registerForm.valid) {
      const formData = this.registerForm.value;
      
      this.loginService.register(formData).subscribe({
        next: () => {
          this.toastr.success('Cadastro realizado com sucesso!');
          this.router.navigate(['/feed']);
        },
        error: (error) => {
          console.error('Erro no cadastro:', error);
          this.toastr.error('Erro ao realizar cadastro. Tente novamente.');
        }
      });
    } else {
      this.toastr.error('Por favor, preencha todos os campos obrigatórios.');
      this.markFormGroupTouched();
    }
  }

  private markFormGroupTouched(): void {
    Object.keys(this.registerForm.controls).forEach(key => {
      const control = this.registerForm.get(key);
      control?.markAsTouched();
    });
  }
}