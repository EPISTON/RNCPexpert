import { Component } from '@angular/core';


@Component({
  moduleId: module.id,
  selector: 'nav-bar',
  templateUrl: './navbar.component.html',
})
export class NavbarComponent {
    appName : string;
    constructor() {
        this.appName = "voyagez malin";
    }
}