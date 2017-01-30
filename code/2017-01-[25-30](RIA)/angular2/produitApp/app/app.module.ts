import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';

import { AppComponent }  from './app.component';
import { NavbarComponent }  from './components/navbar/navbar.component';
import { ProduitListeComponent }  from './components/produitListe/produit-liste.component';
import { ProduitDetailsComponent }  from './components/produitDetails/produit-details.component';
import { ProduitPanierComponent }  from './components/produitPanier/produit-panier.component';

//
// le decorateur NgModule nous permet de déclarer un module angular2
//  lors de la la déclaration d'un module angular 2
// -> qu'est ce qu'il importe comme autre module
// -> declarations , qu'est ce qu'il exporte
// -> bootstrap, le composant de démarrage (uniquement sur un module racine)
@NgModule({
  imports:      [ BrowserModule, FormsModule ],
  declarations: [ AppComponent,
                  NavbarComponent,
                  ProduitListeComponent,
                  ProduitDetailsComponent,
                  ProduitPanierComponent],
  bootstrap:    [ AppComponent ]  
})
export class AppModule { }
