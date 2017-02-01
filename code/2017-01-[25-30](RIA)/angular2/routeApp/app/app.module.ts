import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

import { AppComponent }  from './app.component';
import { NavbarComponent }  from './components/navbar/navbar.component';
import { HomeComponent }  from './components/home/home.component';
import { AboutComponent }  from './components/about/about.component';
import { ContactComponent }  from './components/contact/contact.component';
import { DetailsComponent }  from './components/details/details.component';

//
// le decorateur NgModule nous permet de déclarer un module angular2
//  lors de la la déclaration d'un module angular 2
// -> qu'est ce qu'il importe comme autre module
// -> declarations , qu'est ce qu'il exporte
// -> bootstrap, le composant de démarrage (uniquement sur un module racine)
@NgModule({
  imports:      [ BrowserModule,
                RouterModule.forRoot([
                  { path: 'home', component: HomeComponent},
                  { path: 'about', component: AboutComponent},
                  { path: 'contact', component: ContactComponent},
                  { path: 'details/:id', component: DetailsComponent},
                  {
                    path: '',
                    redirectTo: '/home',
                    pathMatch: 'full'
                  }
                ])
               ],
  declarations: [ AppComponent,
                 NavbarComponent,
                 HomeComponent,
                 AboutComponent,
                 ContactComponent,
                 DetailsComponent],
  bootstrap:    [ AppComponent ]  
})
export class AppModule { }
