var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
define(["require", "exports", "./logger"], function (require, exports, logger_1) {
    "use strict";
    var Produit = (function () {
        function Produit(id, nom, poids) {
            this.id = id;
            this.nom = nom;
            this.poids = poids;
        }
        ;
        Produit.prototype.affiche = function () {
            return "produit " + this.nom + " de poids " + this.poids + " ";
        };
        ;
        return Produit;
    }());
    __decorate([
        logger_1.logger,
        __metadata("design:type", Function),
        __metadata("design:paramtypes", []),
        __metadata("design:returntype", String)
    ], Produit.prototype, "affiche", null);
    exports.Produit = Produit;
});
//# sourceMappingURL=produit.js.map