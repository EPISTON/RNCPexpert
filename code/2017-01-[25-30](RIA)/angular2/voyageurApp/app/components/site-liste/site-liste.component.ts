import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs/Rx';

import {Site} from '../../metier/site';
import {SiteService} from '../../services/site.service';
import {ItineraireService} from '../../services/itineraire.service';

@Component({
    moduleId : module.id,
    selector: 'site-liste',
    templateUrl: './site-liste.component.html'
})
export class SiteListeComponent implements OnInit
 {
    private _sites : Site[];
    sites : Observable<Site[]>;
    searchterm : string;

    // constructeur magique de typescript
    // ici, le mot clé private fait que l'attribut
    // sera automatiquement crée et affecté
    constructor(private _siteService: SiteService,
                private _itineraireService: ItineraireService) {
        this.sites = null;
        this.searchterm = "";
    }

    ngOnInit() {
        this.sites = this._siteService.getSites();
        this.sites.subscribe( s => this._sites = s);

    }

    searchChanged(newsearch: string) {
        console.log("recherche " + newsearch);
        this.searchterm = newsearch;
        this._siteService.setSearch(newsearch);    
    }

    addSiteToItineraire(id : number) {
        this._itineraireService.addSite(
            this._sites.find(s => s.id === id)
        );
    }
}
