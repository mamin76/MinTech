import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  FormGroup,
  FormControl,
  Validators,
  FormBuilder,
} from '@angular/forms';
import {
  SearchCountryField,
  CountryISO,
  PhoneNumberFormat,
} from 'ngx-intl-tel-input';
import { DataService } from '../data.service';
import { AdminService } from '../admin/_services/admin.service';

@Component({
  selector: 'app-create-workforce-member',
  templateUrl: './create-workforce-member.component.html',
  styleUrls: ['./create-workforce-member.component.scss'],
})
export class CreateWorkforceMemberComponent implements OnInit {
  roles: string[] = [];
  Member: FormGroup;
  fileName = '';
  separateDialCode = false;
  SearchCountryField = SearchCountryField;
  CountryISO = CountryISO;
  PhoneNumberFormat = PhoneNumberFormat;
  preferredCountries: CountryISO[] = [CountryISO.SaudiArabia, CountryISO.Egypt];
  created: boolean = false;
  failed: boolean = false;
  createdMsg: string = '';

  changePreferredCountries() {
    this.preferredCountries = [CountryISO.SaudiArabia, CountryISO.Egypt];
  }

  constructor(
    private router: Router,
    private location: Location,
    private fb: FormBuilder,
    private http: DataService,
    private adminService: AdminService
  ) {
    this.generateWorkforceForm();
  }

  ngOnInit(): void {
    this.getMemberRoles();
  }

  goBack() {
    this.location.back();
  }

  getMemberRoles() {
    this.adminService.getRoles().subscribe((res) => (this.roles = res.payload));
  }

  generateWorkforceForm() {
    this.Member = this.fb.group({
      fullName: ['', Validators.required],
      memberEmail: ['', [Validators.required, Validators.email]],
      memberPhone: ['', Validators.required],
      memberRole: ['', Validators.required],
      memberImage: [''],
    });
  }

  onFileSelected(event) {
    if (event.target.files.length > 0) {
      const file: File = event.target.files[0];

      if (file) {
        this.fileName = file.name;
        const formData = new FormData();
        formData.append('thumbnail', file);

        this.Member.controls.memberImage.setValue(file);
      }
    }
  }

  getErrorMessage() {
    if (this.Member.controls.memberEmail.hasError('required')) {
      return 'You must enter a value';
    }
    return this.Member.controls.memberEmail.hasError('email')
      ? 'Not a valid email'
      : '';
  }

  getPhoneErrorMessage() {
    if (this.Member.controls.memberPhone.hasError('required')) {
      return 'You must enter a value';
    }
    return this.Member.controls.memberPhone.hasError('')
      ? 'Please enter valid phone'
      : '';
  }

  createMember() {
    console.log(this.Member.value);
    this.http.createWorkforce(this.Member.value).subscribe(
      (res) => {
        if (res.success === true) {
          this.createdMsg = res.payload;
          this.failed = false;
          this.created = true;
        }
      },
      (error) => {
        this.createdMsg = error.error.error.message;
        this.created = false;
        this.failed = true;
        // console.log(this.failed);
        // console.log(error.error.error.message);
      }
    );
  }
}
