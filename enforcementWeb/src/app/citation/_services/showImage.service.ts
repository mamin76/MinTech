
import { Injectable } from '@angular/core';
import { HttpClient, HttpBackend } from '@angular/common/http';

import { environment } from '../../../environments/environment';


@Injectable({ providedIn: 'root' })
export class ShowImageService {
    private _http: HttpClient;
    private _token: string = "";
    constructor(handler: HttpBackend) {
        this._http = new HttpClient(handler);
        this._token = `Bearer ${localStorage.getItem('token')}`;
    }
    getImage(id: number) {
        const api = `${environment.baseURL}enforcements/citations/evidences/${id}`;
        return this._http.get<any>(api, { headers: { Authorization: `${this._token}` } });
    }

}
