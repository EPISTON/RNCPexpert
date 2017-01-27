
export class Tache {
  id: number;
  titre: string;
  contexte: string;
  priorite: number;

  constructor (id: number, titre: string, contexte: string, priorite: number) {
    this.id = id;
    this.titre = titre;
    this.contexte = contexte;
    this.priorite = priorite;
  }
}