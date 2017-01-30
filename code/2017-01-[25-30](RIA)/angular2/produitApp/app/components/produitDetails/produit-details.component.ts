import { Component, Input } from '@angular/core';

import {Produit} from '../../produit';


@Component({
    moduleId: module.id,
    selector: 'produit-details',
    templateUrl: 'produit-details.component.html'
})
export class ProduitDetailsComponent {
    @Input()
    produit : Produit;
    
}