import {Component, OnInit} from '@angular/core';
import {Network} from '@ionic-native/network/ngx';
import {NetworkService} from '../network.service';

@Component({
    selector: 'app-tab3',
    templateUrl: 'tab3.page.html',
    styleUrls: ['tab3.page.scss']
})
export class Tab3Page implements OnInit {

    constructor(private network: NetworkService) {
    }

    ngOnInit() {

    }

}
