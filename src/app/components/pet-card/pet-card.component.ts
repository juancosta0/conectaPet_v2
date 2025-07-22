import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Pet } from '../../types/pet.type';
import { CommonModule } from '@angular/common';
import { FavoritesService } from '../../services/favorites.service';
import { Router } from '@angular/router';
import { MatIcon } from '@angular/material/icon';
import {MatChipsModule} from '@angular/material/chips';
import { MatCard, MatCardModule } from '@angular/material/card';
import { SecundaryButtonComponent } from '../secundary-button/secundary-button.component';

@Component({
  selector: 'app-pet-card',
  standalone: true,
  imports: [CommonModule, MatIcon, MatChipsModule, MatCardModule, SecundaryButtonComponent],
  templateUrl: './pet-card.component.html',
  styleUrls: ['./pet-card.component.scss']
})
export class PetCardComponent {
  @Input({ required: true }) pet!: Pet;
  @Output() favoriteToggled = new EventEmitter<{ petId: number, isFavorite: boolean }>();

  constructor(
    private favoritesService: FavoritesService,
    private router: Router
  ) {}

  get isFavorite(): boolean {
    return this.favoritesService.isFavorite(this.pet.id);
  }

  toggleFavorite(event: Event): void {
    event.stopPropagation();

    if (this.isFavorite) {
      this.favoritesService.removeFromFavorites(this.pet.id);
    } else {
      this.favoritesService.addToFavorites(this.pet.id);
    }

    this.favoriteToggled.emit({
      petId: this.pet.id,
      isFavorite: !this.isFavorite
    });
  }

  onCardClick(): void {
    this.router.navigate(['/pet', this.pet.id]);
  }
}
