import { Injectable, OnInit } from '@angular/core'
import { BehaviorSubject, Observable } from 'rxjs/Rx';

import { Site } from '../metier/site';

@Injectable()
export class ItineraireService  {

    private _sites: Site[];
    private _sitesItineraire : BehaviorSubject<Site[]>;

    constructor() {
        this._sites = [];
        this._sitesItineraire =
            new BehaviorSubject<Site[]>(this._sites);
    }

    getSitesItineraire() :Observable<Site[]> {
        return this._sitesItineraire.asObservable();
    }

    addSite(site : Site) {
        this._sites.push(site);
        this._sitesItineraire.next(this._sites);
    }


}