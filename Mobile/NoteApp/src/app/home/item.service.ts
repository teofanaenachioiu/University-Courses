import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {tap} from 'rxjs/operators';

declare var WebSocket: any;

const serverUrl = 'localhost:3000';
const httpServerUrl = `http://${serverUrl}`;
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
  private itemSubject: BehaviorSubject<Product[]>;

  constructor(private http: HttpClient) {
    this.itemSubject = new BehaviorSubject([]);

    const ws: any = new WebSocket(`ws://${serverUrl}`);

    ws.onopen = () => {
      ws.send('send');
      console.log('Conexiune creata');
      this.refresh();
      console.log(this.itemSubject.value);
    };

    ws.onmessage = eventRecv => {
      const {event, payload} = JSON.parse(eventRecv.data);
      console.log('Intra in onmessage');
      if (event === 'productsChanged') {
        this.refresh();
        console.log(this.itemSubject.value);

      }
      console.log(event);
    };
  }

  getAll(): Observable<Product[]> {
    return this.itemSubject.asObservable();
  }

  getPagined(page: number, limit: number): Observable<Product[]> {
    console.log(page);
    console.log(limit);
    return this.http.get<Product[]>(`${itemUrl}?page=${page}&limita=${limit}`, httpOptions);
  }

  refresh(): Observable<Product[]> {
    return this.http.get<Product[]>(itemUrl, httpOptions)
        .pipe(tap(items => this.itemSubject.next(items)));
  }
}
