import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FavoritesService {
  private favoritesKey = 'pet-favorites';
  private favoritesSubject = new BehaviorSubject<number[]>(this.getFavoritesFromStorage());
  public favorites$ = this.favoritesSubject.asObservable();

  constructor() {}

  private getFavoritesFromStorage(): number[] {
    const favorites = localStorage.getItem(this.favoritesKey);
    return favorites ? JSON.parse(favorites) : [];
  }

  private saveFavoritesToStorage(favorites: number[]): void {
    localStorage.setItem(this.favoritesKey, JSON.stringify(favorites));
    this.favoritesSubject.next(favorites);
  }

  getFavorites(): number[] {
    return this.favoritesSubject.value;
  }

  isFavorite(petId: number): boolean {
    return this.getFavorites().includes(petId);
  }

  addToFavorites(petId: number): void {
    const favorites = this.getFavorites();
    if (!favorites.includes(petId)) {
      favorites.push(petId);
      this.saveFavoritesToStorage(favorites);
    }
  }

  removeFromFavorites(petId: number): void {
    const favorites = this.getFavorites().filter(id => id !== petId);
    this.saveFavoritesToStorage(favorites);
  }

  toggleFavorite(petId: number): boolean {
    if (this.isFavorite(petId)) {
      this.removeFromFavorites(petId);
      return false;
    } else {
      this.addToFavorites(petId);
      return true;
    }
  }
}