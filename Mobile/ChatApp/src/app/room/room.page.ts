import {Component, OnDestroy, OnInit} from '@angular/core';
import {Message} from '../data/Message';
import {ItemService} from '../home/item.service';

@Component({
    selector: 'app-room',
    templateUrl: './room.page.html',
    styleUrls: ['./room.page.scss'],
})
export class RoomPage implements OnInit, OnDestroy {
    private groupedMessages = new Map();
    private items: Message[] = [];
    private room: string = null;
    private subscriptions = [];
    private messageText: string;
    private rooms: string[];
    isSending: boolean = false;

    constructor(private service: ItemService) {
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(subcription => subcription.unsubscribe());
    }

    ngOnInit(): void {
        this.subscriptions.push(this.service.getMessagesAll().subscribe(res => {
            this.items = res;
            this.createMap();
        }));
        this.subscriptions.push(this.service.getMessages().subscribe(res => {
            this.items = JSON.parse(localStorage.getItem('itemsAll'));
            this.createMap();
        }, err => {
            this.items = JSON.parse(localStorage.getItem('itemsAll'));
            this.createMap();
        }));
    }

    sendMessage() {
        const message = {text: this.messageText, mustSend: true, room: this.room};

        if (message.room == null) {
            return;
        }
        this.service.sendMessage(this.messageText, this.room).subscribe(res => {
            // il primesc prin ws
        }, err => {
        }, () => {
            this.messageText = null;
        });
    }

    private createMap() {
        this.groupedMessages.clear();

        const msgsGroupedByUser = this.items.reduce((r, a) => {
            r[a.room] = r[a.room] || [];
            r[a.room].push(a);
            return r;
        }, Object.create(null));


        Object.keys(msgsGroupedByUser)
            .map(idx => msgsGroupedByUser[idx])
            .forEach(messageArray => {
                    const first = messageArray[0];
                    const sortedArr = messageArray.sort((m1, m2) => m1.created - m2.created);
                    this.groupedMessages.set(first.room, sortedArr);
                }
            );
        this.rooms = [...this.groupedMessages.keys()];
    }

    showMessages(room: string) {
        this.room = room;
    }

    sendMess(item: any) {
      console.log(this.isSending);
        this.isSending = true;
      console.log(this.isSending);

        setTimeout(() => {
            this.service.sendMess(item.text, item.room).subscribe(res => {
                // il primesc prin ws
                this.isSending = false;

            }, err => {
                this.isSending = false;
            }, () => {
            });
        }, 3000);

    }
}
