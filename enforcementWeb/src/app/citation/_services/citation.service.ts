
import { Injectable } from '@angular/core';
import { HttpClient, HttpBackend } from '@angular/common/http';

import { environment } from '../../../environments/environment';


@Injectable({ providedIn: 'root' })
export class CitationService {
    // private _http: HttpClient;
    // private _token: string = "";
    constructor(handler: HttpBackend, private _http: HttpClient) {
        // this._http = new HttpClient(handler);
        // this._token = `Bearer ${localStorage.getItem('token')}`;
    }
    getList(offset: number, limit: number, sortBy: string, sortDirection: string, query: string = '') {
        let api = `${environment.baseURL}enforcements/citations/unpaidCitations?offset=${offset}&limit=${limit}&sortBy=${sortBy}&sortDirection=${sortDirection.toUpperCase()}`;
        if (query)
            api += `&query=${query}`;
        console.log('CitationService getList api', api);
        return this._http.get<any>(api);
    }

    getDetails(ids: string) {
        const api = `${environment.baseURL}enforcements/citations/citationsDetails?citationIds=${ids}`;
        return this._http.get<any>(api);
    }

    payCitation(data) {
        return this._http.put(`${environment.baseURL}enforcements/citations/payCitations`, data);
    }


}
