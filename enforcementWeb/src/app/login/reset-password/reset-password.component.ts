import { Component, OnInit } from '@angular/core';
import { LoginServiceService } from '../_services/login-service.service';
import { ErrorStateMatcher } from '@angular/material/core';
import {
  FormControl,
  FormGroup,
  Validators,
  FormGroupDirective,
  NgForm,
  FormBuilder,
} from '@angular/forms';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(
    control: FormControl | null,
    form: FormGroupDirective | NgForm | null
  ): boolean {
    const isSubmitted = form && form.submitted;
    return !!(
      control &&
      control.invalid &&
      (control.dirty || control.touched || isSubmitted)
    );
  }
}

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss'],
})
export class ResetPasswordComponent implements OnInit {
  confirmMailForm: FormGroup;
  resetPassSuccess: boolean = false;
  matcher = new MyErrorStateMatcher();

  constructor(
    private fb: FormBuilder,
    private loginService: LoginServiceService
  ) {
    this.generateMailForm();
  }

  ngOnInit(): void {}

  // generate form for mail to reset password.
  generateMailForm() {
    this.confirmMailForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
    });
  }

  // send mail to service to verify and send confirm password to user email.
  submitMail() {
    this.loginService
      .confirmEmail(this.confirmMailForm.controls.email.value)
      .subscribe((res) => {
        if (res.success === 1) {
          this.resetPassSuccess = true;
          return res.message;
        } else alert('please try again later');
      });
  }
}
