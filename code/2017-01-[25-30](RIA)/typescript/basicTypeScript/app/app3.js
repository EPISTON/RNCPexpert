var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var Salutation = (function () {
    function Salutation(denomination) {
        this.nom = denomination;
    }
    Salutation.prototype.saluer = function () {
        return "bonjour, " + this.nom;
    };
    return Salutation;
}());
var salutation1 = new Salutation("patrick");
console.log(salutation1.saluer());
var Personne = (function () {
    function Personne(nom, prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }
    Personne.prototype.saluer = function () {
        return "bonjour " + this.nom + ", " + this.prenom;
    };
    Personne.prototype.setNom = function (nom) { this.nom = nom; };
    Personne.prototype.getNom = function () { return this.nom; };
    return Personne;
}());
var Client = (function (_super) {
    __extends(Client, _super);
    function Client(nom, prenom, email) {
        var _this = _super.call(this, nom, prenom) || this;
        _this.email = email;
        return _this;
    }
    Client.prototype.saluer = function () {
        return "bienvenue " + this.nom + " cher client " + this.email;
    };
    return Client;
}(Personne));
var cl1 = new Client("eponge", "bob", "bob@bikinibottom.com");
console.log(cl1.saluer());
var pr1 = new Personne("etoile", "patrick");
console.log(pr1.saluer());
// je peut acceder a un attribut
cl1.setNom("sponge");
console.log(cl1.saluer());
//# sourceMappingURL=app3.js.map