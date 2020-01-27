import {Component, OnDestroy, OnInit} from '@angular/core';
import {MessageService} from './message.service';


@Component({
    selector: 'app-home',
    templateUrl: 'home.page.html',
    styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit, OnDestroy {
    private subscriptions = [];
    private items: any;
    private clicked = false;
    private msgsGroupedByUserArray = [];
    private senderMessages = new Map();
    private messages = [];
    private myClass: string[];

    constructor(private service: MessageService) {
        this.myClass = [];
        this.items = [];
    }

    ngOnInit(): void {

        this.subscriptions.push(this.service.getAll().subscribe(
            items => {
                const msgsGroupedByUser = items.reduce((r, a) => {

                    r[a.sender] = r[a.sender] || [];
                    if (!a.read) {
                        r[a.sender].push(a);
                    }
                    return r;
                }, Object.create(null));


                this.msgsGroupedByUserArray = Object.keys(msgsGroupedByUser).map(idx => msgsGroupedByUser[idx]);

                this.senderMessages.clear();
                this.items = [];
                this.msgsGroupedByUserArray
                    .forEach(messageArray => {
                            const first = messageArray[0];
                            const nrUnread = messageArray.filter(m => m.read === false).length;
                            const lastDate = Math.max.apply(Math, messageArray.map(o => {
                                return o.created;
                            }));
                            this.items
                                .push({sender: first.sender, nr: nrUnread, lastDate});
                            this.senderMessages.set(first.sender, messageArray);

                        }
                    );
                this.items.sort((a, b) => a.lastDate - b.lastDate);
            },
            error => {
            }));

        this.subscriptions.push(this.service.refresh().subscribe());
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(subcription => subcription.unsubscribe());
    }

    showMessages(sender: string) {
        this.clicked = true;
        this.messages = this.senderMessages.get(sender);
        for (let i = 0; i < this.messages.length; i++) {
            if (this.messages[i].read) {
                this.myClass.push('normal');
            } else {
                this.myClass.push('bold');
            }
        }
    }

    changeClass(i: number) {
        console.log(i);
        clearTimeout();
        setTimeout(() => {
            this.myClass[i] = 'normal';
            this.service.update(this.messages[i]);
        }, 1000);
    }
}
