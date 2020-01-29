import {Injectable} from '@angular/core';
import {ToastController} from '@ionic/angular';
import {Plugins} from '@capacitor/core';

const {Network} = Plugins;

@Injectable({
    providedIn: 'root'
})
export class NetworkService {
    internetStatus = true;

    constructor(private toastController: ToastController) {
        Network.addListener('networkStatusChange', (status) => {
            console.log('Network status changed', status);

            this.internetStatus = status.connected;
            const msg = `Device is ${status.connected ? 'online' : 'offline'}`;

            this.toastController.create({
                message: msg,
                duration: 3000,
                position: 'bottom'
            }).then(t => t.present());
        });
    }

    isOnline(): boolean {
        return this.internetStatus;
    }
}
