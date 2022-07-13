import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { LoginServiceService } from '../_services/login-service.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss'],
})
export class LoginPageComponent implements OnInit {
  hide: boolean = true;
  loginForm: FormGroup;
  errorActive: boolean = false;
  errMsg: string = '';
  constructor(
    private router: Router,
    private authService: LoginServiceService
  ) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.loginForm = new FormGroup({
      userName: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    });
  }

  login() {
    if (this.loginForm.valid)
      this.authService.login(this.loginForm.value).subscribe(
        (res) => this.router.navigate(['/add-workforce']),
        (error) => {
          error.error.code == 401
            ? (this.errMsg = 'Incorrect Username or Password')
            : (this.errMsg = error.error.error.message);
          this.errorActive = true;
        }
      );
    // res.success ? console.log(res) : alert('sth went wrong');
  }

  resetPassword() {
    this.router.navigateByUrl('/reset-password');
  }
}
