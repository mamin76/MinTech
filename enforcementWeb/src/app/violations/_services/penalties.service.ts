
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from '../../../environments/environment';


@Injectable({ providedIn: 'root' })
export class PenaltiesService {
    constructor(private _http: HttpClient) {
    }
    getList(page: number, limit: number, query: string = '') {
        let api = `${environment.baseURL}enforcements/penalties/search?page=${page}&size=${limit}`;
        if (query)
            api += `&name=${query}`;
        return this._http.get<any>(api);
    }

    getDetails(id: number) {
        const api = `${environment.baseURL}enforcements/penalties/details?penaltyId=${id}`;
        return this._http.get<any>(api);
    }
    update(data) {
        const api = `${environment.baseURL}enforcements/penalties/edit`;
        return this._http.put<any>(api, data);
    }
    create(data) {
        const api = `${environment.baseURL}enforcements/penalties`;
        return this._http.post<any>(api, data);
    }
    delete(id: number) {
        const api = `${environment.baseURL}enforcements/penalties/delete?penaltyId${id}`;
        return this._http.delete<any>(api);
    }
}
