-- Inserir usuários de teste
INSERT INTO users (name, email, password, user_type, created_at) VALUES 
('João Silva', 'joao@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9tYoHA8t0r5lDvC', 'ADOTANTE', CURRENT_TIMESTAMP),
('ONG Amigos dos Animais', 'ong@amigos.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9tYoHA8t0r5lDvC', 'ONG', CURRENT_TIMESTAMP);

-- Atualizar ONG com CNPJ e descrição
UPDATE users SET 
    cnpj = '12.345.678/0001-90',
    description = 'ONG dedicada ao resgate e cuidado de animais abandonados. Trabalhamos há mais de 10 anos salvando vidas e encontrando lares amorosos para nossos amigos de quatro patas.'
WHERE email = 'ong@amigos.com';

-- Inserir pets de teste
INSERT INTO pets (name, species, breed, age, size, color, description, adoption_status, owner_id, created_at) VALUES 
('Rex', 'Cachorro', 'Labrador', 3, 'GRANDE', 'Dourado', 'Rex é um cão muito carinhoso e brincalhão. Adora crianças e é perfeito para famílias ativas. Está castrado e com todas as vacinas em dia.', 'AVAILABLE', 2, CURRENT_TIMESTAMP),
('Luna', 'Gato', 'Siamês', 2, 'PEQUENO', 'Branco e Preto', 'Luna é uma gatinha muito dócil e carinhosa. Gosta de colo e é ideal para apartamentos. Castrada e vacinada.', 'AVAILABLE', 2, CURRENT_TIMESTAMP),
('Caramelo', 'Cachorro', 'SRD', 5, 'MEDIO', 'Caramelo', 'Caramelo é um vira-lata muito inteligente e leal. Já é adulto e tem temperamento calmo. Perfeito para quem busca um companheiro fiel.', 'AVAILABLE', 2, CURRENT_TIMESTAMP),
('Mimi', 'Gato', 'Persa', 1, 'PEQUENO', 'Branco', 'Mimi é uma gatinha jovem e muito brincalhona. Adora brinquedos e é muito sociável com outros gatos.', 'AVAILABLE', 2, CURRENT_TIMESTAMP),
('Thor', 'Cachorro', 'Pastor Alemão', 4, 'GRANDE', 'Preto e Marrom', 'Thor é um cão protetor e muito leal. Ideal para casas com quintal. Bem treinado e obediente.', 'AVAILABLE', 2, CURRENT_TIMESTAMP),
('Bella', 'Cachorro', 'Golden Retriever', 2, 'GRANDE', 'Dourado', 'Bella é uma cadela muito dócil e adora crianças. Perfeita para famílias grandes. Muito carinhosa e brincalhona.', 'AVAILABLE', 2, CURRENT_TIMESTAMP);

-- Inserir imagens dos pets
INSERT INTO pet_images (url, pet_id, is_main) VALUES 
-- Rex (Labrador)
('https://images.pexels.com/photos/1108099/pexels-photo-1108099.jpeg', 1, true),
('https://images.pexels.com/photos/2253275/pexels-photo-2253275.jpeg', 1, false),

-- Luna (Siamês)
('https://images.pexels.com/photos/45201/kitty-cat-kitten-pet-45201.jpeg', 2, true),
('https://images.pexels.com/photos/416160/pexels-photo-416160.jpeg', 2, false),

-- Caramelo (SRD)
('https://images.pexels.com/photos/1805164/pexels-photo-1805164.jpeg', 3, true),
('https://images.pexels.com/photos/1254140/pexels-photo-1254140.jpeg', 3, false),

-- Mimi (Persa)
('https://images.pexels.com/photos/774731/pexels-photo-774731.jpeg', 4, true),
('https://images.pexels.com/photos/1170986/pexels-photo-1170986.jpeg', 4, false),

-- Thor (Pastor Alemão)
('https://images.pexels.com/photos/333083/pexels-photo-333083.jpeg', 5, true),
('https://images.pexels.com/photos/1490908/pexels-photo-1490908.jpeg', 5, false),

-- Bella (Golden Retriever)
('https://images.pexels.com/photos/1851164/pexels-photo-1851164.jpeg', 6, true),
('https://images.pexels.com/photos/1390361/pexels-photo-1390361.jpeg', 6, false);