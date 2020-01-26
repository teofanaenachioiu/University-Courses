import {Component} from '@angular/core';
import {ItemService} from '../item.service';
import {Product} from '../Product';
import {ItemList} from '../ItemList';

@Component({
    selector: 'app-home',
    templateUrl: 'home.page.html',
    styleUrls: ['home.page.scss'],
})
export class HomePage {
    private searchTerm: '';
    private products: Product[];
    private timer: any;
    private itemsToAdd: ItemList[];
    private quantity: number;
    private product: Product;

    constructor(private service: ItemService) {
        this.itemsToAdd = [];
    }

    setFilteredItems() {
        clearTimeout(this.timer);
        this.timer = setTimeout(() => {
            this.products = this.service.filter(this.searchTerm);
        }, 1000);
    }

    upload() {
        this.service.upload(this.itemsToAdd);
    }

    add() {
        if (this.product == null || this.quantity == null) {
            return;
        }

        const item = new ItemList();
        item.code = this.product.code;
        item.quantity = this.quantity;
        item.name = this.product.name;

        this.itemsToAdd.push(item);
        this.service.addItem(item);

        this.product = null;
        this.quantity = null;
    }

    productClicked(product: Product) {
        this.product = product;
        this.products = this.service.filter('');
        this.searchTerm = '';
    }
}
