
import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'dialog-confirmation',
  templateUrl: './dialog-confirmation.html',
  styleUrls: ['./dialog-confirmation.scss']
})
export class DialogConfirmation {
  comment: string = '';
  form: FormGroup;
  get f() { return this.form.controls; }

  submitted = false;

  constructor(
    public dialogRef: MatDialogRef<DialogConfirmation>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _fb: FormBuilder

  ) {
    console.log(' dialog data', data);
  }

  ngOnInit(): void {
    this.form = this._fb.group({
      comment: ['', Validators.required],
    });
  }

  close(): void {
    this.dialogRef.close();
  }

  onConfirm(): void {
    if (!this.data.comment) {
      this.dialogRef.close({ confirmed: true });
    } else {
      this.submitted = true;
      // stop here if form is invalid
      if (this.form.invalid) return;

      this.dialogRef.close({ confirmed: true, comment: this.f.comment.value });
    }

  }

  public myError = (controlName: string, errorName: string) => {
    return (this.submitted) ? this.form.controls[controlName].hasError(errorName) : false;
  }
}