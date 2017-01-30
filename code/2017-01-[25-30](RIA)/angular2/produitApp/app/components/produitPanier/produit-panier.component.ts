import { Component, OnInit } from '@angular/core';
import { CurrencyPipe } from '@angular/common';

import {PanierService} from '../../services/panier-service';

import {ProduitQuantity} from '../../produit-quantity';


@Component({
    moduleId: module.id,
    selector: 'produit-panier',
    templateUrl: './produit-panier.component.html'
})
export class ProduitPanierComponent {
    produits : ProduitQuantity[];
    panierService: PanierService;

    constructor(panierService : PanierService) {
        this.panierService = panierService;
        this.produits = [];
    }

}
