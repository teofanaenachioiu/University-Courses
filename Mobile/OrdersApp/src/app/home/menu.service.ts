import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {MenuItem} from '../data/MenuItem';

declare var WebSocket: any;

const serverUrl = 'localhost:3000';
const httpServerUrl = `http://${serverUrl}`;
const itemUrl = `${httpServerUrl}/MenuItem`;

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
    })
};

@Injectable({
    providedIn: 'root'
})
export class MenuService {
    private itemSubject: BehaviorSubject<MenuItem[]>;

    constructor(private http: HttpClient) {
        this.itemSubject = new BehaviorSubject([]);

        const ws: any = new WebSocket(`ws://${serverUrl}`);

        ws.onopen = () => {
            ws.send('send');
            console.log('Conexiune creata');
        };

        ws.onmessage = eventRecv => {
            const res = JSON.parse(eventRecv.data);
            console.log('Intra in onmessage');
            console.log(res);
        };
    }

    getAll(): Observable<MenuItem[]> {
        return this.itemSubject.asObservable();
    }

    getItems(characters: string): Observable<MenuItem[]> {
        console.log(characters);
        const url = `${itemUrl}?q=${characters}`;
        console.log(url);
        return this.http.get<any>(url, httpOptions)
            .pipe(tap(items => {
                console.log(items);
                this.itemSubject.next(items.slice(0, 5));
            }, err => {
                console.log(err);
            }));
    }
}
