import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PetCardComponent } from '../../components/pet-card/pet-card.component';
import { Pet } from '../../types/pet.type';
import { PetService } from '../../services/pet.service';
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
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadPets();
    this.route.queryParams.subscribe(params => {
      this.showFavorites = params['favorites'] === 'true';
    });
  }

  private loadPets(): void {
    this.petService.getAllPets().subscribe({
      next: (pets) => {
        this.pets = pets;
      },
      error: (error) => {
        console.error('Erro ao carregar pets:', error);
      }
    });
  }

  onFavoriteToggled(event: { petId: number, isFavorite: boolean }): void {
    // Lógica adicional se necessário
  }
}
