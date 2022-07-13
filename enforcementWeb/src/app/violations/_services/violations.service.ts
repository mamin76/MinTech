
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from '../../../environments/environment';


@Injectable({ providedIn: 'root' })
export class ViolationsService {
    constructor(private _http: HttpClient) {
    }
    getList(page: number, limit: number, sortBy: string, sortDirection: string, query: string = '') {
        let api = `${environment.baseURL}enforcements/violation/search?sortBy=${sortBy}&sortDirection=${sortDirection.toUpperCase()}&page=${page}&size=${limit}`;
        if (query)
            api += `&query=${query}`;
        return this._http.get<any>(api);
    }

    getDetails(id: number) {
        const api = `${environment.baseURL}enforcements/violation/${id}`;
        return this._http.get<any>(api);
    }
    update(id: number, data) {
        const api = `${environment.baseURL}enforcements/violation/${id}`;
        return this._http.put<any>(api, data);
    }
    create(data) {
        const api = `${environment.baseURL}enforcements/violation`;
        return this._http.post<any>(api, data);
    }
    delete(id: number) {
        const api = `${environment.baseURL}enforcements/violation/${id}`;
        return this._http.delete<any>(api);
    }

    checkUniqueName(name: string, lang: string) {
        let api = `${environment.baseURL}enforcements/violation/existByName?name=${name}&lang=${lang}`;
        return this._http.get<any>(api);
    }

}
