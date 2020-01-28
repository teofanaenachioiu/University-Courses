import {Injectable} from '@angular/core';
import {Plugins} from '@capacitor/core';

const {Storage} = Plugins;

@Injectable({
    providedIn: 'root'
})
export class LocalStorageService {

    constructor() {
    }

    async setObject(keyname: string, values: any) {
        await Storage.set({
            key: keyname,
            value: JSON.stringify(values)
        });
    }

    async getObject(keyname: string): Promise<any> {
        const ret = await Storage.get({key: keyname});
        const user = JSON.parse(ret.value);
        return user;
    }

    async setItem(keyname: string, values: any) {
        await Storage.set({
            key: keyname,
            value: values
        });
    }

    async getItem(keyname: string): Promise<any> {
        const {value} = await Storage.get({key: keyname});
        console.log('Got item: ', value);
        return value;
    }

    async removeItem(keyname: string) {
        await Storage.remove({key: keyname});
    }

    async keys(): Promise<any> {
        const {keys} = await Storage.keys();
        return keys;
    }

    async clear() {
        await Storage.clear();
    }
}
