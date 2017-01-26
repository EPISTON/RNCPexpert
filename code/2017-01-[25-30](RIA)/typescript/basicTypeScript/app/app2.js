// ma variable maRecherche contiendra une fonction du type chercheFonction
var maRecherche;
maRecherche = function (source, trouve) {
    var result = source.search(trouve);
    return result > -1;
};
console.log(maRecherche("bonjour monde", "jour"));
var monTableau;
monTableau = ["bob", "patrick"];
console.log(monTableau[0]);
var maMap;
maMap = { name: "toto", surname: "titi" };
console.log(maMap["name"]);
var p1 = { id: 1, nom: "steak de lama", prix: 35.5 };
p1.nom = "steak d'autruche";
// tableau de chaine en lecture seule
var montab2 = ["bob", "patrick", "krabs"];
//montab2[1] = "sandy";
console.log(montab2[1]);
//# sourceMappingURL=app2.js.map