export class Message {
    id: number;
    text: string;
    created: number;
    room: string;
    username: string;
    mustSend: boolean = false;
}
