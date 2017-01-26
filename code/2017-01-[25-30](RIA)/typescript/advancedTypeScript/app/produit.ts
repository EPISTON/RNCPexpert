import {logger} from './logger';

class Produit {
    readonly id : number;
    nom : string;
    poids: number;
    constructor (id: number, nom: string, poids: number) {
        this.id = id;
        this.nom = nom;
        this.poids = poids;
    };

    @logger
    affiche() : string {
        return `produit ${this.nom} de poids ${this.poids} `;
    };
}

export {Produit};