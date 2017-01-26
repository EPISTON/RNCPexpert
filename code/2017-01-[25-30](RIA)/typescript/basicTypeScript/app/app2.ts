
// cette interface definit une signature de fonction
// ici, la signature est celle d'une fonction prenant
// 2 parametres source et trouve, et renvoyant un boolean
interface chercheFonction {
    (source: string, trouve: string) : boolean;
}

// ma variable maRecherche contiendra une fonction du type chercheFonction
let maRecherche : chercheFonction;

maRecherche = function(source: string, trouve: string) : boolean {
    let result = source.search(trouve);
    return result > -1;
}

console.log(maRecherche("bonjour monde", "jour"));

// propriété indexable par number
interface TableauChaine {
    [index: number] : string;
}

let monTableau: TableauChaine;

monTableau = ["bob", "patrick"];
console.log(monTableau[0])

// propriété indexable par chaine
interface MapChaine {
    [index: string] : string;
}

let maMap : MapChaine;
maMap = { name : "toto", surname: "titi"};
console.log(maMap["name"]);

//
// Interval(5-10)
// interval[1] -> 6

// l'id est en lecture seule
interface Produit {
    readonly id : number;
    nom : string;
    prix : number;
}

let p1 : Produit = {id: 1, nom: "steak de lama", prix: 35.5};
p1.nom = "steak d'autruche";
//p1.id = 5;

interface readOnlyStringArray {
    readonly [index: number] : string;
}
// tableau de chaine en lecture seule
let montab2 : readOnlyStringArray= ["bob", "patrick", "krabs"];
//montab2[1] = "sandy";
console.log(montab2[1]);


