import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {NetworkService} from './network.service';
import {ItemService} from './item.service';
import {Item} from '../data/Item';
import {IonVirtualScroll} from '@ionic/angular';
import {IonInfiniteScroll} from '@ionic/angular';

@Component({
    selector: 'app-home',
    templateUrl: 'home.page.html',
    styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit, OnDestroy {
    @ViewChild(IonInfiniteScroll, null) infiniteScroll: IonInfiniteScroll;
    @ViewChild(IonVirtualScroll, null) virtualScroll: IonVirtualScroll;

    private items: Item[];
    private item: Item;
    private subscriptions = [];

    constructor(private network: NetworkService, private service: ItemService) {
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(subcription => subcription.unsubscribe());
    }

    ngOnInit(): void {
        // this.subscriptions.push(this.service.refresh().subscribe());
        this.subscriptions.push(this.service.getPaginated().subscribe());
        this.subscriptions.push(this.service.getAll().subscribe(res => {
            this.items = res;
        }));
    }

    loadData(event) {
        console.log('intra in lodadata');
        setTimeout(() => {
            this.subscriptions.push(this.service.getPaginated().subscribe(
                res => {
                    console.log('Done');

                    // verifica conditia de la final
                    event.target.complete();
                    if (res.total === this.items.length) {
                        event.target.disabled = true;
                    }
                }, error => {
                    event.target.complete();
                }
            ));

        }, 1000);
    }
}
