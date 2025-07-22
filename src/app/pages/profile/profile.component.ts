import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PrimaryInputComponent } from '../../components/primary-input/primary-input.component';
import { UserProfile } from '../../types/user.type';
import { UserService } from '../../services/user.service';
import { ToastrService } from 'ngx-toastr';

interface ProfileForm {
  name: FormControl<string | null>;
  email: FormControl<string | null>;
  cnpj: FormControl<string | null>;
  description: FormControl<string | null>;
}

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, PrimaryInputComponent],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user: UserProfile | null = null;
  isEditMode = false;
  profileForm!: FormGroup<ProfileForm>;

  constructor(
    private userService: UserService,
    private toastr: ToastrService
  ) {
    this.initializeForm();
  }

  ngOnInit(): void {
    this.loadUserProfile();
  }

  private initializeForm(): void {
    this.profileForm = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.minLength(3)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      cnpj: new FormControl(''),
      description: new FormControl('')
    });
  }

  private loadUserProfile(): void {
    this.user = this.userService.getCurrentUser();
    if (this.user) {
      this.updateFormWithUserData();
    }
  }

  private updateFormWithUserData(): void {
    if (this.user) {
      this.profileForm.patchValue({
        name: this.user.name,
        email: this.user.email,
        cnpj: this.user.cnpj || '',
        description: this.user.description || ''
      });
    }
  }

  getUserTypeLabel(): string {
    if (!this.user) return '';
    return this.user.userType === 'ong' ? 'ONG' : 'Adotante';
  }

  formatDate(date: Date): string {
    return new Intl.DateTimeFormat('pt-BR', {
      year: 'numeric',
      month: 'long'
    }).format(new Date(date));
  }

  toggleEditMode(): void {
    this.isEditMode = !this.isEditMode;
    if (this.isEditMode) {
      this.updateFormWithUserData();
    }
  }

  cancelEdit(): void {
    this.isEditMode = false;
    this.updateFormWithUserData();
  }

  saveProfile(): void {
    if (this.profileForm.valid && this.user) {
      const formData = this.profileForm.value;
      const updateData: Partial<UserProfile> = {
        name: formData.name || '',
        email: formData.email || ''
      };

      if (this.user.userType === 'ong') {
        updateData.cnpj = formData.cnpj || '';
        updateData.description = formData.description || '';
      }

      this.userService.updateProfile(updateData).subscribe({
        next: (updatedUser) => {
          if (updatedUser) {
          this.user = updatedUser;
          this.isEditMode = false;
          this.toastr.success('Perfil atualizado com sucesso!');
          }
        },
        error: () => {
          this.toastr.error('Erro ao atualizar perfil. Tente novamente.');
        }
      });
    } else {
      this.toastr.error('Por favor, preencha todos os campos obrigatórios.');
    }
  }
}
