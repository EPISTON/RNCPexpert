import { Component , Input} from '@angular/core';
import { Tache } from'./tache';


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

const taches : Tache[] = [
  new Tache(1, "apprendre angular2", "formation", 4),
  new Tache(2, "assomer l'ouvrier", "environnement", 5),
  new Tache(3, "voire hunger games", "culture", 1),
  new Tache(4, "faire les courses", "maison", 3),
  new Tache(5, "avancer sur le fil rouge", "formation", 3)
];

@Component({
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
            <div *ngIf="selectedTache">
              <label>titre: </label>
              <input [(ngModel)]="selectedTache.titre" placeholder="titre" />
            </div>
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
export class AppComponent  {
   taches: Tache[];
   selectedTache: Tache;

   constructor() {
     this.taches = taches;
     this.selectedTache = null;
   }

   onTacheSelect(id: number) {
     for (var i = 0; i < this.taches.length; i++) {
       if (this.taches[i].id == id) {
         this.selectedTache = taches[i];
         return;
       }
     }
     this.selectedTache = null;
   }
}
