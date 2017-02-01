import { Component, OnInit } from '@angular/core';
import { CurrencyPipe } from '@angular/common';


import {ProduitService} from '../../services/produit-service';
import {PanierService} from '../../services/panier-service';

import {Produit} from '../../produit';


@Component({
  moduleId: module.id,
  selector: 'produit-liste',
  templateUrl: './produit-liste.component.html',
  providers: [ProduitService, PanierService]
})
export class ProduitListeComponent implements OnInit
 {
    editedProduit : Produit;
    produitService : ProduitService;
    panierService : PanierService;
    produits : Produit[];

    constructor (produitService : ProduitService, panierService: PanierService) {
        this.produitService = produitService
        this.panierService = panierService;
    }

    ngOnInit() {
        // on récupere une promesse du service produit
        // qui, quand on la résoudra, nous fournira la liste des
        // produits, le callback du then est alors appelé 
        this.produitService.getProduits()
                            .then(produits => this.produits = produits);
    }

    editProduit(id: number) {
        for (var i = 0; i < this.produits.length; i++) {
            if (this.produits[i].id === id) {
                this.editedProduit = this.produits[i];
                return;
            }
        }
        this.editedProduit = null;
    }

    ajouterPanier(id: number) {
        for (var i = 0; i < this.produits.length; i++) {
            if (this.produits[i].id === id) {
                this.panierService.addProduit(this.produits[i]);
                return;
            }
        }
    }
}