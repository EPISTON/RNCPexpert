import {Injectable} from '@angular/core'
import {Site} from '../metier/site';
import {BehaviorSubject, Observable} from 'rxjs/Rx';

const SAMPLE_SITE :Site[] = [
    new Site(1, "edugroupe", "152 rue malakof", "paris", "france"),
    new Site(2, "chateau fillon", "152 rue de la fortune", "Beauce", "france"),
    new Site(3, "le crabe croustillant", "15 rue la gastro", "bikinibottom", "pacifique"),
    new Site(4, "cap gemini", "5-7 avenue frederick", "surennes", "france"),
    new Site(5, "holmes home", "221b baker street", "london", "england"),
];

@Injectable()
export class SiteService {

    private _sites : Site[];
    private _siteObservableBuilder : BehaviorSubject<Site[]>;
    private _searchterm: string;

    constructor() {
        this._sites = SAMPLE_SITE;
        this._searchterm = "";
        this._siteObservableBuilder = 
            new BehaviorSubject<Site[]>(this._sites);
    }

    setSearch(searchterm: string) {
        this._searchterm = searchterm;
        if (this._searchterm === "") {
            this._siteObservableBuilder.next(this._sites);
        }
        else {
            this._siteObservableBuilder.next(
                this._sites.filter(
                    s => s.nom.indexOf(searchterm) != -1
                )
            );
        }

    }

    getSites() : Observable<Site[]> {
        return this._siteObservableBuilder.asObservable();
    }

    findByid(id: number) : Site {
        return this._sites.find(s => s.id == id);
    }
}