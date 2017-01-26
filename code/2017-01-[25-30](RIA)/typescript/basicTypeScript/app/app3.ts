
class Salutation {
    nom: string;
    constructor(denomination: string) {
        this.nom = denomination;
    }
    saluer() {
        return "bonjour, " + this.nom;
    }
}

let salutation1 = new Salutation("patrick");

console.log(salutation1.saluer());

class Personne {
    protected nom: string;
    private prenom: string;
    constructor(nom: string, prenom: string) {
        this.nom = nom;
        this.prenom = prenom;
    }
    saluer() {
        return "bonjour " + this.nom + ", " + this.prenom;
    }
    public setNom(nom : string) : void {this.nom = nom;}
    public getNom() : string {return this.nom;}
}

class Client extends Personne {
    email: string;

    constructor(nom: string, prenom:string, email: string) {
        super(nom, prenom);
        this.email = email;
    }
    saluer() {
        return `bienvenue ${this.nom} cher client ${this.email}`;
    }
}

let cl1 = new Client("eponge", "bob", "bob@bikinibottom.com");
console.log(cl1.saluer());

let pr1 =  new Personne("etoile", "patrick");
console.log(pr1.saluer());

// je peut acceder a un attribut
cl1.setNom("sponge");
console.log(cl1.saluer());
