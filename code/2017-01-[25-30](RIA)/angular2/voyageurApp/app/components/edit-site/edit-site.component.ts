import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';

import {Site} from '../../metier/site';
import {SiteService} from '../../services/site.service'


@Component({
    moduleId : module.id,
    selector: 'edit-site',
    templateUrl: './edit-site.component.html'
})
export class EditSiteComponent implements OnInit {
    editedSite : Site;
    currentId : number;

    constructor(private siteService: SiteService,
                private route: ActivatedRoute) {
        this.editedSite = null;
        this.currentId = 0;
    }

    ngOnInit() {
       this.route.params
           .subscribe(params => this.currentId= params['id'])
        this.editedSite = this.siteService.findByid(this.currentId);
    }


}
