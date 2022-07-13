import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment as env } from '../../../environments/environment';
import { User } from '../_models/user/user.modle';

@Injectable({
  providedIn: 'root',
})
export class LoginServiceService {
  private _isLoggedIn$ = new BehaviorSubject<boolean>(false);
  isLoggedIn$ = this._isLoggedIn$.asObservable();

  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private http: HttpClient) {
    const token = localStorage.getItem('token');
    this._isLoggedIn$.next(!!token);
    let currentUserJson = {};
    if (localStorage.getItem('currentUser')) {
      currentUserJson = JSON.parse(localStorage.getItem('currentUser'))
    }
    this.currentUserSubject = new BehaviorSubject<User>(currentUserJson);

    this.currentUser = this.currentUserSubject.asObservable();


  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  getUser(token: string): User {
    return JSON.parse(atob(token.split('.')[1])) as User;
  }
  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  private requestHeader = {
    'content-Type': '',
    authentication: '',
  };
  // {
  //           headers: {
  //             'content-type': 'application/json',
  //             'Access-Control-Allow-Origin': '*',
  //           },
  //         }
  login(data): Observable<any> {
    const { userName, password } = data;
    return this.http
      .post(`${env.baseURL}operation/employee/login`, {
        username: userName,
        password: password,
      })
      .pipe(
        tap((response: any) => {
          localStorage.setItem('token', response.payload.token);
          this._isLoggedIn$.next(true);
          this.getUser(response.payload.token);

          console.log("auth ser login user response.payload ============ ", response.payload);

          localStorage.setItem('currentUser', JSON.stringify(response.payload));
          this.currentUserSubject.next(response.payload);
        })
      );
  }

  confirmEmail(mail): Observable<any> {
    return this.http.post(
      `${env.baseURL}operation/employee/forgetPassword`,
      { email: mail },
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      }
    );
  }

  resetPassword(data): Observable<any> {
    const { password } = data;
    //
    return new Observable((res) => {
      msg: 'we dont have a mail to send only pass and confirmPass';
    });
    // return this.http.post(
    //   `${env.baseURL}operation/employee/resetPassword`,
    //   {
    //     email: 'mahmoudatef925@gmail.com',
    //     password: password,
    //   },
    // );
  }
}
