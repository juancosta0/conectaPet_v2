import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PetCardComponent } from '../../components/pet-card/pet-card.component';
import { Pet } from '../../types/pet.type';
import { PetService } from '../../services/pet.service';
import { FavoritesService } from '../../services/favorites.service';
import { MatCardModule } from '@angular/material/card';
import { MatIcon } from '@angular/material/icon';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [CommonModule, PetCardComponent, MatCardModule, MatIcon],
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss']
})
export class FeedComponent {
  pets: Pet[] = [];
  showFavorites = false;

  constructor(
    private petService: PetService,
    private favoritesService: FavoritesService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.showFavorites = params['favorites'] === 'true';
      this.loadPets();
    });
  }

  private loadPets(): void {
    if (this.showFavorites) {
      const favoriteIds = this.favoritesService.getFavorites();
      if (favoriteIds.length > 0) {
        this.petService.getFavoritesPets(favoriteIds).subscribe({
          next: (pets) => {
            this.pets = pets;
          },
          error: (error) => {
            console.error('Erro ao carregar pets favoritos:', error);
            this.pets = [];
          }
        });
      } else {
        this.pets = [];
      }
    } else {
      this.petService.getAllPets().subscribe({
        next: (pets) => {
          this.pets = pets;
        },
        error: (error) => {
          console.error('Erro ao carregar pets:', error);
        }
      });
    }
  }

  onFavoriteToggled(event: { petId: number, isFavorite: boolean }): void {
    if (this.showFavorites && !event.isFavorite) {
      // Remove o pet da lista se estivermos na tela de favoritos e ele foi desfavoritado
      this.pets = this.pets.filter(pet => pet.id !== event.petId);
    }
  }
}
