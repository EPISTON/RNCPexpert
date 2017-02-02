import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs/Rx';

import {Site} from '../../metier/site';
import {ItineraireService} from '../../services/itineraire.service';

@Component({
    moduleId : module.id,
    selector: 'itineraire',
    templateUrl: './itineraire.component.html'
})
export class ItineraireComponent implements OnInit {

    sites : Observable<Site[]>;

    constructor(private _itineraireService : ItineraireService) {
        this.sites = null;
    }

    ngOnInit() {
        this.sites = this._itineraireService.getSitesItineraire();
    }


}
