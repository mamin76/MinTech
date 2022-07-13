import { Component, ViewChild, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../admin/_services/admin.service';
import { DataService } from '../data.service';
@Component({
  selector: 'app-role-creation',
  templateUrl: './role-creation.component.html',
  styleUrls: ['./role-creation.component.scss'],
})
export class RoleCreationComponent implements OnInit {
  @ViewChild('role') role;
  @ViewChild('actions') actions;
  roleForm: FormGroup;
  groupRoles: any;
  chosenAction: string;
  actionsList: any[] = [];
  chosenRoles: string[] = [];
  alreadyExist: boolean = false;
  displayedColumns: string[] = ['Privileges', 'Privilage'];
  displayMsg: string = '';
  succeeded: boolean = false;

  constructor(
    private router: Router,
    private location: Location,
    private dataService: DataService
  ) {
    this.generateForm();
  }

  ngOnInit(): void {
    this.getActionByGroupName();
  }

  goBack() {
    this.location.back();
  }

  generateForm() {
    this.roleForm = new FormGroup({
      newRole: new FormControl('', [Validators.required]),
    });
  }

  getActionByGroupName() {
    this.dataService.getActionsByGroupName().subscribe((res) => {
      this.groupRoles = res.payload;
    });
  }

  handleActions() {
    this.chosenAction = this.role.selectedOptions.selected[0]?.value;
    for (const key in this.groupRoles) {
      if (key == this.chosenAction) this.chosenRoles = this.groupRoles[key];
    }
  }

  setRoleAction(row) {
    const chosenIndex = this.actionsList.indexOf(row.name);
    row.checked === true
      ? this.actionsList.push({ name: row.name, groupName: this.chosenAction })
      : (this.actionsList = this.actionsList.filter(
          (listItem) => listItem.name !== row.name
        ));
  }

  createRoleActivities() {
    this.dataService
      .createRoleWithAction(
        this.roleForm.controls.newRole.value,
        this.actionsList
      )
      .subscribe(
        (res) => {
          if (res.success === true) {
            this.displayMsg = res.payload;
            this.alreadyExist = false;
            this.succeeded = true;
          }
        },
        (error) => {
          this.succeeded = false;
          this.alreadyExist = true;
        }
      );
  }
}
