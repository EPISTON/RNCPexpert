import {Injectable} from '@angular/core'

import {Produit} from '../produit';
import {ProduitQuantity} from '../produit-quantity';


@Injectable()
export class PanierService {
    produits : ProduitQuantity[];

    constructor() {
        this.produits = [];
    }

    addProduit(produit: Produit) {
       for (var i = 0; i < this.produits.length; i++) {
            if (this.produits[i].id === produit.id) {
                this.produits[i].quantite++;
                return;
            }
        }
        this.produits.push(new ProduitQuantity(produit.id,
                                                produit.nom,
                                                produit.prix,
                                                1));
    }

    removeProduit(produit: Produit) {

    }
}