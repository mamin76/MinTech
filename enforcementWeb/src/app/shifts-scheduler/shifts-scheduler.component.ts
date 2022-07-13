import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { first } from 'rxjs/operators';
import { ShiftSchedulerService } from '../_services';

@Component({
  selector: 'app-shifts-scheduler',
  templateUrl: './shifts-scheduler.component.html',
  styleUrls: ['./shifts-scheduler.component.scss'],
})
export class ShiftsSchedulerComponent implements OnInit {
  searchKey: '';

  roleShiftsData: any;
  selectedDateRange: any[] = [];
  filterStartDate: string = '';
  filterEndDate: string = '';

  roles: any[];

  shifts: any[] = [
    {
      title: '#HU87IOF',
      name: 'Shift 1 - dummy shift name',
      membersCount: 40,
      time: '10:00 AM to 05:00PM',
    },
    {
      title: '#HU87IOF',
      name: 'Shift 2 - dummy shift name',
      membersCount: 25,
      time: '10:00 AM to 05:00PM',
    },
    {
      title: '#HU87IOF',
      name: 'Shift 3 - dummy shift name',
      membersCount: 30,
      time: '10:00 AM to 05:00PM',
    },
    {
      title: '#HU87IOF',
      name: 'Shift 4 - dummy shift name',
      membersCount: 10,
      time: '10:00 AM to 05:00PM',
    },
  ];

  constructor(
    private location: Location,
    private router: Router,
    private _shiftSchedulerService: ShiftSchedulerService
  ) {
    this._setFilterDate(null, null);
  }

  ngOnInit(): void {

    this._loadRoleShiftsData(this.filterStartDate, this.filterEndDate);

  }

  createShift() {
    this.router.navigateByUrl('/create-shift');
  }

  applyFilter() { }
  onSearchClear() {
    this.searchKey = '';
  }

  private _loadRoleShiftsData(start: string, end: string) {
    this._shiftSchedulerService.getRoleShifts(start, end)
      .pipe(first())
      .subscribe(
        resp => {
          this.roleShiftsData = resp.payload;
          console.log('_shiftSchedulerService.getRoleShifts() resp', this.roleShiftsData);
          this.roles = Object.keys(this.roleShiftsData.content).map((key) => {
            return { title: key, roles: this.roleShiftsData.content[key] }
          });
          console.log("this roles", this.roles)
        },
        error => {
          console.log(" err ===== ", error);
        });
  }

  onSelectedDateRange() {
    if (this.selectedDateRange) {
      const start = this._dateFormat(this.selectedDateRange[0]);
      const end = this._dateFormat(this.selectedDateRange[1]);
      if ((start != this.filterStartDate) || (end != this.filterEndDate)) {
        this._setFilterDate(start, end);
        this._loadRoleShiftsData(this.filterStartDate, this.filterEndDate);
      }
    }
  }

  private _dateFormat(d) {
    const day = (d.getDate() < 10) ? `0${d.getDate()}` : d.getDate();
    const month = ((d.getMonth() + 1) < 10) ? `0${d.getMonth() + 1}` : d.getMonth() + 1;
    const year = d.getFullYear();
    return `${year}-${month}-${day}`;
  }

  private _setFilterDate(start: string, end: string) {
    if (start && end) {
      this.filterStartDate = start;
      this.filterEndDate = end;
    } else {
      this.filterStartDate = '2021-12-17';
      this.filterEndDate = '2022-01-30';

      this.selectedDateRange[0] = new Date(2021, 11, 17);
      this.selectedDateRange[1] = new Date(2022, 0, 30);

    }
  }
}
