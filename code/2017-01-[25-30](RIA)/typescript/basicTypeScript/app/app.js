console.log("bonjour typescript");
// permet de déclarer une variable de type boolean
var isFinished = false;
//isFinished = "hoho";
//isFinished = 42;
console.log("isFinished = " + isFinished);
// meme si on ne le precise pas, typescript infere le type
var monNom = "vincent";
//monNom = 42;
var chaine = "patrick";
// type number
var chiffre = 42;
// les chaines multilignes
var blocdetexte = "\n                            un texte\n                            sur plusieurs\n                            lignes\n                            ";
console.log(blocdetexte);
// on a, avec les chaine ` l'interpolation via ${}
// c.a.d qu'il injectera automatiquement le contenu de la variable
var message = "bonjour, mr " + monNom;
console.log(message);
//let listeNombre : number[] = [1, 2, 3, "hoho"];
var listeNombre = [1, 2, 3, 5];
var listePrenom = ["bob", "patrick", "krabs"];
// on peu declarer des tuples
var couple = ["vincent", 40];
//couple = ["bob", "joe"];
// definition d'un type enumeration
var Color;
(function (Color) {
    Color[Color["Rouge"] = 0] = "Rouge";
    Color[Color["Vert"] = 1] = "Vert";
    Color[Color["Bleue"] = 2] = "Bleue";
})(Color || (Color = {}));
;
// utilisation de celui-ci a la declaration d'une variable
var c = Color.Rouge;
// je peu connaitre les valeurs de l'ennumeration
console.log(Color[2]);
//---- a eviter si possible -----
var mavar1 = 4;
// le type any equivaut a une vriable dynamique de javascript
mavar1 = "toto";
mavar1 = true;
console.log("mavar1 = " + mavar1);
// void -> on ne renvoie rien, et aussi null et undefined
var u = undefined;
// never -> type de retour d'une fonction dont on ne sort jamais "normalement"
/*
* declaration de fonction
*
*
*/
function addition(x, y) {
    return x + y;
}
console.log(addition(15, 25));
// format d'un objet a respecter/ contrat
function salutation(principal) {
    console.log(principal.nom + ", " + principal.prenom);
}
salutation({ nom: "courtalon", prenom: "vincent" });
//salutation({nom: "courtalon", age: 40});
//salutation({nom: "courtalon", prenom: "vincent", age: 40});
var identite = { nom: "courtalon", prenom: "vincent", age: 40 };
salutation(identite);
function creerCarre(config) {
    var nouveauCarre = { couleur: "Red", taille: 10 };
    if (config.couleur) {
        nouveauCarre.couleur = config.couleur;
    }
    nouveauCarre.taille = config.taille;
    return nouveauCarre;
}
var c1 = creerCarre({ taille: 50 });
var c2 = creerCarre({ taille: 50, couleur: "Blue" });
//# sourceMappingURL=app.js.map