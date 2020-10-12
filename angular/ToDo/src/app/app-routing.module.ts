import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginLayoutComponent } from './components/layouts/login-layout/login-layout.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { RegistrationComponent } from './components/registration/registration.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'logout', redirectTo: 'login', pathMatch: 'full' },

  {
    path: 'login', component: LoginLayoutComponent,
    children: [
      { path: '', component: LoginPageComponent },
      { path: 'login', component: LoginPageComponent }
    ]
  },
  {
    path: 'register', component: LoginLayoutComponent,
    children: [
      { path: '', component: RegistrationComponent },
    ]
  },
  { path: 'dashboard', loadChildren: () => import('./components/navigation/navigation.module').then(m => m.NavigationModule) },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
