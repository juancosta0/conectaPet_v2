import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { Pet } from '../../types/pet.type';
import { PetService } from '../../services/pet.service';
import { FavoritesService } from '../../services/favorites.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-pet-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './pet-details.component.html',
  styleUrls: ['./pet-details.component.scss']
})
export class PetDetailsComponent implements OnInit {
  pet: Pet | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private petService: PetService,
    private favoritesService: FavoritesService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    const petId = Number(this.route.snapshot.paramMap.get('id'));
    if (petId) {
      this.loadPet(petId);
    } else {
      this.router.navigate(['/feed']);
    }
  }

  private loadPet(id: number): void {
    this.petService.getPetById(id).subscribe({
      next: (pet) => {
        if (pet) {
          this.pet = pet;
        } else {
          this.toastr.error('Pet não encontrado');
          this.router.navigate(['/feed']);
        }
      },
      error: () => {
        this.toastr.error('Erro ao carregar informações do pet');
        this.router.navigate(['/feed']);
      }
    });
  }

  get isFavorite(): boolean {
    return this.pet ? this.favoritesService.isFavorite(this.pet.id) : false;
  }

  toggleFavorite(): void {
    if (!this.pet) return;

    if (this.isFavorite) {
      this.favoritesService.removeFromFavorites(this.pet.id);
      this.toastr.success(`${this.pet.name} removido dos favoritos`);
    } else {
      this.favoritesService.addToFavorites(this.pet.id);
      this.toastr.success(`${this.pet.name} adicionado aos favoritos`);
    }
  }
}
