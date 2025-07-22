// src/app/types/pet.type.ts

// Definindo um tipo para o porte, que corresponde ao seu formControl 'porte'
export type PetSize = 'Pequeno' | 'Médio' | 'Grande' | string;

export interface Pet {
  id: number;           // ID será gerado pelo back-end, mas é útil no front-end
  name: string;         // Mapeado de 'nome'
  species: string;      // Mapeado de 'tipoAnimal'
  breed: string;        // Mapeado de 'raca'
  age: number;          // Mapeado de 'idade'
  isFavorited: boolean; // Mapeado de 'favorito', usado para controle de favoritos
  size: PetSize;        // Mapeado de 'porte'
  color: string;        // Mapeado de 'cor'
  description: string;  // Mapeado de 'descricao'
  imageUrls: string[];  // Mapeado de 'imagens'. No front, teremos as URLs das imagens

}
