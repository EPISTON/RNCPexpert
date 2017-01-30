
import {Produit} from './produit';

export class ProduitQuantity extends Produit {
    quantite : number;

    constructor(id: number, nom: string, prix:number, quantite:number) {
        super(id, nom, prix);
        this.quantite = quantite;
    }
}