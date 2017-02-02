import {Injectable} from '@angular/core'
import {Site} from '../metier/site';
import {BehaviorSubject, Observable} from 'rxjs/Rx';
import {Http, Response} from '@angular/http';


@Injectable()
export class SiteService 
{

    private _sites : Site[];
    private _siteObservableBuilder : BehaviorSubject<Site[]>;
    private _searchterm: string;

    constructor(private _http : Http) {
        this._sites = [];
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
         this._http.get('http://localhost:8080/siteApiForm/rest/sites')
                  .do(r => console.log(r))  // debuggage, affichage
                  .map((res: Response) => res.json()) // transformer ma reponse en json
                  .subscribe(data => {
                      // 1 , je sauve la liste des sites dans mon service
                      this._sites = data;
                      // 2, je pousse la nouvelle liste a ceux qui ecoute mon observable
                      this._siteObservableBuilder.next(this._sites);
                  });
        return this._siteObservableBuilder.asObservable();
    }

    findByid(id: number) : Site {
        return this._sites.find(s => s.id == id);
    }
}