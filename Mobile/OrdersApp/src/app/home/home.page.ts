import {Component, OnInit} from '@angular/core';
import {NetworkService} from './network.service';
import {MenuService} from './menu.service';
import {MenuItem} from '../data/MenuItem';

@Component({
    selector: 'app-home',
    templateUrl: 'home.page.html',
    styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {
    private subscriptions = [];
    private items: any;
    private searchTerm: '';
    private itemClick: MenuItem;
    private timer: any;

    constructor(private networkService: NetworkService, private service: MenuService) {
        this.subscriptions.push(this.service.getAll().subscribe(
            items => {
                this.items = items;
            },
            error => {
            }));

    }

    ngOnInit() {

    }

    itemClicked(item: any) {
        this.itemClick = item;
        this.items = this.service.getItems('');
        this.searchTerm = '';
    }

    setFilteredItems() {
        clearTimeout(this.timer);
        this.timer = setTimeout(() => {
            console.log("hei");
            this.service.getItems(this.searchTerm);
        }, 1000);
    }
}
