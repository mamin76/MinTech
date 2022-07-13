import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ConfirmPasswordValidator } from '../_util/confirm-password.validator';

@Component({
  selector: 'app-new-password',
  templateUrl: './new-password.component.html',
  styleUrls: ['./new-password.component.scss'],
})
export class NewPasswordComponent implements OnInit {
  newPasswordForm: FormGroup;
  hidePassword: boolean = true;
  hideConfirmPassword: boolean = true;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initConfirmPassword();
  }

  initConfirmPassword() {
    this.newPasswordForm = this.fb.group(
      {
        password: ['', Validators.required],
        confirmPassword: new FormControl('', [Validators.required]),
      },
      { validator: ConfirmPasswordValidator('password', 'confirmPassword') }
    );
  }

  confirmPass() {
    // console.log(this.newPasswordForm.value);
  }
}
