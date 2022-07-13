import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment as env } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  creationSuccessStatus: boolean = false;
  creationFailStatus: boolean = false;
  constructor(private http: HttpClient) { }
  _token = `Bearer ${localStorage.getItem('token')}`;

  getFixedRoles(): Observable<any> {
    return this.http.get(`${env.baseURL}operation/roles/fixed_roles`, {
      headers: { Authorization: this._token },
    });
  }

  getRoles(): Observable<any> {
    return this.http.get(`${env.baseURL}operation/roles/roles-names`, {
      headers: { Authorization: this._token },
    });
  }

  getAllOperations(): Observable<any> {
    return this.http.get(`${env.baseURL}operation/operation/all`, {
      headers: { Authorization: this._token },
    });
  }

  assignEssentialRole(user): Observable<any> {
    const { fullName, userEmail, userPhone, userOperation, userRole } = user;
    let formData: any = new FormData();
    formData.append('name', fullName);
    formData.append('email', userEmail);
    formData.append('phone', userPhone.number);
    formData.append('operationName', userOperation);
    formData.append('roleName', userRole);
    // formData.append('image', null);
    // {
    return this.http.post(`${env.baseURL}operation/employee`, formData, {
      headers: { Authorization: this._token },
    });
  }

  setUserCreationStatus(status) {
    if (status === true) {
      this.creationFailStatus = false;
      this.creationSuccessStatus = true;
    } else {
      this.creationSuccessStatus = false;
      this.creationFailStatus = true;
    }
  }

  getUserCreationSuccess() {
    return this.creationSuccessStatus;
  }
  getUserCreationFailed() {
    return this.creationFailStatus;
  }

  getEssentialUsers(
    employeeName: string,
    pageCount: number,
    limitCount: number
  ): Observable<any> {
    return this.http.get(
      `${env.baseURL}operation/employee/search?${employeeName !== undefined
        ? `keyword=${employeeName !== null && employeeName !== undefined
          ? `${employeeName}&`
          : ''
        }`
        : ''
      }page=${pageCount !== null ? pageCount : 0}&limit=${limitCount !== null ? limitCount : 10
      }`,
      { headers: { Authorization: this._token } }
    );
  }

  getAvailableWorkforceList(operationName, roleName, today): Observable<any> {
    return this.http.get(
      `${env.baseURL}operation/employee/freeWithOpRole?roleName=${roleName}&operationName=${operationName}&&today=${today}`,
      //today=2021-12-27
      { headers: { Authorization: this._token } }
    );
  }

  reassignWorkforcers(newOperation, employeesNames): Observable<any> {
    return this.http.put(
      `${env.baseURL}operation/employee/reassignOperation`,
      {
        newOperationName: newOperation,
        employeesUserNames: employeesNames,
      },
      { headers: { Authorization: this._token } }
    );
  }

  getEmployeesList(
    pageCount: number,
    limitCount: number,
    employeeName: string
  ): Observable<any> {
    return this.http.get(
      `${env.baseURL}operation/employee?page=${pageCount !== null ? pageCount : 0
      }&limit=${limitCount !== null ? limitCount : 10}&employeeName=${employeeName !== null ? employeeName : ''
      }`,
      { headers: { Authorization: this._token } }
    );
  }

  updateEmployee(): Observable<any> {
    return this.http.put(
      `${env.baseURL}operation/employee/updateEmployeeByHead`,
      { headers: { Authorization: this._token } }
    );
  }
  getImage(id: number) {
    const api = `${env.baseURL}operation/employee/imagesbase64/${id}`;
    return this.http.get<any>(api);
  }
  // this.http.put(`${env.baseURL}operation/employee/freeWithOpRole`)
}
