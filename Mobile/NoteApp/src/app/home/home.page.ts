import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {NetworkService} from './network.service';
import {ItemService} from './item.service';
import {Note} from '../data/Note';
import {ActionSheetController, IonInfiniteScroll, IonVirtualScroll} from '@ionic/angular';

@Component({
    selector: 'app-home',
    templateUrl: 'home.page.html',
    styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit, OnDestroy {
    items: Note[];
    item: Note;
    private subscriptions = [];
    private page = 1;
    @ViewChild(IonInfiniteScroll, null) infiniteScroll: IonInfiniteScroll;
    @ViewChild(IonVirtualScroll, null) virtualScroll: IonVirtualScroll;

    constructor(private itemService: ItemService,
                public actionSheetController: ActionSheetController,
                private network: NetworkService) {
    }

    async ngOnInit() {
        this.page = 1;
        this.subscriptions.push(this.itemService.getPagined(this.page).subscribe(
            res => {
                console.log('RESULT');
                console.log(res);
                if (res.status === 304) {
                    this.items = JSON.parse(localStorage.getItem('items'));
                } else {
                    this.itemService.lastModif = res.headers.get('Last-Modified');
                    this.itemService.itemSubject.next(res.body.notes);
                    this.items = res.body.notes;
                    localStorage.setItem('items', JSON.stringify(this.items));
                    this.page++;
                }
            },
            error => {
                this.items = JSON.parse(localStorage.getItem('items'));
            }));
        this.subscriptions.push(this.itemService.getAll().subscribe(res => {
            this.items = res;
        }));
    }

    ngOnDestroy() {
        this.subscriptions.forEach(subcription => subcription.unsubscribe());
    }


    loadData(event) {
        console.log('intra in lodadata');
        this.subscriptions.push(this.itemService.getPagined(this.page).subscribe(
            res => {
                console.log('Page:');
                console.log(this.page);
                if (res.status === 304) {
                    this.items = JSON.parse(localStorage.getItem('items'));
                } else {
                    this.itemService.lastModif = res.headers.get('Last-Modified');
                    this.itemService.addElems(res.body.notes);
                    this.items = this.items.concat(res.body.notes);
                    console.log(this.items);
                    this.page++;

                    setTimeout(() => {
                        console.log('Done');
                        event.target.complete();

                        if (res.body.more === false) {
                            console.log('falseeee');
                            event.target.disabled = true;
                        }
                    }, 500);
                }
            },
            error => {
                console.log(error);
                event.target.complete();
            }));
    }


    async presentActionSheet(item: Note, index: number) {
        const actionSheet = await this.actionSheetController.create({
            header: 'Albums',
            buttons: [{
                text: 'Delete',
                role: 'destructive',
                icon: 'trash',
                handler: () => {
                    console.log('Delete clicked');
                    this.itemService.delete(item).subscribe(res => {

                    }, error => {
                        this.items[index].deleted = true;
                        const refreshId = setInterval(() => {
                            this.itemService.delete(item).subscribe(res => {
                            }, err => {
                            }, () => {
                                clearInterval(refreshId);
                                this.items[index].deleted = false;
                                this.items = this.items.slice(0, index).concat(this.items.slice(index + 1, this.items.length));
                            });
                        }, 10000);
                    }, () => {
                        this.items = this.items.slice(0, index).concat(this.items.slice(index + 1, this.items.length));
                    });
                }
            }, {
                text: 'Cancel',
                icon: 'close',
                role: 'cancel',
                handler: () => {
                    console.log('Cancel clicked');
                }
            }]
        });
        await actionSheet.present();
    }
}
