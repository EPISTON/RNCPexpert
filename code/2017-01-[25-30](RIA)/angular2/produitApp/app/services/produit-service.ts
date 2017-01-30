import {Injectable} from '@angular/core'

import {Produit} from '../produit';

const PRODUITS_SAMPLE : Produit[] = [
    new Produit(1, "steak de lama", 29.99),
    new Produit(2, "biere oceania", 9.99),
    new Produit(3, "tofu tout fou", 19.99),
    new Produit(4, "quinoa des andes", 24.99),
    new Produit(5, "miel des carpathes", 15.99)
]

@Injectable()
export class ProduitService {
    produits: Produit[];

    constructor() {
        this.produits = PRODUITS_SAMPLE;
    }

    getProduits() : Promise<Produit[]> {
        return Promise.resolve(this.produits);
    }

}