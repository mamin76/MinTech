import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { ShiftSchedulerService } from '../_services';

@Component({
  selector: 'app-assign-shift-members',
  templateUrl: './assign-shift-members.component.html',
  styleUrls: ['./assign-shift-members.component.scss'],
})
export class AssignShiftMembersComponent implements OnInit {
  @Input('shiftData') shiftData;
  @Input('freeWorkforcesList') freeWorkforcesList: any[] = [];
  @Input('supervisorsList') supervisorsList: any[];
  createdMsg: boolean = false;
  created: boolean = false;
  failed: boolean = false;
  activeSupervisor: boolean[] = [];
  formData: FormData;
  shiftWorkforceList: object[] = [];
  assignedShift: any;

  constructor(
    private shiftService: ShiftSchedulerService,
    private router: Router,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.freeWorkforcesList.forEach((element) => {
      this.activeSupervisor.push(true);
    });
  }

  goBack() {
    this.location.back();
  }

  choseWorker(e, i) {
    this.activeSupervisor[i] = !e.target.checked;
    if (this.activeSupervisor[i] == true) {
      const index = this.shiftWorkforceList.findIndex(
        (a: any) => a.employeeId == e.target.value
      );
      this.shiftWorkforceList.splice(index, 1);
    }
  }

  chosenSupervisor(e, employeeId, i) {
    let supervisorId = e.value;

    if (this.activeSupervisor[i] === false) {
      this.shiftWorkforceList.push({
        supervisorId: supervisorId,
        employeeId: employeeId,
      });
    }
  }

  assignShift() {
    this.assignedShift = {
      ...this.shiftData,
      shiftSuperVisorList: this.shiftWorkforceList,
    };
    this.shiftService.createNewShift(this.assignedShift).subscribe((res) => {
      if (res.success === true) {
        this.location.back();
      }
    });
  }
}
