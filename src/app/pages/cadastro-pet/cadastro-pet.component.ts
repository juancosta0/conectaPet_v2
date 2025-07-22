import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PrimaryInputComponent } from '../../components/primary-input/primary-input.component';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CommonModule } from '@angular/common';
import { PrimaryButtonComponent } from '../../components/primary-button/primary-button.component';

interface PetForm {
  nome: FormControl<string | null>;
  tipoAnimal: FormControl<string | null>;
  raca: FormControl<string | null>;
  idade: FormControl<number | null>;
  porte: FormControl<string | null>;
  cor: FormControl<string | null>;
  descricao: FormControl<string | null>;
  imagens: FormControl<File[] | null>;
}

@Component({
  selector: 'app-cadastro-pet',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    PrimaryInputComponent,
    PrimaryButtonComponent
  ],
  templateUrl: './cadastro-pet.component.html',
  styleUrls: ['./cadastro-pet.component.scss']
})
export class CadastroPetComponent {
  petForm: FormGroup<PetForm>;
  previewUrls: (string | ArrayBuffer)[] = [];
  maxImages = 5;

  constructor(
    private router: Router,
    private toastr: ToastrService
  ) {
    this.petForm = new FormGroup({
      nome: new FormControl('', [Validators.required, Validators.minLength(2)]),
      tipoAnimal: new FormControl('', [Validators.required]),
      raca: new FormControl('', [Validators.required]),
      idade: new FormControl<number | null>(null, [Validators.required, Validators.min(0), Validators.max(30)]),
      porte: new FormControl('', [Validators.required]),
      cor: new FormControl('', [Validators.required]),
      descricao: new FormControl('', [Validators.required, Validators.minLength(20), Validators.maxLength(300)]),
      imagens: new FormControl<File[]>([])
    });
  }

  onFileSelected(event: Event): void {
    const files = (event.target as HTMLInputElement).files;
    if (files) {
      const currentFiles = this.petForm.get('imagens')?.value || [];
      const newFiles = Array.from(files);

      // Verifica o limite de imagens
      if (currentFiles.length + newFiles.length > this.maxImages) {
        this.toastr.warning(`Máximo de ${this.maxImages} fotos permitidas!`);
        return;
      }

      // Verifica o tamanho dos arquivos (máximo 5MB por arquivo)
      const maxSize = 5 * 1024 * 1024; // 5MB
      const oversizedFiles = newFiles.filter(file => file.size > maxSize);
      if (oversizedFiles.length > 0) {
        this.toastr.error('Algumas imagens são muito grandes. Máximo 5MB por foto.');
        return;
      }

      // Atualiza o valor no formulário
      this.petForm.patchValue({ imagens: [...currentFiles, ...newFiles] });
      this.petForm.get('imagens')?.updateValueAndValidity();

      // Gera as URLs para pré-visualização
      newFiles.forEach(file => {
        const reader = new FileReader();
        reader.onload = () => {
          this.previewUrls.push(reader.result as string);
        };
        reader.readAsDataURL(file);
      });

      this.toastr.success(`${newFiles.length} foto(s) adicionada(s)!`);
    }
  }

  removeImage(index: number): void {
    // Remove a URL de pré-visualização
    this.previewUrls.splice(index, 1);

    // Remove o arquivo do FormControl
    const currentFiles = this.petForm.get('imagens')?.value || [];
    currentFiles.splice(index, 1);
    this.petForm.patchValue({ imagens: currentFiles });
    this.petForm.get('imagens')?.updateValueAndValidity();

    this.toastr.info('Foto removida!');
  }

  getDescriptionLength(): number {
    return this.petForm.get('descricao')?.value?.length || 0;
  }

  submit() {
    if (this.petForm.valid) {
      const formData = this.petForm.value;

      // Validações adicionais
      if (!this.previewUrls.length) {
        this.toastr.warning('Adicione pelo menos uma foto do pet!');
        return;
      }

      console.log('Dados do pet:', formData);
      console.log('Número de imagens:', this.previewUrls.length);

      this.toastr.success('Pet cadastrado com sucesso! 🎉');
      this.router.navigate(['/feed']);
    } else {
      this.toastr.error('Por favor, preencha todos os campos obrigatórios.');
      this.markFormGroupTouched();
    }
  }

  private markFormGroupTouched(): void {
    Object.keys(this.petForm.controls).forEach(key => {
      const control = this.petForm.get(key);
      control?.markAsTouched();
    });
  }

  navigateToFeed() {
    this.router.navigate(['/feed']);
  }

  resetForm() {
    this.petForm.reset();
    this.previewUrls = [];
    this.toastr.info('Formulário limpo!');
  }

}
