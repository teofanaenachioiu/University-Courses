import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {MenuItem} from './MenuItem';
import {OrderItem} from './OrderItem';

declare var WebSocket: any;

const serverUrl = '192.168.43.199:3000';
const httpServerUrl = `http://${serverUrl}`;
const itemUrl = `${httpServerUrl}/MenuItem`;
const orderUrl = `${httpServerUrl}/OrderItem`;

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
    private specialMenu: MenuItem;
    private isSpecialSubject: BehaviorSubject<boolean>;
    private timer: any;

    constructor(private http: HttpClient) {
        this.itemSubject = new BehaviorSubject([]);
        this.isSpecialSubject = new BehaviorSubject(false);

        const ws: any = new WebSocket(`ws://${serverUrl}`);

        ws.onopen = () => {
            ws.send('send');
            console.log('Conexiune creata');
        };

        ws.onmessage = eventRecv => {
            const res = JSON.parse(eventRecv.data);
            console.log('Intra in onmessage');
            this.specialMenu = res;

            this.isSpecialSubject.next(true);

            clearTimeout(this.timer);
            this.timer = setTimeout(() => {
                this.isSpecialSubject.next(false);
            }, 3000);
        };
    }

    isSpecialOffer(): Observable<boolean> {
        return this.isSpecialSubject.asObservable();
    }

    getSpecialMenu(): MenuItem {
        return this.specialMenu;
    }

    getAll(): Observable<MenuItem[]> {
        return this.itemSubject.asObservable();
    }

    refresh(characters: string): Observable<MenuItem[]> {
        return this.http.get<MenuItem[]>(`${itemUrl}?q=${characters}`, httpOptions)
            .pipe(tap(items => {
                this.itemSubject.next(items);
            }));
    }

    addOrder(order: OrderItem) {
        return this.http.post<OrderItem[]>(`${orderUrl}`, order, httpOptions);
    }
}
