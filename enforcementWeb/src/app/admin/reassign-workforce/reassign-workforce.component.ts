import { AfterViewInit, Component, ViewChild, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { AdminService } from '../_services/admin.service';

@Component({
  selector: 'app-reassign-workforce',
  templateUrl: './reassign-workforce.component.html',
  styleUrls: ['./reassign-workforce.component.scss'],
})
export class ReassignWorkforceComponent implements OnInit, AfterViewInit {
  workforceForm: FormGroup;
  workForcers: string[] = [];
  operations: string[] = [];
  roles: string[] = [];
  freeWorkforcers: string[] = [];
  displayedColumns: string[] = ['item', 'cost'];
  success: boolean = false;
  successMsg: any = '';
  err: boolean = false;
  errMsg: string = '';
  // dataSource = new MatTableDataSource<any>(this.freeWorkforcers);
  // @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private router: Router,
    private location: Location,
    private adminService: AdminService
  ) {
    this.generateForm();
  }

  ngOnInit(): void {
    this.getRolesList();
    this.getOperationsList();
  }

  ngAfterViewInit() {
    // this.dataSource.paginator = this.paginator;
  }

  generateForm() {
    this.workforceForm = new FormGroup({
      workforceCurrentOperation: new FormControl(''),
      workforceRole: new FormControl(''),
      workforceNewOperation: new FormControl(''),
    });
  }

  goBack() {
    this.location.back();
  }

  getOperationsList() {
    this.adminService.getAllOperations().subscribe((res) => {
      this.operations = res.payload;
    });
  }

  getRolesList() {
    this.adminService.getRoles().subscribe((res) => {
      this.roles = res.payload;
    });
  }

  getWorkforceList() {
    const today = new Date().toISOString().split('T')[0];
    this.adminService
      .getAvailableWorkforceList(
        this.workforceForm.controls.workforceCurrentOperation.value,
        this.workforceForm.controls.workforceRole.value,
        today
      )
      .subscribe((res) => {
        this.freeWorkforcers = res.payload;
      });
  }

  setWorkforce(row) {
    const chosenIndex = this.workForcers.indexOf(row.name);
    row.checked === true
      ? this.workForcers.push(row.name)
      : this.workForcers.splice(chosenIndex, 1);
  }

  assignWorkforcers() {
    if (this.workForcers.length == 0) {
      this.errMsg = `Please Choose Workers`;
      this.success = false;
      this.err = true;
    } else {
      this.adminService
        .reassignWorkforcers(
          this.workforceForm.controls.workforceNewOperation.value,
          this.workForcers
        )
        .subscribe((res) => {
          this.successMsg = res.payload;
          this.err = false;
          this.success = true;
        });
    }
  }
}
