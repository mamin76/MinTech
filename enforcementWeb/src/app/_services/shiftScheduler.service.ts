import { Injectable } from '@angular/core';
import { HttpClient, HttpBackend } from '@angular/common/http';

import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ShiftSchedulerService {
  private _http: HttpClient;
  private _token: string = '';
  shiftEmployeesList: any[] = [];

  constructor(handler: HttpBackend) {
    this._http = new HttpClient(handler);
    this._token = `Bearer ${localStorage.getItem('token')}`;
  }

  getRoleShifts(startDate: string, endDate: string) {
    return this._http.get<any>(
      `${environment.baseURL}operation/roles/shifts?startDate=${startDate}&endDate=${endDate}`,
      { headers: { Authorization: `${this._token}` } }
    );
  }

  setShiftEmployeesList(list) {
    this.shiftEmployeesList = list;
  }

  getShiftEmployeesList() {
    return this.shiftEmployeesList;
  }

  getFreeWorkforces(
    startDate,
    endDate,
    startTime,
    endTime,
    roleName
  ): Observable<any> {
    return this._http.get(
      `${environment.baseURL}operation/employee/getFreeWorkForces?startDate=${startDate}&endDate=${endDate}&startTime=${startTime}&endTime=${endTime}&roleName=${roleName}`,
      { headers: { Authorization: this._token } }
    );
  }

  getSupervisors(): Observable<any> {
    return this._http.get(
      `${environment.baseURL}operation/employee/getSupervisors`,
      { headers: { Authorization: this._token } }
    );
  }

  createNewShift(shiftData): Observable<any> {
    const {
      shiftName,
      shiftStartDate,
      shiftEndDate,
      shiftStartTime,
      shiftEndTime,
      shiftRoleName,
      shiftSuperVisorList,
    } = shiftData;

    return this._http.post(
      `${environment.baseURL}operation/shift/`,
      {
        name: shiftName,
        startDate: `${shiftStartDate}`,
        endDate: `${shiftEndDate}`,
        startTime: shiftStartTime,
        endTime: shiftEndTime,
        roleName: shiftRoleName,
        superVisorList: shiftSuperVisorList,
      },
      { headers: { Authorization: this._token } }
    );
  }

  getShiftDetail(request): Observable<any> {
    const { startDate, endDate, startTime, endTime, roleName } = request;
    return this._http.get(
      `${environment.baseURL}operation/shift/getShiftToUpdate?startDate=${startDate}&endDate=${endDate}&startTime=${startTime}&endTime=${endTime}&roleName=${roleName}`,
      { headers: { Authorization: `${this._token}` } }
    );
  }

  deleteShift(shiftId): Observable<any> {
    return this._http.delete(
      `${environment.baseURL}operation/shift/${shiftId}`,
      { headers: { Authorization: `${this._token}` } }
    );
  }

  updateShiftDate(ID, Starting, Ending): Observable<any> {
    return this._http.put(
      `${environment.baseURL}operation/shift/`,
      {
        id: ID,
        startDate: Starting,
        endDate: Ending,
      },
      { headers: { Authorization: `${this._token}` } }
    );
  }

  updateShiftDetails(ID, Starting, ShiftDetails): Observable<any> {
    return this._http.post(
      `${environment.baseURL}operation/shift/updateShiftDetails`,
      {
        shiftId: ID,
        day: Starting,
        workforceShiftDetails: ShiftDetails,
      },
      { headers: { Authorization: `${this._token}` } }
    );
  }
}
