import {Component, OnDestroy, OnInit} from '@angular/core';
import {Item} from '../data/Item';
import {ItemService} from '../home/item.service';
import {MenuItem} from '../data/MenuItem';
import {OrderItem} from '../data/OrderItem';
import {Router} from '@angular/router';

@Component({
    selector: 'app-table',
    templateUrl: './table.page.html',
    styleUrls: ['./table.page.scss'],
})
export class TablePage implements OnInit, OnDestroy {
    private menuItems: MenuItem[];
    private menuItem: MenuItem;
    private subscriptions = [];
    private quantity: number;
    private searchTerm: string;
    private timer: any;
    private orderItems: OrderItem[];
    private isSpecialOffer: boolean;
    private specialItem: MenuItem;

    constructor(private service: ItemService, private router: Router) {
        const token = localStorage.getItem('token');
        if (!token) {
            this.router.navigate(['/']);
        }
    }

    ngOnInit() {
        this.subscriptions.push(this.service.getAll().subscribe(res => {
            this.menuItems = res.slice(0, 5);
        }));
        this.subscriptions.push(this.service.getAllOrderItems().subscribe(res => {
            this.orderItems = res;
        }));
        this.subscriptions.push(this.service.isSpecialOffer().subscribe(res => {
            this.specialItem = this.service.getSpecialOffer();
            this.isSpecialOffer = res;
        }));
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(subcription => subcription.unsubscribe());
    }

    orderItem() {
        if (this.menuItem == null) {
            return;
        }
        const order = {code: this.menuItem.code, quantity: this.quantity, free: false, name: this.menuItem.name};
        this.service.orderItem(order).subscribe();
    }

    itemClicked(item: any) {
        this.menuItem = item;
        this.service.getItems('...').subscribe();
        this.searchTerm = '';
    }

    setFilteredItems() {
        clearTimeout(this.timer);
        this.timer = setTimeout(() => {
            console.log('in filter timeout');
            if (this.searchTerm == '') {
                this.service.getItems('...').subscribe();
            } else {
                this.service.getItems(this.searchTerm).subscribe();
            }
        }, 500);
    }

    sendMess(item: OrderItem) {
        this.service.sendMess(item).subscribe();
    }

    buyOffer(specialItem: MenuItem) {
        console.log('buyyy');
        const elem = {code: specialItem.code, quantity: 1, free: true, name: specialItem.name};
        this.service.disableSpecialOffer();

        this.service.orderItem(elem).subscribe(res => {
        });
    }
}
