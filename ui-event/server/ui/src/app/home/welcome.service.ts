import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

export class ReminderRequest {
    what: string;
    detail: string;
}

@Injectable({
    providedIn: 'root'
})
export class WelcomeService {
//    private readonly update;

    constructor(private readonly http: HttpClient) {
//        this.update = new EventSource('exp/file-updates');

//        this.update.addListener('message', e => { console.log(e.data) });
    }
}
