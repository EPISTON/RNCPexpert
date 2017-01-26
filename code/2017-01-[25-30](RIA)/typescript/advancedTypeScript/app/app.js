define(["require", "exports", "./produit", "./client"], function (require, exports, produit_1, client_1) {
    "use strict";
    var AppMain = (function () {
        function AppMain() {
            this.p1 = new produit_1.Produit(1, "quinoa des andes", 45.5);
            this.c1 = new client_1.Client("eponge", "bob", "bob@bikinibottom.com");
        }
        AppMain.prototype.run = function () {
            console.log("bonjour depuis AppMain");
            console.log(this.p1.affiche());
            //let ev = new EmailValidator();
        };
        return AppMain;
    }());
    return AppMain;
});
//# sourceMappingURL=app.js.map