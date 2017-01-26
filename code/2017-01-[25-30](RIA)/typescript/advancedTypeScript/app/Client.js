define(["require", "exports"], function (require, exports) {
    "use strict";
    var emailRegexp = /^[A-Za-z0-9.]+@[A-Za-z0-9.]+$/;
    var EmailValidator = (function () {
        function EmailValidator() {
        }
        EmailValidator.prototype.validate = function (email) {
            return emailRegexp.test(email);
        };
        return EmailValidator;
    }());
    var Client = (function () {
        function Client(nom, prenom, email) {
            this.nom = nom;
            this.prenom = prenom;
            if (Client.validator.validate(email)) {
                this.email = email;
            }
            else {
                this.email = "noemail@none.org";
            }
        }
        return Client;
    }());
    Client.validator = new EmailValidator();
    exports.Client = Client;
});
//# sourceMappingURL=client.js.map