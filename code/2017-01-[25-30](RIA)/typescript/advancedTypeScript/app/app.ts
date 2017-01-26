import {Produit} from "./produit";
import {Client} from "./client";


class AppMain {
    p1 : Produit = new Produit(1, "quinoa des andes", 45.5);
    c1 : Client = new Client("eponge", "bob", "bob@bikinibottom.com");
    run () : void {
         console.log("bonjour depuis AppMain");
         console.log(this.p1.affiche());
         //let ev = new EmailValidator();
    }
}

export = AppMain;