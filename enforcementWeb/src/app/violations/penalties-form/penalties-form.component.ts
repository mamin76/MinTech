import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import { first } from 'rxjs/operators';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { PenaltiesService } from '../_services';

import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmation } from '../../_shared/dialog-confirmation/dialog-confirmation';

interface SelectDropdownIdName {
  id: string;
  name: string;
}

@Component({
  selector: 'app-penalties-form',
  templateUrl: './penalties-form.component.html',
  styleUrls: ['./penalties-form.component.scss']
})
export class PenaltiesFormComponent implements OnInit {
  title: string = "Define Penalty";
  loading = false;
  data: any = {};
  saveData: any = {};
  id;
  type;

  submitted = false;
  form: FormGroup;
  get f() { return this.form.controls; }

  methods: SelectDropdownIdName[] = [
    { id: 'MONETARY;ONE_PRICE', name: 'Montarey One Time'},
    { id: 'MONETARY;COUNTED', name: 'Montarey Counted' },
    { id: 'EMAIL', name: 'Email'},
  ];

  constructor(
    private _router: Router,
    private _activatedRoute: ActivatedRoute,
    private _formBuilder: FormBuilder,
    private _penaltiesService: PenaltiesService,
    private _dialog: MatDialog,

  ) {
    this.id = this._activatedRoute.snapshot.params.id;
    this.type = this._activatedRoute.snapshot.params.type;

    if (this.id)
      this._loadFormData(this.id);
  }

  ngOnInit(): void {

    if (this.id === undefined) {
      this.title = 'Define Penalty'
    } else {
      this.title = (this.type == "edit") ? `Edit Penalty` : `View Penalty`;
    }

    const disabled: boolean = (this.type == 'view') ? true : false;
    this.form = this._formBuilder.group({
      method: [{ value: 'MONETARY;ONE_PRICE', disabled }, [Validators.required]],
      enName: [{ value: '', disabled }, [Validators.required]],
      arName: [
        { value: '', disabled: (this.type == 'view') ? true : false },
        [
          Validators.required,
          (this.type !== 'edit') ? Validators.pattern('[\u0600-\u06FF ]*') : Validators.nullValidator
        ]
      ],
      fees: [{ value: 0, disabled }, [Validators.required]],
      email: [{ value: '', disabled }, [Validators.email]],
      subject: [{ value: '', disabled }, []],
      body: [{ value: '', disabled }, []],
      enDescription: [{ value: '', disabled }, [Validators.required]],
      arDescription: [{ value: '', disabled }, [
        Validators.required,
        (this.type !== 'edit') ? Validators.pattern('[\u0600-\u06FF ]*') : Validators.nullValidator
      ]],

    });


    const methodControl = <FormGroup>this.f.method; // for checking

    const emailControl = <FormGroup>this.f.email;
    const subjectControl = <FormGroup>this.f.subject;
    const bodyControl = <FormGroup>this.f.body;

    const feesControl = <FormGroup>this.f.fees;
    const enDescriptionControl = <FormGroup>this.f.enDescription;
    const arDescriptionControl = <FormGroup>this.f.arDescription;

    methodControl.valueChanges.subscribe(value => {
      if (value === 'EMAIL') {
        emailControl.setValidators([Validators.required, Validators.email]);
        subjectControl.setValidators([Validators.required]);
        bodyControl.setValidators([Validators.required]);

        feesControl.setValidators(null);
        enDescriptionControl.setValidators(null);
        arDescriptionControl.setValidators(null);
      } else {
        feesControl.setValidators([Validators.required]);
        enDescriptionControl.setValidators([Validators.required]);
        arDescriptionControl.setValidators([
          Validators.required,
          (this.type !== 'edit') ? Validators.pattern('[\u0600-\u06FF ]*') : Validators.nullValidator
        ]);

        emailControl.setValidators(null);
        subjectControl.setValidators(null);
        bodyControl.setValidators(null);
      }

      emailControl.updateValueAndValidity();
      subjectControl.updateValueAndValidity();
      bodyControl.updateValueAndValidity();

      feesControl.updateValueAndValidity();
      enDescriptionControl.updateValueAndValidity();
      arDescriptionControl.updateValueAndValidity();

    });

  }
  onBackViolations(): void {
    this._router.navigateByUrl(`/enforcement-violations`);
  }

  onBack(): void {
    this._router.navigateByUrl(`/penalties-list`);
  }

  onSubmit() {
    // console.log('onSubmit ========== ', this.form, ' invalid = ', this.form.invalid);

    this.submitted = true;
    // stop here if form is invalid
    if (this.form.invalid) return;

    this.loading = true;
    console.log('fotm value on submit ========== ', this.form.value);

    this.saveData = {
      method: this.f.method.value,
      enName: this.f.enName.value,
      arName: this.f.arName.value,
    };

    if (this.saveData.method == 'EMAIL') {
      this.saveData.email = this.f.email.value;
      this.saveData.subject = this.f.subject.value;
      this.saveData.body = this.f.body.value;
    } else {
      const methodWithType = this.f.method.value.split(';');
      this.saveData.method = methodWithType[0];
      this.saveData.type = methodWithType[1];
      this.saveData.fees = this.f.fees.value;
      this.saveData.enDescription = this.f.enDescription.value;
      this.saveData.arDescription = this.f.arDescription.value;

    }
    console.log('init this.saveData on submit ========== ', this.saveData);

    if (this.id === undefined) {
      this._penaltiesService.create(this.saveData).pipe(first())
        .subscribe(
          data => {
            console.log("create _penaltiesService data =========", data);
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
            console.log("create _penaltiesService err ===== ", error);
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
      this.saveData.id = this.id;
      this._penaltiesService.update(this.saveData).pipe(first())
        .subscribe(
          data => {
            console.log("update _penaltiesService data =========", data);
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
            console.log("create _penaltiesService err ===== ", error);
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

  private _loadFormData(id) {
    this._penaltiesService.getDetails(id)
      .pipe(first())
      .subscribe(
        resp => {
          this.data = resp.payload;
          console.log("this.data ========= ", this.data)
          // this.f['id'].setValue(this.data.id);
          if (this.data.method == 'EMAIL') {
            this.f['method'].setValue(this.data.method);
            this.f['subject'].setValue(this.data.subject);
            this.f['email'].setValue(this.data.email);
            this.f['body'].setValue(this.data.body);
          } else {
            this.f['method'].setValue(`${this.data.method};${this.data.type}`);
            this.f['fees'].setValue(this.data.fees);
          }
          this.f['enName'].setValue(this.data.enName);
          this.f['arName'].setValue(this.data.arName);
          this.f['enDescription'].setValue(this.data.enDescription);
          this.f['arDescription'].setValue(this.data.arDescription);
        },
        error => {
          console.log(" err ===== ", error);
        });
  }

  onSelectionChange($event) {
    console.log('onSelectionChange $event == ', $event);
    // const emailValidationRequired = (this.f.method.value == 'EMAIL') ? Validators.required : Validators.nullValidator;
    // const monetryValidationRequired = (this.f.method.value == 'MONETARY') ? Validators.required : Validators.nullValidator;
    // console.log('emailValidationRequired', emailValidationRequired, 'monetryValidationRequired', monetryValidationRequired);
  }

}