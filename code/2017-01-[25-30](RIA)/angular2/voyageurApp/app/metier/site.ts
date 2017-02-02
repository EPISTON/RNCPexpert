
export class Site {
    id: number;
    nom: string;
    rue: string;
    ville: string;
    pays: string;

    constructor(id: number,
                nom: string,
                rue: string,
                ville: string,
                pays: string) {
        this.id = id;
        this.nom = nom;
        this.rue = rue;
        this.ville = ville;
        this.pays = pays;
    }
    
}