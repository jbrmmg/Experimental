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
    constructor(private readonly http: HttpClient) {
    }
}
