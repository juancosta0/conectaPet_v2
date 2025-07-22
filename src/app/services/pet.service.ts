import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Pet } from '../types/pet.type';

@Injectable({
  providedIn: 'root'
})
export class PetService {
  private apiUrl = 'http://localhost:8080/api/pets';

  // Mock data para desenvolvimento
  private mockPets: Pet[] = [
    {
      id: 1, name: 'Caramelo', species: 'Cachorro', breed: 'SRD', age: 2,
      size: 'Médio', color: 'Caramelo', description: 'Amigável e brincalhão. Adora brincar no quintal e é muito carinhoso com crianças.',
      imageUrls: ['https://images.pexels.com/photos/1805164/pexels-photo-1805164.jpeg'],
      isFavorited: true
    },
    {
      id: 2, name: 'Mimi', species: 'Gato', breed: 'Siamês', age: 3, size: 'Pequeno',
      color: 'Branco e Cinza', description: 'Calma e carinhosa. Gosta de ficar no colo e é muito tranquila.',
      imageUrls: ['https://images.pexels.com/photos/45201/kitty-cat-kitten-pet-45201.jpeg'],
      isFavorited: false
    },
    {
      id: 3, name: 'Rex', species: 'Cachorro', breed: 'Pastor Alemão', age: 4,
      size: 'Grande', color: 'Preto e Marrom', description: 'Leal e inteligente. Excelente cão de guarda e muito obediente.',
      imageUrls: ['https://images.pexels.com/photos/333083/pexels-photo-333083.jpeg'],
      isFavorited: false
    },
    {
      id: 4, name: 'Luna', species: 'Gato', breed: 'SRD', age: 1, size: 'Pequeno',
      color: 'Frajola', description: 'Curiosa e cheia de energia. Adora explorar e brincar com bolinhas.',
      imageUrls: ['https://images.pexels.com/photos/416160/pexels-photo-416160.jpeg'],
      isFavorited: true
    }
  ];

  constructor(private http: HttpClient) {}

  getAllPets(): Observable<Pet[]> {
    return this.http.get<Pet[]>(this.apiUrl);
  }

  getPetById(id: number): Observable<Pet> {
    return this.http.get<Pet>(`${this.apiUrl}/${id}`);
  }

  getFavoritesPets(favoriteIds: number[]): Observable<Pet[]> {
    const params = favoriteIds.join(',');
    return this.http.get<Pet[]>(`${this.apiUrl}/favorites?ids=${params}`);
  }

  createPet(petData: Pet): Observable<Pet> {
    return this.http.post<Pet>(this.apiUrl, petData);
  }
}
