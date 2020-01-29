import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ItemService} from './item.service';
import {Message} from '../data/Message';
import {IonVirtualScroll} from '@ionic/angular';
import {IonInfiniteScroll} from '@ionic/angular';
import {Router} from '@angular/router';

@Component({
    selector: 'app-home',
    templateUrl: 'home.page.html',
    styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit, OnDestroy {
    @ViewChild(IonInfiniteScroll, null) infiniteScroll: IonInfiniteScroll;
    @ViewChild(IonVirtualScroll, null) virtualScroll: IonVirtualScroll;

    private items: Message[];
    private item: Message;
    private subscriptions = [];
    private username: string;
    private isShowError: boolean = false;
    private canEnterInRoom: boolean = false;

    constructor(private service: ItemService) {
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(subcription => subcription.unsubscribe());
    }

    ngOnInit(): void {

    }

    addSubscriptions() {
        this.subscriptions.push(this.service.getMessages().subscribe());
        this.subscriptions.push(this.service.getAll().subscribe(res => {
            this.items = res;
        }));
    }


    login() {
        console.log(`username ${this.username}`);
        this.service.identify(this.username).subscribe(res => {
            console.log(res);
            localStorage.setItem('token', res.token);
            this.addSubscriptions();
            this.isShowError = false;
            this.canEnterInRoom = true;
        }, err => {
            console.log(err);
            this.isShowError = true;
        });
    }
}
