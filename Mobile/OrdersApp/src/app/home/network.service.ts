import {Injectable} from '@angular/core';
import {Network} from '@ionic-native/network/ngx';
import {ToastController} from '@ionic/angular';

@Injectable({
    providedIn: 'root'
})
export class NetworkService {
    internetStatus = true;

    constructor(private network: Network,  private toastController: ToastController) {
        if (this.network.type === 'none') {
            this.internetStatus = false;
        }

        this.network.onDisconnect().subscribe(() => {
            this.internetStatus = false;
            const toast = this.toastController.create({
                message: `You are now offline`,
                duration: 3000,
                position: 'bottom'
            });
            toast.then(t => t.present());
        });

        this.network.onConnect().subscribe(() => {
            this.internetStatus = true;
            const toast = this.toastController.create({
                message: `You are now online`,
                duration: 3000,
                position: 'bottom'
            });
            toast.then(t => t.present());
        });
    }

    isOnline(): boolean {
        return this.internetStatus;
    }
}
