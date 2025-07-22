import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { CadastroComponent } from './pages/cadastro/cadastro.component';
import { CadastroPetComponent } from './pages/cadastro-pet/cadastro-pet.component';
import { FeedComponent } from './pages/feed/feed.component';
import { PetDetailsComponent } from './pages/pet-details/pet-details.component';
import { ProfileComponent } from './pages/profile/profile.component';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'feed',
        pathMatch: 'full'
    },
    {
        path: "login",
        component: LoginComponent
    },
    {
        path: "cadastro",
        component: CadastroComponent
    },
    {
        path: "cadastro-pet",
        component: CadastroPetComponent
    },
    {
        path: "feed",
        component: FeedComponent
    },
    {
        path: "pet/:id",
        component: PetDetailsComponent
    },
    {
        path: "profile",
        component: ProfileComponent
    },
    {
        path: '**',
        redirectTo: 'feed'
    }
];
