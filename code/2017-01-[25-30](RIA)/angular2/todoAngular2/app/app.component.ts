import { Component , Input, OnInit} from '@angular/core';
import { Tache } from './tache';
import { TacheService } from './tache.service';


/*

de selectedTache.titre ---> input.value
<input value="{{selectedTache.titre}}" placeholder="titre" />

 de selectedTache.titre ---> input.value
<input [value]="selectedTache.titre" placeholder="titre" />

on a besoin d'importer le module angularForm
et la directive Input
dans ce fichier -> Input depuis @angular/core
dans le fichier app.module
ajouter la dépendance au module @angular/form
pour du bidirectionnel
[(ngModel)]="selectedTache.titre"


d'un evenement html --> code typescript
(click)="onTacheSelect(tache.id)

cette directive desactive l'execution/rendu
de la balise si la condition est fausse
 <div *ngIf="selectedTache">

*/



@Component({
  providers: [TacheService],
  selector: 'my-app',
  template: `<h1>todoManager</h1>
            <h2>liste des taches restantes</h2>
            <ul class="taches">
              <li *ngFor="let tache of taches"
                (click)="onTacheSelect(tache.id)">
                <span class="badge bleft">{{tache.id}}</span>
                {{tache.titre}}
                <span class="badge bright">{{tache.priorite}}</span> 
              </li>
            </ul>
            <tache-details [tache]="selectedTache"></tache-details>
            `,
  styles: [
    `
      .taches {
        margin: 0 0 2em 0;
        list-style-type: none;
        padding: 0;
        width: 17em;
      }
      .taches li {
        background-color: #EEE;
        position: relative;
        left: 0;
        margin: .5em;
        padding: .3em 0;
        height: 1.6em;
        border-radius: 4px;
      }
      .taches .badge {
        font-size: small;
        display: inline-block;
        position: relative;
        line-height: 1em;
        padding: 0.8em 0.7em 0 0.7em;
        height: 1.8em;
        border-radius: 4px 4px 4px 4px;
        left: -1px;
        top: -4px;
      }
      .bleft {
        background-color: #607D8B;
        left: -1px;
        margin-right: .8em;
      }
      .bright {
        float: right;
        background-color: DarkSalmon;
      }
      .taches li:hover {
        border: solid 2px blue;
      }
    `]
})
export class AppComponent implements OnInit {
   taches: Tache[];
   selectedTache: Tache;
   tacheService: TacheService;

   constructor(tacheService: TacheService) {
     this.tacheService = tacheService;
     this.taches = [];
     this.selectedTache = null;
   }
   ngOnInit() {
     this.tacheService.getTaches()
                      .then(taches => this.taches = taches);
   }

   onTacheSelect(id: number) {
     for (var i = 0; i < this.taches.length; i++) {
       if (this.taches[i].id == id) {
         this.selectedTache = this.taches[i];
         return;
       }
     }
     this.selectedTache = null;
   }
}
