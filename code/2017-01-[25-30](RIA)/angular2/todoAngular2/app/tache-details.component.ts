import { Component , Input} from '@angular/core';
import { Tache } from'./tache';

@Component({
    selector: 'tache-details',
    template: `
     <div *ngIf="tache">
        <h2>tache no {{tache.id}}</h2>
        <label>titre: </label>
        <input [(ngModel)]="tache.titre" placeholder="titre" />
        <label>contexte: </label>
        <input [(ngModel)]="tache.contexte" placeholder="contexte" />
        <label>priorite: </label>
        <input [(ngModel)]="tache.priorite" placeholder="1" />        
     </div>
    `
})
export class TacheDetailsComponent {
    @Input()
    tache: Tache;
}