import {Injectable} from '@angular/core';
import {Proba} from './Proba';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {tap} from 'rxjs/operators';
import {tick} from '@angular/core/testing';

// declare var WebSocket: any;

const serverUrl = 'localhost:8080';
const httpServerUrl = `http://${serverUrl}`;
const itemUrl = `${httpServerUrl}/concurs`;

@Injectable({
  providedIn: 'root'
})
export class ProbaService {

  private itemSubject: BehaviorSubject<Proba[]>;

  constructor(private http: HttpClient) {
    this.itemSubject = new BehaviorSubject([]);
    /*const ws: any = new WebSocket(`ws://${serverUrl}`);

    ws.onopen = () => {
      const token = localStorage.getItem('token');
      ws.send(JSON.stringify({token}));
      console.log('Conexiune creata');
    };

    ws.onmessage = eventRecv => {
      const {event, payload} = JSON.parse(eventRecv.data);
      console.log('Intra in onmessage');
      switch (event) {
        case 'created': {
          const items = this.itemSubject.getValue() || [];
          items.push(payload);
          this.itemSubject.next(items);
          break;
        }
        case 'deleted': {
          const items = this.itemSubject.getValue();
          for (let i = items.length - 1; i >= 0; i--) {
            if (items[i]._id === payload._id) {
              items.splice(i, 1);
            }
          }
          this.itemSubject.next(items);
          break;
        }
        case 'updated': {
          const items = this.itemSubject.getValue();
          for (let i = 0; i < items.length; i++) {
            if (items[i]._id === payload._id) {
              Object.assign(items[i], payload);
            }
          }
          this.itemSubject.next(items);
          break;
        }
      }
      console.log(event);
    };*/
  }

  authHttpOptions() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return httpOptions;
  }

  save(proba: Proba) {
    return this.http.post<Proba>(itemUrl, proba, this.authHttpOptions());
  }

  update(proba: Proba) {
    console.log(proba);
    return this.http.put(`${itemUrl}/probe/${proba.id}`, proba, this.authHttpOptions());
  }

  delete(id: number) {
    return this.http.delete(`${itemUrl}/probe/${id}`, this.authHttpOptions());
  }

  /*getAll(): Observable<Proba[]> {
    return this.itemSubject.asObservable();
  }*/

  getAll(): Observable<Proba[]> {
    return this.http.get<Proba[]>(itemUrl + '/probe', this.authHttpOptions());
    // .pipe(tap(items => this.itemSubject.next(items)));
  }

  getById(id: string) {
    // console.log(`${itemUrl}/${id}`);
    return this.http.get<Proba>(`${itemUrl}/probe/${id}`, this.authHttpOptions());
  }
}
