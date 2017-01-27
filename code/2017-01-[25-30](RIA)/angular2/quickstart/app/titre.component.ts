import { Component } from '@angular/core';

@Component({
    selector: 'my-titre',
    template: 
        `
            <h1>{{titre}}</h1>
        `
})
export class TitreComponent {
    titre: string = "hoho, je suis le titre";
}