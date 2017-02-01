import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params} from '@angular/router';

@Component({
    moduleId: module.id,
    selector: 'details-page',
    templateUrl: './details.component.html'
})
export class DetailsComponent implements OnInit {
    currentid : number;
    route: ActivatedRoute;

    // si j'indique dans le constructeur
    // un parametre ActivatedRoute
    // la route courant me sera injectée
    constructor(route: ActivatedRoute) {
        this.currentid = 0;
        this.route = route;
    }

    ngOnInit() {
        // dans la route courante
        // j'utlise subscribe pour ecouter
        // l'observable des parametres de la route
        // afin de réagir a tout changement de parametre
        this.route.params
            .subscribe( params => this.currentid = params['id']);        
    }
}