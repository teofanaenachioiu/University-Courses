import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {Product} from './Product';
import {Item} from './Item';
import {ItemList} from './ItemList';

declare var WebSocket: any;

const serverUrl = 'localhost:3000';
const httpServerUrl = `http://${serverUrl}`;
const wsServerUrl = `ws://${serverUrl}`;
const productUrl = `${httpServerUrl}/product`;
const itemUrl = `${httpServerUrl}/item`;

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
    })
};

@Injectable({
    providedIn: 'root'
})
export class ItemService {
    productSubject: BehaviorSubject<Product[]>;
    itemSubject: BehaviorSubject<ItemList[]>;
    items: ItemList[];
    products: Product[];
    isDownloading = true;
    currentPageNumber = 1;
    totalPageNumber = 1;
    isError = false;
    isUploading = false;
    totalItems = 0;
    currentItem = 0;
    isDownloadingAll = true;

    constructor(private http: HttpClient) {
        this.productSubject = new BehaviorSubject([]);
        this.itemSubject = new BehaviorSubject<ItemList[]>([]);

        const ws: any = new WebSocket(wsServerUrl);

        ws.onopen = () => {
            ws.send('send');
            console.log('Conexiune creata');

            this.getPagined(this.currentPageNumber).subscribe(resp => {
                this.totalPageNumber = resp.total / 10;
                console.log(`Current page number ${this.currentPageNumber}`);
                console.log(`Total pages number ${this.totalPageNumber}`);
                this.currentPageNumber = this.currentPageNumber + 1;
                this.productSubject.next(this.productSubject.value.concat(resp.products));
                this.products = this.productSubject.value;
            }, error => {
                console.log(error);
                this.isError = true;
            }, () => {
                this.isDownloading = false;
                if (this.currentPageNumber < this.totalPageNumber) {
                    this.downloadInfo();
                } else {
                    this.isDownloadingAll = false;
                }
            });

        };

        ws.onmessage = eventRecv => {
            const {event, payload} = JSON.parse(eventRecv.data);
            console.log('Intra in onmessage');
            if (event === 'productsChanged') {
                this.isError = true;
                this.currentPageNumber = 1;
            }
        };
    }

    downloadInfo() {
        this.isError = false;
        this.getPagined(this.currentPageNumber).subscribe(resp => {
            this.currentPageNumber = this.currentPageNumber + 1;
            console.log(`Current page number ${this.currentPageNumber}`);
            this.productSubject.next(this.productSubject.value.concat(resp.products));
            this.products = this.productSubject.value;
        }, error => {
            console.log(error);
            this.isError = true;
        }, () => {
            if (this.currentPageNumber <= this.totalPageNumber) {
                this.downloadInfo();
            } else {
                this.isDownloadingAll = false;
            }
        });
    }

    getPagined(page: number): Observable<any> {
        return this.http.get<any>(`${productUrl}?page=${page}`, httpOptions);
    }

    filter(searchTerm: string) {
        if (searchTerm === '') {
            return [];
        }
        return this.products.filter((item) => item.name.indexOf(searchTerm.toLowerCase()) > -1).slice(0, 5);
    }

    upload(items: ItemList[]) {
        this.isUploading = true;
        this.totalItems = items.length;

        for (const item of items) {
            return this.http.post<ItemList>(itemUrl, item, httpOptions).subscribe(res => {
            }, err => {
              console.log(err);
            }, () => {
                this.currentItem = this.currentItem + 1;
                if (this.currentItem === this.totalItems) {
                    this.isUploading = false;
                }
            });
        }
    }

    addItem(item: ItemList) {
        this.itemSubject.next(this.itemSubject.value.concat([item]));
        this.items = this.itemSubject.value;
    }
}
