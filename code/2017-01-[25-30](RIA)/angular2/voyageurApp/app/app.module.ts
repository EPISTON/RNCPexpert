import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { AppComponent }  from './app.component';
import { SiteListeComponent }  from './components/site-liste/site-liste.component';
import { EditSiteComponent } from './components/edit-site/edit-site.component';
import { ItineraireComponent }  from './components/itineraire/itineraire.component';
import { NavbarComponent }  from './components/navbar/navbar.component';
import {SiteService} from './services/site.service';
import {ItineraireService} from './services/itineraire.service';
//
// le decorateur NgModule nous permet de déclarer un module angular2
//  lors de la la déclaration d'un module angular 2
// -> qu'est ce qu'il importe comme autre module
// -> declarations , qu'est ce qu'il exporte
// -> bootstrap, le composant de démarrage (uniquement sur un module racine)
@NgModule({
  imports:      [ BrowserModule,
                  FormsModule,
                  RouterModule.forRoot([
                    { path: 'edit/:id', component: EditSiteComponent},
                    { path: 'liste', component: SiteListeComponent},
                    { path: '', redirectTo: '/liste', pathMatch: 'full'}
                  ]) ],
  declarations: [ AppComponent,
               SiteListeComponent,
               EditSiteComponent,
               ItineraireComponent,
               NavbarComponent],
  providers: [SiteService, ItineraireService],
  bootstrap:    [ AppComponent ]  
})
export class AppModule { }
