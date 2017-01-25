console.log("bonjour typescript");

// permet de déclarer une variable de type boolean
let isFinished : boolean = false;

//isFinished = "hoho";
//isFinished = 42;
console.log("isFinished = " + isFinished);

// meme si on ne le precise pas, typescript infere le type
let monNom = "vincent";

//monNom = 42;
let chaine : string = "patrick";
// type number
let chiffre : number = 42;
// les chaines multilignes
let blocdetexte : string = `
                            un texte
                            sur plusieurs
                            lignes
                            `;
console.log(blocdetexte);
// on a, avec les chaine ` l'interpolation via ${}
// c.a.d qu'il injectera automatiquement le contenu de la variable
let message : string = `bonjour, mr ${monNom}`;
console.log(message);

//let listeNombre : number[] = [1, 2, 3, "hoho"];
let listeNombre : number[] = [1, 2, 3, 5];
let listePrenom : Array<string> = ["bob", "patrick", "krabs"];

// on peu declarer des tuples
let couple : [string, number] = ["vincent", 40];

//couple = ["bob", "joe"];


// definition d'un type enumeration
enum Color { Rouge, Vert, Bleue};
// utilisation de celui-ci a la declaration d'une variable
let c : Color = Color.Rouge;
// je peu connaitre les valeurs de l'ennumeration
console.log(Color[2]);

//---- a eviter si possible -----
let mavar1 : any = 4;
// le type any equivaut a une vriable dynamique de javascript
mavar1 = "toto";
mavar1 = true;
console.log("mavar1 = " + mavar1);

// void -> on ne renvoie rien, et aussi null et undefined
let u : undefined = undefined;

// never -> type de retour d'une fonction dont on ne sort jamais "normalement"


/*
* declaration de fonction
*
*
*/

function addition(x : number, y: number) : number {
    return x + y;
}

console.log(addition(15, 25));

// declaration d'une interface
interface Denomination {
    nom: string;
    prenom : string;
}
// format d'un objet a respecter/ contrat

function salutation(principal: Denomination) : void {
    console.log(principal.nom + ", " + principal.prenom);
}

salutation({nom: "courtalon", prenom: "vincent"});
//salutation({nom: "courtalon", age: 40});
//salutation({nom: "courtalon", prenom: "vincent", age: 40});
let identite = {nom: "courtalon", prenom: "vincent", age: 40};
salutation(identite);

// declaration d'une interface
interface Carre {
    couleur? : string;
    taille : number;
}

function creerCarre(config: Carre) : Carre {
    let nouveauCarre = {couleur: "Red", taille: 10};
    if (config.couleur) {
        nouveauCarre.couleur = config.couleur;
    }
    nouveauCarre.taille = config.taille;
    return nouveauCarre;
}

let c1 = creerCarre({taille: 50});
let c2 = creerCarre({taille: 50, couleur: "Blue"});

