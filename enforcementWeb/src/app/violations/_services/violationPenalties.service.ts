
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from '../../../environments/environment';
import { ViolationPenaliesSave } from '../_models';

@Injectable({ providedIn: 'root' })
export class ViolationPenaltiesService {
    constructor(private _http: HttpClient) {
    }
    getList(page: number, limit: number, operationId: number, query: string = '') {
        let api = `${environment.baseURL}enforcements/OperationViolationPenalties/search?page=${page}&limit=${limit}&operationId=${operationId}&language=en`;
        if (query)
            api += `&name=${query}`;
        return this._http.get<any>(api);
    }

    getDetails(violationId: number, operationId: number) {
        const api = `${environment.baseURL}enforcements/violation/details?violationId=${violationId}&operationId=${operationId}`;
        return this._http.get<any>(api);
    }
    update(data) {
        const api = `${environment.baseURL}enforcements/OperationViolationPenalties`;
        return this._http.put<any>(api, data);
    }
    create(data: ViolationPenaliesSave) {
        const api = `${environment.baseURL}enforcements/OperationViolationPenalties`;
        return this._http.post<any>(api, data);
    }
    delete(data: any) {
        const api = `${environment.baseURL}enforcements/OperationViolationPenalties`;
        return this._http.delete<any>(api, data);
    }
    getViolationsList() {
        let api = `${environment.baseURL}enforcements/violation`;
        return this._http.get<any>(api);
    }
    getPenaltiesList() {
        let api = `${environment.baseURL}enforcements/penalties`;
        return this._http.get<any>(api);
    }
}
