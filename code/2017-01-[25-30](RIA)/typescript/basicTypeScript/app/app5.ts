// les "arrow" function
// bind automatiquement le this pour s'en rappeler
// même si elle sont appelée hors du contexte de l'objet initial
// c'est l'equivalent (en plus elegant) du mecanisme de la closure
// utilisé pour sauvegarder this

let monFiltre = {
    min: 10,
    propertyName: "poids",
    getFilter: function() {
        return (element : any) : boolean => {
            if (element[this.propertyName] && element[this.propertyName] > this.min)
                return true;
            else
                return false;
        };
    }
}

class MaCollection {
    elements : any[];
    constructor (elements : any[]) {
        this.elements = elements;
    }

    applyFilter (filtre: any) : any[] {
        let f = filtre.getFilter();
        let result = [];
        for(var i = 0; i < this.elements.length; i++) {
            if (f(this.elements[i])) {
                result.push(this.elements[i]);
            }
        }
        return result;
    }
}

let col1 = new MaCollection([
    {id: 1, nom: "steak de lama", poids: 1.5},
    {id: 2, nom: "steak de bison", poids: 15.5},
    {id: 3, nom: "riz bio bio", poids: 12.5},
    {id: 4, nom: "tofu tout fou", poids: 0.75},
]);

let resultat = col1.applyFilter(monFiltre);

console.log(resultat);