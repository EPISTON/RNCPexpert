import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';

import { AppComponent }  from './app.component';

// pour gerer les formulaire dans notre application
// on import FormsModule depuis '@angular/forms'
// et on le déclare dans la propriété imports de @NgModule

//
// le decorateur NgModule nous permet de déclarer un module angular2
//  lors de la la déclaration d'un module angular 2
// -> qu'est ce qu'il importe comme autre module
// -> declarations , qu'est ce qu'il exporte
// -> bootstrap, le composant de démarrage (uniquement sur un module racine)
@NgModule({
  imports:      [ BrowserModule, FormsModule ],
  declarations: [ AppComponent],
  bootstrap:    [ AppComponent ]  
})
export class AppModule { }
