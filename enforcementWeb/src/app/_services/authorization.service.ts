import { Injectable } from '@angular/core';
// import { BehaviorSubject, Observable } from 'rxjs';
// import { AuthenticationService } from './authentication.service';
import { LoginServiceService } from '../login/_services/login-service.service';
@Injectable({
  providedIn: 'root'
})

export class AuthorizationService {
  constructor(private _authenticationService: LoginServiceService) { }

  getCurrentUser() {
    return this._authenticationService.currentUserValue;
  }

  getCurrentRoles() {
    const currentUser = this._authenticationService.currentUserValue;
    const roles = currentUser['employeeDto']['role']['actions'];
    return (roles.length) ? roles : false;
  }

  check(groupName: string, name: string) {
    const roles = this.getCurrentRoles();
    const filterd = roles.filter((item) => item.name == name && item.groupName == groupName);
    return (filterd.length) ? true : false;
  }
  checkGroupName(groupName: string) {
    const roles = this.getCurrentRoles();
    const filterd = roles.filter((item) => item.groupName == groupName);
    console.log("AuthorizationService currentUser check filterd =================", filterd);
    return (filterd.length) ? true : false;
  }

  getRoleName() {
    const currentUser = this._authenticationService.currentUserValue;
    const roleName = currentUser['employeeDto']['role']['name'];
    return (roleName.length) ? roleName : false;
  }
}
