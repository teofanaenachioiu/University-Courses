import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {Message} from './Message';
import {Storage} from '@ionic/storage';

declare var WebSocket: any;

const serverUrl = 'localhost:3000';
const httpServerUrl = `http://${serverUrl}`;
const itemUrl = `${httpServerUrl}/message`;

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
    })
};

@Injectable({
    providedIn: 'root'
})
export class MessageService {
    private itemSubject: BehaviorSubject<Message[]>;
    private itemsToUpdate = [];


    constructor(private http: HttpClient, private storage: Storage) {
        this.itemSubject = new BehaviorSubject([]);

        this.storage.get('items').then((val) => {
            if (val !== undefined) {
                this.itemSubject.next(val);
            } else {
            }
        });
        const ws: any = new WebSocket(`ws://${serverUrl}`);

        ws.onopen = () => {
            ws.send('send');
            console.log('Conexiune creata');
        };

        ws.onmessage = eventRecv => {
            const recv = JSON.parse(eventRecv.data);
            console.log('Intra in onmessage');
            this.refresh();
        };
    }

    getAll(): Observable<Message[]> {
        return this.itemSubject.asObservable();
    }


    refresh(): Observable<Message[]> {
        return this.http.get<Message[]>(itemUrl, httpOptions)
            .pipe(tap(items => {
                    this.itemSubject.next(items);
                    this.storage.set('items', items);
                }
            ));
    }

    update(message: Message) {
        const messagesss = this.itemSubject.value;
        const mes = messagesss.findIndex(m => m.id === message.id);
        messagesss[mes].read = true;
        this.itemSubject.next(messagesss);
        this.storage.set('items', messagesss);

        message.read = true;

        return this.http.put<Message[]>(`${itemUrl}/` + message.id, message, httpOptions)
            .subscribe(res => {

            }, error => {
                this.itemsToUpdate.push(message);
                this.tryUpdate();
            });
    }

    up(message: Message) {
        return this.http.put<Message[]>(`${itemUrl}/` + message.id, message, httpOptions)
            .subscribe(res => {
            }, error => {
                this.itemsToUpdate.push(message);
                this.tryUpdate();
            }, () => {
                this.tryUpdate();
            });
    }

    tryUpdate() {
        clearTimeout();
        setTimeout(() => {
            if (this.itemsToUpdate.length > 0) {
                const mess = this.itemsToUpdate[0];
                this.itemsToUpdate = this.itemsToUpdate.slice(1, this.itemsToUpdate.length);
                this.up(mess);
            }
        }, 3000);

    }
}
