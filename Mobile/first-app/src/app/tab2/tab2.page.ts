import {Component, OnInit} from '@angular/core';
import {ProductService} from '../product.service';
import {Storage} from '@ionic/storage';
import { createAnimation } from '@ionic/core';

@Component({
    selector: 'app-tab2',
    templateUrl: 'tab2.page.html',
    styleUrls: ['tab2.page.scss']
})
export class Tab2Page implements OnInit {

    constructor(private storage: Storage) {

    }

    ngOnInit(): void {
        // set a key/value
        this.storage.set('name', 'Max').then(() => {

        });

        // Or to get a key/value pair
        this.storage.get('name').then((val) => {
            console.log('Your name is', val);
        });

        createAnimation()
            .addElement(document.querySelector('.img'))
            .duration(1000)
            .direction('alternate')
            .iterations(Infinity)
            .keyframes([
                { offset: 0, transform: 'scale(1)', opacity: '1' },
                { offset: 1, transform: 'scale(1.5)', opacity: '0.5'
                }
            ]).play();
    }

}
