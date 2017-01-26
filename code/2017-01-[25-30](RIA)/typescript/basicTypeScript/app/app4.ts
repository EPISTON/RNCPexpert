abstract class Figure {
    x: number;
    y: number;
    constructor(x: number, y: number) {
        this.x = x;
        this.y = y;
    }
    abstract afficher() : string;
}

class Triangle extends Figure  {
    taille : number;
    constructor (x: number, y: number, taille:number) {
        super(x, y);
        this.taille = taille;
    }
    afficher() : string {
        return `triangle de taille ${this.taille} au coordonnées ${this.x} ${this.y}`;
    }
}

let tri1 : Triangle = new Triangle(10, 15, 5);
console.log(tri1.afficher());

interface affichable {
    //x: number;
    afficher () : string;
}

let aff1 : affichable = tri1;
