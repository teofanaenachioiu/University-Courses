import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {Item} from '../data/Item';
import {tap} from 'rxjs/operators';
import {MenuItem} from '../data/MenuItem';
import {OrderItem} from '../data/OrderItem';

declare var WebSocket: any;

const serverUrl = 'localhost:3000';
const httpServerUrl = `http://${serverUrl}`;
const wsServerUrl = `ws://${serverUrl}`;
const itemUrl = `${httpServerUrl}/MenuItem`;
const authUrl = `${httpServerUrl}/auth`;


const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
    })
};

@Injectable({
    providedIn: 'root'
})
export class ItemService {
    private itemSubject: BehaviorSubject<MenuItem[]>;
    private orderItemSubject: BehaviorSubject<OrderItem[]>;
    private isSpecialOfferSubject: BehaviorSubject<boolean>;
    private specialMenu: MenuItem;
    private timer: any;

    constructor(private http: HttpClient) {
        this.itemSubject = new BehaviorSubject([]);
        this.orderItemSubject = new BehaviorSubject([]);
        this.isSpecialOfferSubject = new BehaviorSubject(false);

        const orders = localStorage.getItem('orders');
        if (orders != null) {
            this.orderItemSubject.next(JSON.parse(orders));
        }

        const ws: any = new WebSocket(wsServerUrl);

        ws.onopen = () => {
            ws.send('send');
            console.log('Connected to server via WebSockets');
        };

        ws.onmessage = eventRecv => {
            const res = JSON.parse(eventRecv.data);
            console.log('Receive new message via WebSockets');
            console.log(res);
            this.specialMenu = res;

            this.isSpecialOfferSubject.next(true);

            clearTimeout(this.timer);
            this.timer = setTimeout(() => {
                this.isSpecialOfferSubject.next(false);
            }, 3000);
        };
    }

    getSpecialOffer(): MenuItem {
        return this.specialMenu;
    }

    // verifica ce vine de pe server (res)
    refresh(): Observable<any> {
        return this.http.get<any>(itemUrl, this.authOptions()).pipe(tap(res => {
            console.log(res);
            this.itemSubject.next(res);
            localStorage.setItem('items', JSON.stringify(this.itemSubject.value));
        }));
    }

    getItems(characters: string): Observable<any> {
        return this.http.get<any>(`${itemUrl}?q=${characters}`, this.authOptions()).pipe(tap(res => {
            console.log(res);
            this.itemSubject.next(res);
            localStorage.setItem('items', JSON.stringify(this.itemSubject.value));
        }));
    }

    getAll(): Observable<any> {
        return this.itemSubject.asObservable();
    }

    isSpecialOffer(): Observable<any> {
        return this.isSpecialOfferSubject.asObservable();
    }


    identify(table: number) {
        console.log('in identify function');
        return this.http.post<any>(`${httpServerUrl}/auth`, {table}, httpOptions);
    }


    authOptions(): any {
        return {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                Authorization: `${localStorage.getItem('token')}`
            })
        };
    }

    orderItem(order: { code: number; quantity: number; free: boolean; name: string }) {
        return this.http.post<OrderItem>(`${httpServerUrl}/OrderItem`, order, this.authOptions()).pipe(tap(res => {
            console.log('result la post order item');
            console.log(res);
            const message = new OrderItem();
            message.code = order.code;
            message.free = order.free;
            message.quantity = order.quantity;
            message.name = order.name;
            this.orderItemSubject.next(this.orderItemSubject.value.concat([message]));
            localStorage.setItem('orders', JSON.stringify(this.orderItemSubject.value));
        }, err => {
            const message = new OrderItem();
            message.code = order.code;
            message.free = order.free;
            message.mustSend = true;
            message.quantity = order.quantity;
            message.name = order.name;
            this.orderItemSubject.next(this.orderItemSubject.value.concat([message]));
        }));
    }

    getAllOrderItems(): Observable<any> {
        return this.orderItemSubject.asObservable();
    }

    sendMess(item: OrderItem) {
        return this.http.post<OrderItem>(`${httpServerUrl}/OrderItem`, item, this.authOptions()).pipe(tap(res => {
            console.log('result la post send mess');
            console.log(res);
            const it = this.orderItemSubject.value
                .filter(t => !t.mustSend || t.code !== item.code || t.quantity !== item.quantity || t.name !== item.name);
            const message = new OrderItem();
            message.code = item.code;
            message.free = item.free;
            message.quantity = item.quantity;
            message.name = item.name;
            it.push(message);
            this.orderItemSubject.next(it);
            localStorage.setItem('orders', JSON.stringify(this.orderItemSubject.value));
        }, err => {
        }));
    }

    disableSpecialOffer() {
        this.isSpecialOfferSubject.next(false);
    }
}

