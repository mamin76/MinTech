import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import { first } from 'rxjs/operators';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ViolationPenaltiesService } from '../_services';
import { AuthorizationService } from 'src/app/_services';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmation } from '../../_shared/dialog-confirmation/dialog-confirmation';

@Component({
  selector: 'app-manage-violations-penalties-form',
  templateUrl: './manage-violations-penalties-form.component.html',
  styleUrls: ['./manage-violations-penalties-form.component.scss']
})
export class ManageViolationsPenaltiesFormComponent implements OnInit {
  title: string = "Create Violation Penalty";
  loading = false;
  violationsDataList: any = [];
  penaltiesDataList: any = [];
  id;
  type;

  submitted = false;
  form: FormGroup;
  get f() { return this.form.controls; }
  formData;
  typesOfShoes: string[] = ['Boots', 'Clogs', 'Loafers', 'Moccasins', 'Sneakers'];

  selectedViolation: any = {
    id: 0,
    enName: 'Select Violation'
  };

  selectedPenalties: any = [{
    id: 0,
    enName: 'Select penalty',
    isExpanded: true
  }];
  operationId: number;
  constructor(
    private _router: Router,
    private _activatedRoute: ActivatedRoute,
    private _formBuilder: FormBuilder,
    private _violationPenaltiesService: ViolationPenaltiesService,
    public authorizationService: AuthorizationService,
    private _dialog: MatDialog,

  ) {
    this.id = this._activatedRoute.snapshot.params.id;
    this.type = this._activatedRoute.snapshot.params.type;

    if (localStorage.getItem('currentUser')) {
      const currentUserJson = JSON.parse(localStorage.getItem('currentUser'));
      this.operationId = currentUserJson.employeeDto.operationId;
    }

    if (this.id) {
      this._loadFormData(this.id, this.operationId);
    }

    this._loadViolationsData();
    this._loadPenaltiesData();
  }

  private _loadFormData(id, operationId) {
    this._violationPenaltiesService.getDetails(id, operationId)
      .pipe(first())
      .subscribe(
        resp => {
          this.formData = resp.payload;
          console.log('this.formData ======== ', this.formData);
          this.selectedViolation = {
            id: this.formData.id,
            enName: this.formData.enName
          };

          this.selectedPenalties = this.formData.penaltyList;
        },
        error => {
          console.log(" err ===== ", error);
        });
  }

  ngOnInit(): void {
    const disabled: boolean = (this.type == 'view') ? true : false
    this.form = this._formBuilder.group({

    });
    if (this.id === undefined) {
      this.title = 'Define Violation Penalty'
    } else {
      this.title = (this.type == "edit") ? `Edit Violation Penalty` : `View Violation Penalty`;
    }
  }

  onBackViolations(): void {
    this._router.navigateByUrl(`/enforcement-violations`);
  }

  onBack(): void {
    this._router.navigateByUrl(`/violation-penalties-list`);
  }

  onSubmit() {

    this.submitted = true;
    // stop here if form is invalid

    this.loading = true;

    let data = {
      "operationId": this.operationId,
      "violationId": this.selectedViolation.id
    };
    const arrayUniqueByKey = [...new Map(this.selectedPenalties.map(
      item => [item['id'], item['id']])).values()];

    data['penaltiesIds'] = arrayUniqueByKey;

    if (this.id === undefined) {
      this._violationPenaltiesService.create(data).pipe(first())
        .subscribe(
          data => {
            this.loading = false;
            const dialogRef = this._dialog.open(DialogConfirmation, {
              panelClass: 'custom-mat-show-image-dialog-container',
              width: '468px',
              data: {
                title: { title: "Success", color: "#481A90" },
                body: data.payload,
                btnConfirmation: { title: "Ok", color: "#481A90" },
                btnClose: { hide: true, title: "close" },
                comment: false
              }
            });
            dialogRef.afterClosed().subscribe(result => {
              if (result)
                this.onBack();
            });
          },
          error => {
            console.log("create _violationPenaltiesService err ===== ", error.error.error.message);
            this.loading = false;
            const dialogRef = this._dialog.open(DialogConfirmation, {
              panelClass: 'custom-mat-show-image-dialog-container',
              width: '468px',
              data: {
                title: { title: "Attention", color: "#D93636" },
                body: error.error.error.message,
                btnConfirmation: { hide: true, title: "Confirm", color: "#D93636" },
                btnClose: { title: "close" },
                comment: false
              }
            });

            dialogRef.afterClosed().subscribe(result => {
            });

          });
    } else {
      this._violationPenaltiesService.update(data).pipe(first())
        .subscribe(
          data => {
            console.log("update _violationPenaltiesService data =========", data);
            this.loading = false;
            const dialogRef = this._dialog.open(DialogConfirmation, {
              panelClass: 'custom-mat-show-image-dialog-container',
              width: '468px',
              data: {
                title: { title: "Success", color: "#481A90" },
                body: data.payload,
                btnConfirmation: { title: "Ok", color: "#481A90" },
                btnClose: { hide: true, title: "close" },
                comment: false
              }
            });
            dialogRef.afterClosed().subscribe(result => {
              if (result)
                this.onBack();
            });
          },
          error => {
            console.log("update _violationPenaltiesService err ===== ", error);
            this.loading = false;
            const dialogRef = this._dialog.open(DialogConfirmation, {
              panelClass: 'custom-mat-show-image-dialog-container',
              width: '468px',
              data: {
                title: { title: "Attention", color: "#D93636" },
                body: error.error.error.message,
                btnConfirmation: { hide: true, title: "Confirm", color: "#D93636" },
                btnClose: { title: "close" },
                comment: false
              }
            });

            dialogRef.afterClosed().subscribe(result => {
            });
          });
    }
  }

  public myError = (controlName: string, errorName: string) => {
    return (this.submitted) ? this.form.controls[controlName].hasError(errorName) : false;
  }

  private _loadViolationsData() {
    this._violationPenaltiesService.getViolationsList()
      .pipe(first())
      .subscribe(
        resp => {
          this.violationsDataList = resp.payload;

        },
        error => {
          console.log(" err ===== ", error);
        });
  }

  private _loadPenaltiesData() {
    this._violationPenaltiesService.getPenaltiesList()
      .pipe(first())
      .subscribe(
        resp => {
          this.penaltiesDataList = resp.payload;

        },
        error => {
          console.log(" err ===== ", error);
        });
  }

  onSelectionChange($event) {
    console.log('onSelectionChange $event == ', $event);
  }

  applyFilterRight(e: Event) {
    const filterValue = (e.target as HTMLInputElement).value;
    if (filterValue.length >= 3) {
    }
  }

  onViolationSelect($event) {
    // console.log('onViolationSelect $event === ', $event.option._value);
    this.selectedViolation = $event.option._value;
  }

  onPenaltiesSelect($event, idx) {
    // console.log('onPenaltiesSelect $event === ', 'idx= ', idx, ' , ', $event.option._value);
    this.selectedPenalties[idx] = $event.option._value;
    this.selectedPenalties[idx].isExpanded = false;
    console.log('this.selectedPenalties', this.selectedPenalties);
  }

  addPenalty() {
    this.selectedPenalties.push({
      id: 0,
      enName: 'Select penalty',
      isExpanded: true
    });
    console.log('addPenalty this.selectedPenalties', this.selectedPenalties);
  }
  removePenalty(idx) {
    this.selectedPenalties.splice(idx, 1);
  }
}