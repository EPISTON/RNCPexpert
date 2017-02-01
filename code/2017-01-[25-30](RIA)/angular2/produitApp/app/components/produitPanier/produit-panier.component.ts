import { Component, OnInit, OnDestroy } from '@angular/core';
import { CurrencyPipe } from '@angular/common';

import {Observable} from 'rxjs/Rx';

import {PanierService} from '../../services/panier-service';

import {ProduitQuantity} from '../../produit-quantity';


@Component({
    moduleId: module.id,
    selector: 'produit-panier',
    templateUrl: './produit-panier.component.html'
})
export class ProduitPanierComponent implements OnInit, OnDestroy{
    produits : ProduitQuantity[];
    panierService: PanierService;
    panier: Observable<ProduitQuantity[]>;

    constructor(panierService : PanierService) {
        this.panierService = panierService;
        this.produits = [];
    }

    ngOnInit() {
        console.log("init de panier");
        // je recupere l'observable sur le panier
        this.panier = this.panierService.getpanier();
        // je souscris a cet observable
        // a chaque nouvelle donnée, mon labda sera rappelé
        // tout ce qu'il fait, c'est de remplacer la liste
        // des produits par la nouvelle
        //this.panier.subscribe(data => this.produits = data);

    }
    ngOnDestroy() {
        //this.panier
    }

}
