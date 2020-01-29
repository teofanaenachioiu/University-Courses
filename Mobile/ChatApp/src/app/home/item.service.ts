import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {Message} from '../data/Message';
import {tap} from 'rxjs/operators';

declare var WebSocket: any;

const serverUrl = 'localhost:3000';
const httpServerUrl = `http://${serverUrl}`;
const wsServerUrl = `ws://${serverUrl}`;
const itemUrl = `${httpServerUrl}/message`;


const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
    })
};

@Injectable({
    providedIn: 'root'
})
export class ItemService {
    private itemSubject: BehaviorSubject<Message[]>;
    private allItemSubject: BehaviorSubject<Message[]>;
    private created = 0;


    constructor(private http: HttpClient) {
        this.itemSubject = new BehaviorSubject([]);
        this.allItemSubject = new BehaviorSubject([]);

        const ws: any = new WebSocket(wsServerUrl);

        ws.onopen = () => {
            ws.send('send');
            console.log('Connected to server via WebSockets');
        };

        ws.onmessage = eventRecv => {
            const res = JSON.parse(eventRecv.data);
            console.log('Receive new message via WebSockets');
            this.allItemSubject.next(this.allItemSubject.value.concat([res]));
            localStorage.setItem('itemsAll', JSON.stringify(this.allItemSubject.value));
        };
    }

    // verifica ce vine de pe server (res)
    getMessages(): Observable<any> {
        console.log('in get message');
        return this.http.get<any>(`${itemUrl}/?created=${this.created}`, this.httpOptionsToken()).pipe(tap(res => {
            this.itemSubject.next(res);
            this.allItemSubject.next(this.allItemSubject.value.concat(res));
            this.created = Math.max.apply(Math, res.map(o => o.created));
            localStorage.setItem('items', JSON.stringify(this.itemSubject.value));
            localStorage.setItem('itemsAll', JSON.stringify(this.allItemSubject.value));
        }));
    }

    identify(username: string) {
        return this.http.post<any>(`${httpServerUrl}/login`, {username}, httpOptions);
    }

    getAll(): Observable<any> {
        return this.itemSubject.asObservable();
    }

    httpOptionsToken() {
        return {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                token: `${localStorage.getItem('token')}`,
                Authorization: `Bearer ${localStorage.getItem('token')}`
            })
        };
    }

    sendMessage(messageText: string, room: string) {

        return this.http.post<Message>(`${httpServerUrl}/message`, {text: messageText, room}, this.httpOptionsToken())
            .pipe(tap(res => {
            }, err => {
                const message = new Message();
                message.id = 0;
                message.text = messageText;
                message.mustSend = true;
                message.room = room;
                this.allItemSubject.next(this.allItemSubject.value.concat([message]));
            }));
    }

    sendMess(messageText: string, room: string) {

        return this.http.post<Message>(`${httpServerUrl}/message`, {text: messageText, room}, this.httpOptionsToken())
            .pipe(tap(res => {
                const it = this.allItemSubject.value.filter(t => !t.mustSend || t.text !== messageText || t.room !== room);
                this.allItemSubject.next(it);
            }, err => {

            }));
    }

    getMessagesAll(): Observable<Message[]> {
        return this.allItemSubject.asObservable();
    }
}
