import {Component, OnDestroy, OnInit} from '@angular/core';
import {MenuItem} from './MenuItem';
import {ItemService} from './item.service';
import {NetworkService} from './network.service';
import {OrderItem} from './OrderItem';

@Component({
    selector: 'app-home',
    templateUrl: 'home.page.html',
    styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit, OnDestroy {
    private items: MenuItem[];
    private orders: OrderItem[] = [];
    private subscriptions = [];
    private item: MenuItem = null;
    private searchTerm = '';
    private timer: any;
    private quantity: number;
    private myClass: string[] = [];
    private isSpecialOffer: boolean = false;
    private specialMenu: MenuItem = null;


    constructor(private service: ItemService, private network: NetworkService) {
    }

    ngOnInit(): void {
        this.subscriptions.push(this.service.getAll().subscribe(
            items => {
                this.addInCache(items);
                this.items = items.slice(0, 5);
            },
            error => {
            }));

        this.subscriptions.push(this.service.refresh('...').subscribe());

        this.subscriptions.push(this.service.isSpecialOffer().subscribe(res => {
            this.isSpecialOffer = res;
            this.specialMenu = this.service.getSpecialMenu();
        }));

        if (this.network.isOnline()) {
            console.log('online');
        } else {
            console.log('offline');
        }

        this.orders = JSON.parse(localStorage.getItem('orders'));
        if (this.orders === null) {
            this.orders = [];
        }
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(subcription => subcription.unsubscribe());
    }

    addOrder(item: MenuItem) {
        this.item = item;
        this.searchTerm = '';
        this.service.refresh('...').subscribe();
    }

    setFilteredItems() {
        clearTimeout(this.timer);
        this.timer = setTimeout(() => {
            if (this.searchTerm === '') {
                if (this.network.isOnline()) {
                    this.service.refresh('...').subscribe();
                } else {
                    this.items = this.getItemsLocal('...');
                }
            } else {
                if (this.network.isOnline()) {
                    this.service.refresh(this.searchTerm).subscribe();
                } else {
                    this.items = this.getItemsLocal(this.searchTerm);
                }
            }
        }, 1000);
    }

    addInCache(newItems: any) {
        let res = [];
        res = JSON.parse(localStorage.getItem('items'));
        if (res === null) {
            res = [];
        }

        const itemsAg = res.concat(newItems);

        const newItemss = [];

        for (let item of itemsAg) {
            if (newItemss.filter(it => it.code === item.code).length === 0) {
                newItemss.push(item);
            }
        }

        localStorage.setItem('items', JSON.stringify(newItemss));
    }

    getItemsLocal(characters: string): MenuItem[] {
        const itemsLocal = JSON.parse(localStorage.getItem('items'));
        return itemsLocal.filter(menuItem => menuItem.name.indexOf(characters) !== -1).slice(0, 5);
    }

    addSpecialOfferInList() {
        const order = new OrderItem();
        order.code = this.specialMenu.code;
        order.name = this.specialMenu.name;
        order.quantity = 1;
        order.free = true;
        this.orders.push(order);
        this.myClass.push('bold');

        this.service.addOrder(order).subscribe(res => {
        }, err => {
            console.log('try update');
            this.tryUpdate(order, this.myClass.length - 1);
        }, () => {
            this.myClass[this.myClass.length - 1] = 'normal';
        });
        localStorage.setItem('orders', JSON.stringify(this.orders));
    }

    addInOrderList() {
        const order = new OrderItem();
        order.code = this.item.code;
        order.name = this.item.name;
        order.quantity = this.quantity;
        order.free = false;
        this.orders.push(order);
        this.myClass.push('bold');
        this.quantity = null;

        this.service.addOrder(order).subscribe(res => {
        }, err => {
            console.log('try update');
            this.tryUpdate(order, this.myClass.length - 1);
        }, () => {
            this.myClass[this.myClass.length - 1] = 'normal';
        });
        localStorage.setItem('orders', JSON.stringify(this.orders));
    }

    private tryUpdate(order: OrderItem, index: number) {
        const refreshId = setInterval(() => {
            if (this.network.isOnline()) {
                this.service.addOrder(order).subscribe(r => {
                }, e => {
                    this.myClass[index] = 'bold';
                }, () => {
                    this.myClass[index] = 'normal';
                    clearInterval(refreshId);
                });
            }
        }, 3000);
    }
}
