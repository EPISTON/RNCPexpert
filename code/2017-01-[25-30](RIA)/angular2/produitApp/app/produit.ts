
export class Produit {
    id: number;
    nom: string;
    prix: number;

    constructor (id: number, nom: string, prix: number) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
    }
}