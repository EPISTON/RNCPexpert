import {Injectable} from '@angular/core';

// rxjs est la librairie nous frounissant les fonctionnnalités
// liées aux observables
// a terme, cela devrait etre intégré au future version devrait
// javascript
import {Observable, BehaviorSubject} from 'rxjs/Rx';

import {Produit} from '../produit';
import {ProduitQuantity} from '../produit-quantity';


@Injectable()
export class PanierService {
    private _produits : ProduitQuantity[];
    //
    // la particularité de cette observable est qu'il renvoie
    // forcement une donnée au moment du suscribe
    // soit la donnée initiale
    // soit le dernière transmise sur le flux s'il y en a une
    //
    private _produitsObservableBuilder : BehaviorSubject<ProduitQuantity[]>;


    constructor() {
        this._produits = [];
        this._produitsObservableBuilder =
                     new BehaviorSubject(this._produits);
    }

    addProduit(produit: Produit) {
       for (var i = 0; i < this._produits.length; i++) {
            if (this._produits[i].id === produit.id) {
                this._produits[i].quantite++;
                return;
            }
        }
        this._produits.push(new ProduitQuantity(produit.id,
                                                produit.nom,
                                                produit.prix,
                                                1));
        // envoyer sur l'observable la nouvelle version
        // du panier
        this._produitsObservableBuilder.next(this._produits);
    }

    removeProduit(produit: Produit) {

    }

    getpanier() : Observable<ProduitQuantity[]> {
        // on renvoit un Observable basique a ceux
        // qui veulent etre tenu au courant du contenu du panier
        return this._produitsObservableBuilder.asObservable();
    }
}