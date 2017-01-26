const emailRegexp = /^[A-Za-z0-9.]+@[A-Za-z0-9.]+$/

class EmailValidator {

    validate(email: string) : boolean {
        return emailRegexp.test(email);
    }
}

class Client {
    private static validator = new EmailValidator();

    nom: string
    prenom: string
    email: string

    constructor (nom: string, prenom: string, email: string) {
        this.nom = nom;
        this.prenom = prenom;
        if (Client.validator.validate(email)) {
            this.email = email;
        }
        else {
            this.email = "noemail@none.org";
        }
    }
}

export {Client};
