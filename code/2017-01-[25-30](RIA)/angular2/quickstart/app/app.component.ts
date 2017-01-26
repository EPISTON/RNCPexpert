import { Component } from '@angular/core';

class Todo {
  id: number;
  titre: string;
  contexte: string;
  priorite: number;

  constructor (id: number, titre: string, contexte: string, priorite: number) {
    this.id = id;
    this.titre = titre;
    this.contexte = contexte;
    this.priorite = priorite;
  }
}

@Component({
  selector: 'my-app',
  template: `<h1>{{titre}}</h1>
            <p>
              {{message}}
            </p>
            <h2>{{tache.titre}} - {{tache.priorite}}</h2>
            `,
})
export class AppComponent  {
   titre : string = "todoApp"
   message : string = 'bienvenue sur todoApp';
   tache: Todo = new Todo(1, "apprendre angular2", "formation", 4); 
}
