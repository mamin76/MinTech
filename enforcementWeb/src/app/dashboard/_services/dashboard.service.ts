
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from '../../../environments/environment';


@Injectable({ providedIn: 'root' })
export class DashboardService {

    constructor(private _http: HttpClient) {
    }

    getOperationList() {
        let api = `${environment.baseURL}operation/operation/all`;
        return this._http.get<any>(api);
    }

    getSectionOne(byMonth: string, operationId: string) {
        let api = `${environment.baseURL}enforcements/citations/dashboard_Sec_One?byMonth=${byMonth}&operationId=${operationId}`
        return this._http.get<any>(api);

    }
    getSectionTwo(operationId: string) {
        let api = `${environment.baseURL}enforcements/citations/dashboard_Sec_Two?operationId=${operationId}`
        return this._http.get<any>(api);
    }

    getSectionThree(months: string) {
        let api = `${environment.baseURL}enforcements/citations/dashboard_Sec_Three?months=${months}`
        return this._http.get<any>(api);
    }
    getSectionThreeBars(year: string) {
        let api = `${environment.baseURL}enforcements/citations/dashboard_Sec_Three_One?year=${year}`
        return this._http.get<any>(api);
    }
}
