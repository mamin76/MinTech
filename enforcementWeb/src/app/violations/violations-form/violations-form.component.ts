import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import { first } from 'rxjs/operators';
import { FormBuilder, FormControl, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { ViolationsService } from '../_services';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmation } from '../../_shared/dialog-confirmation/dialog-confirmation';

@Component({
  selector: 'app-violations-form',
  templateUrl: './violations-form.component.html',
  styleUrls: ['./violations-form.component.scss']
})
export class ViolationsFormComponent implements OnInit {
  title: string = "Define Violation";
  loading = false;
  data: any = {};
  id;
  type;

  submitted = false;
  form: FormGroup;
  get f() { return this.form.controls; }

  constructor(
    private _router: Router,
    private _activatedRoute: ActivatedRoute,
    private _formBuilder: FormBuilder,
    private _violationsService: ViolationsService,
    private _dialog: MatDialog,

  ) {
    this.id = this._activatedRoute.snapshot.params.id;
    this.type = this._activatedRoute.snapshot.params.type;

    if (this.id) {
      this._loadDate(this.id);
    }
  }

  ngOnInit(): void {
    this.form = this._formBuilder.group({
      id: [],
      enName: [
        { value: '', disabled: (this.type == 'view') ? true : false },
        [
          Validators.required,
          (this.type !== 'edit') ? uniqueEnNameValidator.bind(this) : Validators.nullValidator
        ]
      ],
      arName: [
        { value: '', disabled: (this.type == 'view') ? true : false },
        [
          Validators.required,
          (this.type !== 'edit') ? uniqueArNameValidator.bind(this) : Validators.nullValidator,
          (this.type !== 'edit') ? Validators.pattern('[\u0600-\u06FF ]*') : Validators.nullValidator
        ]
      ],
      enDescription: [{ value: '', disabled: (this.type == 'view') ? true : false }, []],
      arDescription: [{ value: '', disabled: (this.type == 'view') ? true : false }, [
        Validators.required,
        (this.type !== 'edit') ? Validators.pattern('[\u0600-\u06FF ]*') : Validators.nullValidator
      ]],


    });
    if (this.id === undefined) {
      this.title = 'Define Violation'
    } else {
      this.title = (this.type == "edit") ? `Edit Violation` : `View Violation`;
    }

  }

  onBack(): void {
    this._router.navigateByUrl(`/enforcement-violations`);
  }

  onSubmit() {

    this.submitted = true;
    // stop here if form is invalid
    if (this.form.invalid) return;

    this.loading = true;

    const data = {
      enName: this.f.enName.value,
      arName: this.f.arName.value,
      enDescription: this.f.enDescription.value,
      arDescription: this.f.arDescription.value,
    };
    if (this.id === undefined) {
      this._violationsService.create(data).pipe(first())
        .subscribe(
          data => {
            console.log("create _violationsService data =========", data);
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
            console.log("create _violationsService err ===== ", error);
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
      this._violationsService.update(this.id, data).pipe(first())
        .subscribe(
          data => {
            console.log("update _violationsService data =========", data);
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
            console.log("create _violationsService err ===== ", error);
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

  public myErrorWithoutSubmit = (controlName: string, errorName: string) => {
    return this.form.controls[controlName].hasError(errorName);
  }

  private _loadDate(id) {
    this._violationsService.getDetails(id)
      .pipe(first())
      .subscribe(
        resp => {
          this.data = resp.payload;
          this.f['id'].setValue(this.data.id);
          this.f['enName'].setValue(this.data.enName);
          this.f['arName'].setValue(this.data.arName);
          this.f['enDescription'].setValue(this.data.enDescription);
          this.f['arDescription'].setValue(this.data.arDescription);

        },
        error => {
          console.log(" err ===== ", error);
        });
  }
}

export function uniqueEnNameValidator(control: AbstractControl) {
  this._violationsService.checkUniqueName(
    control.value, 'en'
  ).pipe(first())
    .subscribe(
      resp => {
        console.log('uniqueEnNameValidator data ', resp);
        if (resp.payload)
          control.setErrors({ unavailable: true });
      },
      error => {
        // control.setErrors({ unavailable: true });
      });
}

export function uniqueArNameValidator(control: AbstractControl) {
  this._violationsService.checkUniqueName(
    control.value, 'ar'
  ).pipe(first())
    .subscribe(
      resp => {
        console.log('uniqueEnNameValidator data ', resp);
        if (resp.payload)
          control.setErrors({ unavailable: true });
      },
      error => {
        // control.setErrors({ unavailable: true });
      });
}