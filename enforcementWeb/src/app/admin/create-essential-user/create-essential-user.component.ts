import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import {
  SearchCountryField,
  CountryISO,
  PhoneNumberFormat,
} from 'ngx-intl-tel-input';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { AdminService } from '../_services/admin.service';

@Component({
  selector: 'app-create-essential-user',
  templateUrl: './create-essential-user.component.html',
  styleUrls: ['./create-essential-user.component.scss'],
})
export class CreateEssentialUserComponent implements OnInit {
  roles: String[] = [];
  operations: String[] = [];
  userEmail: FormControl;
  separateDialCode = false;
  SearchCountryField = SearchCountryField;
  CountryISO = CountryISO;
  PhoneNumberFormat = PhoneNumberFormat;
  preferredCountries: CountryISO[] = [CountryISO.SaudiArabia, CountryISO.Egypt];

  changePreferredCountries() {
    this.preferredCountries = [CountryISO.SaudiArabia, CountryISO.Egypt];
  }

  userForm = new FormGroup({
    fullName: new FormControl('', [Validators.required]),
    userEmail: new FormControl('', [Validators.required, Validators.email]),
    userPhone: new FormControl('', [
      Validators.required,
      // Validators.pattern('[0-9]{4}-[0-9]{3}-[0-9]{4}'),
    ]),
    userOperation: new FormControl('', [Validators.required]),
    userRole: new FormControl('', [Validators.required]),
  });

  constructor(
    private router: Router,
    private location: Location,
    private route: ActivatedRoute,
    private http: AdminService
  ) {
    let id = route.snapshot.paramMap.get('id');
    // console.log(id);
  }

  ngOnInit(): void {
    this.getOperationsList();
    this.getRolesList();
  }

  getErrorMessage() {
    if (this.userForm.controls.userEmail.hasError('required')) {
      return 'You must enter a value';
    }
    return this.userForm.controls.userEmail.hasError('email')
      ? 'Not a valid email'
      : '';
  }

  getPhoneErrorMessage() {
    if (this.userForm.controls.userPhone.hasError('required')) {
      return 'You must enter a value';
    }
    return this.userForm.controls.userPhone.hasError('')
      ? 'Please enter valid phone'
      : '';
  }

  goBack() {
    // this.router.navigateByUrl('/essential-users');
    this.location.back();
  }

  getOperationsList() {
    this.http.getAllOperations().subscribe((res) => {
      this.operations = res.payload;
    });
  }

  getRolesList() {
    this.http.getFixedRoles().subscribe((res) => {
      this.roles = res.payload;
    });
  }

  createNewUser() {
    this.http.assignEssentialRole(this.userForm.value).subscribe(
      (res) => {
        if (res.success === true) {
          // console.log(res.message);
          this.http.setUserCreationStatus(true);
          this.router.navigateByUrl('/essential-users');
        } else {
          this.http.setUserCreationStatus(false);
          this.router.navigateByUrl('/essential-users');
        }
        // this.router.navigateByUrl('/essential-users');
      },
      (error) => {
        this.http.setUserCreationStatus(false);
        this.router.navigateByUrl('/essential-users');
      }
    );
  }
}
