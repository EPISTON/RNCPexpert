import {Tache} from './tache';
import {Injectable} from '@angular/core';

const taches : Tache[] = [
  new Tache(1, "apprendre angular2", "formation", 4),
  new Tache(2, "assomer l'ouvrier", "environnement", 5),
  new Tache(3, "voire hunger games", "culture", 1),
  new Tache(4, "faire les courses", "maison", 3),
  new Tache(5, "avancer sur le fil rouge", "formation", 3)
];

@Injectable()
export class TacheService {

    getTaches() : Promise<Tache[]> {
        return  Promise.resolve(taches);
    }
}