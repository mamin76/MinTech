import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { Router } from '@angular/router';

import { first } from 'rxjs/operators';
import { ShowImageService } from '../../_services';

import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'show-images-dialog',
  templateUrl: './show-images-dialog.html',
  styleUrls: ['./show-images-dialog.scss']
})
export class ShowImagesDialog {
  loading: boolean = false;
  currentIdx = 0;
  imagePath;
  constructor(
    public dialogRef: MatDialogRef<ShowImagesDialog>,
    @Inject(MAT_DIALOG_DATA) public data: [Number],
    private _showImageService: ShowImageService,
    private _sanitizer: DomSanitizer
  ) {
    data.map((item) => {
      console.log("image dialog item", item);
    });
    console.log('images dialog data', data);


  }

  ngOnInit(): void {
    this._loadImage(this.data[this.currentIdx]);
  }

  private _loadImage(idx) {
    if (idx === undefined) return false;
    this.loading=true;
    this._showImageService.getImage(idx)
      .pipe(first())
      .subscribe(
        resp => {
          console.log('dialog image resp', resp);
          this.imagePath = this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,'
            + resp.content);

          console.log("this.imagePath", this.imagePath);
          this.loading=false;
        },
        error => {
          console.log(" err ===== ", error);
          this.loading=true;
        });
  }

  onDot(idx) {
    console.log('onDot', idx);
    this.currentIdx = idx;
    this._loadImage(this.data[idx]);
  }
  onPrev() {
    console.log('onPrev');
    --this.currentIdx;
    this._loadImage(this.data[this.currentIdx]);
  }
  onNext() {
    ++this.currentIdx;
    this._loadImage(this.data[this.currentIdx]);
    console.log('onNext');
  }
  close(): void {
    this.dialogRef.close();
  }

}