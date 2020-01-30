import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {NetworkService} from './network.service';
import {ItemService} from './item.service';
import {Item} from '../data/Item';
import {IonVirtualScroll} from '@ionic/angular';
import {IonInfiniteScroll} from '@ionic/angular';
import {Router} from '@angular/router';

@Component({
    selector: 'app-home',
    templateUrl: 'home.page.html',
    styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit, OnDestroy {
    private isShowError: boolean = false;
    private table: number;

    constructor(private network: NetworkService, private service: ItemService, private router: Router) {
    }

    ngOnDestroy(): void {
    }

    ngOnInit(): void {
    }

    login() {
        console.log(`table ${this.table}`);
        this.service.identify(this.table).subscribe(res => {
            console.log(res);

            localStorage.setItem('token', res.token);
            localStorage.setItem('table', res.token);
            this.isShowError = false;
            this.router.navigate(['table']);
        }, err => {
            console.log(err);
            this.isShowError = true;
        });
    }
}
