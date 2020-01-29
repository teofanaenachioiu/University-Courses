import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {Item} from '../data/Item';
import {tap} from 'rxjs/operators';

declare var WebSocket: any;

const serverUrl = '10.152.3.179:3000';
const httpServerUrl = `http://${serverUrl}`;
const wsServerUrl = `ws://${serverUrl}`;
const itemUrl = `${httpServerUrl}/product`;


const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
    })
};

@Injectable({
    providedIn: 'root'
})
export class ItemService {
    private itemSubject: BehaviorSubject<Item[]>;
    private page = 1;

    constructor(private http: HttpClient) {
        this.itemSubject = new BehaviorSubject([]);

        const ws: any = new WebSocket(wsServerUrl);

        ws.onopen = () => {
            ws.send('send');
            console.log('Connected to server via WebSockets');
        };

        ws.onmessage = eventRecv => {
            const res = JSON.parse(eventRecv.data);
            console.log('Receive new message via WebSockets');
            console.log(res);
        };
    }

    // verifica ce vine de pe server
    getPaginated(): Observable<any> {
        return this.http.get<any>(`${itemUrl}?page=${this.page}`, httpOptions).pipe(tap(
            res => {
                // console.log(res);
                const items = res.products;
                this.itemSubject.next(this.itemSubject.value.concat(items));
                // console.log(this.itemSubject.value);
                localStorage.setItem('items', JSON.stringify(this.itemSubject.value));
                this.page++;
            },
            error => {
                // la eroare imi iau din local storage
                this.itemSubject.next(JSON.parse(localStorage.getItem('items')));
            }));
    }

    // verifica ce vine de pe server (res)
    refresh(): Observable<any> {
        return this.http.get<any>(itemUrl, httpOptions).pipe(tap(res => {
            console.log(res);
            this.itemSubject.next(res);
            localStorage.setItem('items', JSON.stringify(this.itemSubject.value));
        }));
    }

    // sterge-l si local
    delete(item: Item) {
        return this.http.delete<any>(`${itemUrl}/${item.id}`, httpOptions);
    }

    // actualizeaza-l si local
    update(item: Item) {
        return this.http.put<any>(`${itemUrl}/${item.id}`, item, httpOptions);
    }

    getAll(): Observable<any> {
        return this.itemSubject.asObservable();
    }

    // modifHttpOptions(): any {
    //     return {
    //         headers: new HttpHeaders({
    //             'Content-Type': 'application/json',
    //             'If-Modified-Since': `${this.lastModif}`
    //         })
    //     };
    // }
}
