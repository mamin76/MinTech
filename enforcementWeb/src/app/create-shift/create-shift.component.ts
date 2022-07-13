import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { AdminService } from '../admin/_services/admin.service';
import { Subject } from 'rxjs';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DataService } from '../data.service';
import { ShiftSchedulerService } from '../_services';

@Component({
  selector: 'app-create-shift',
  templateUrl: './create-shift.component.html',
  styleUrls: ['./create-shift.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CreateShiftComponent implements OnInit {
  private _destroyed = new Subject<void>();
  roles: String[] = [];
  operations: String[] = [];
  freeWorkforcesList: any[] = [];
  supervisorsList: any[] = [];
  next: boolean = false;
  shiftData: {
    shiftName: string;
    shiftStartDate: string;
    shiftEndDate: string;
    shiftStartTime: string;
    shiftEndTime: string;
    shiftRoleName: string;
  };

  constructor(
    private router: Router,
    private location: Location,
    private route: ActivatedRoute,
    private http: DataService,
    private shiftService: ShiftSchedulerService
  ) {}

  ngOnInit(): void {
    this.getOperationsList();
    this.getRolesList();
    this.getSupervisorsList();
  }

  // other time validation for 12 hours ^(0?[1-9]|1[0-2]):[0-5][0-9]$
  createShiftForm = new FormGroup({
    shiftName: new FormControl('', [Validators.required]),
    userRole: new FormControl('', [Validators.required]),
    startDate: new FormControl('', [Validators.required]),
    endDate: new FormControl('', [Validators.required]),
    startTime: new FormControl('', [
      Validators.required,
      Validators.pattern('([01]?[0-9]|2[0-3]):[0-5][0-9]'),
    ]),
    endTime: new FormControl('', [
      Validators.required,
      Validators.pattern('([01]?[0-9]|2[0-3]):[0-5][0-9]'),
    ]),
  });

  checkTimeValidation(start, end) {
    return start.value.split(':')[0] >= end.value.split(':')[0];
  }

  getErrorMessage() {
    if (this.createShiftForm.controls.userEmail.hasError('required')) {
      return 'You must enter a value';
    }
    return this.createShiftForm.controls.userEmail.hasError('email')
      ? 'Not a valid email'
      : '';
  }

  getPhoneErrorMessage() {
    if (this.createShiftForm.controls.userPhone.hasError('required')) {
      return 'You must enter a value';
    }
    return this.createShiftForm.controls.userPhone.hasError('')
      ? 'Please enter valid phone'
      : '';
  }

  goBack() {
    this.location.back();
  }

  getOperationsList() {
    this.http.getAllOperations().subscribe((res) => {
      this.operations = res.payload;
    });
  }

  getRolesList() {
    this.http.getRoles().subscribe((res) => {
      this.roles = res.payload;
    });
  }

  nextForm() {
    this.next = true;
  }

  createNewShift() {
    const newStartDate = new Date(this.createShiftForm.controls.startDate.value)
      .toISOString()
      .split('T')[0];
    const newEndDate = new Date(this.createShiftForm.controls.endDate.value)
      .toISOString()
      .split('T')[0];
    // console.log(newStartDate, newEndDate);

    this.shiftData = {
      shiftName: this.createShiftForm.controls.shiftName.value,
      shiftStartDate: newStartDate,
      shiftEndDate: newEndDate,
      shiftStartTime: this.createShiftForm.controls.startTime.value,
      shiftEndTime: this.createShiftForm.controls.endTime.value,
      shiftRoleName: this.createShiftForm.controls.userRole.value,
    };
    this.getWorkforcersList(this.shiftData);
  }

  getWorkforcersList(shiftData) {
    const startDate = shiftData.shiftStartDate;
    const endDate = shiftData.shiftEndDate;
    const startTime = shiftData.shiftStartTime;
    const endTime = shiftData.shiftEndTime;
    const roleName = shiftData.shiftRoleName;

    this.shiftService
      .getFreeWorkforces(startDate, endDate, startTime, endTime, roleName)
      .subscribe((res) => {
        this.freeWorkforcesList = res.payload;
        this.nextForm();
      });
  }

  getSupervisorsList() {
    this.shiftService.getSupervisors().subscribe((res) => {
      this.supervisorsList = res.payload;
    });
  }
}
