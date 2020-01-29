import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {Note} from '../data/Note';
import {not} from 'rxjs/internal-compatibility';

declare var WebSocket: any;

const serverUrl = '10.152.3.179:3000';
const httpServerUrl = `http://${serverUrl}`;
const itemUrl = `${httpServerUrl}/note`;


const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
    })
};

@Injectable({
    providedIn: 'root'
})
export class ItemService {
    itemSubject: BehaviorSubject<Note[]>;
    lastModif = new Date(2018, 1, 1);

    constructor(private http: HttpClient) {
        this.itemSubject = new BehaviorSubject([]);

        const ws: any = new WebSocket(`ws://${serverUrl}`);

        ws.onopen = () => {
            ws.send('send');
            console.log('Conexiune creata');
        };

        ws.onmessage = eventRecv => {
            const {event, note} = JSON.parse(eventRecv.data);
            console.log('Intra in onmessage');
            console.log(event);
            switch (event) {
                case 'inserted': {
                    const items = this.itemSubject.getValue() || [];
                    items.push(note);
                    this.itemSubject.next(items);
                    localStorage.setItem('items', JSON.stringify(items));
                    break;
                }
                case 'deleted': {
                    const items = this.itemSubject.getValue();
                    for (let i = items.length - 1; i >= 0; i--) {
                        if (items[i].id === note.id) {
                            items.splice(i, 1);
                        }
                    }
                    this.itemSubject.next(items);
                    localStorage.setItem('items', JSON.stringify(items));
                    break;
                }
            }
        };
    }


    getPagined(page: number): Observable<any> {
        return this.http.get<any>(`${itemUrl}?page=${page}`, {headers: this.modifHttpOptions(), observe: 'response'});
    }

    delete(item: Note) {
        return this.http.delete<any>(`${itemUrl}/${item.id}`, httpOptions);
    }


    getAll(): Observable<any> {
        return this.itemSubject.asObservable();
    }

    addElems(items: Note[]) {
        this.itemSubject.next(this.itemSubject.value.concat(items));
        localStorage.setItem('items', JSON.stringify(this.itemSubject.value));
    }


    modifHttpOptions(): any {
        return {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'If-Modified-Since': `${this.lastModif}`
            })
        };
    }
}
