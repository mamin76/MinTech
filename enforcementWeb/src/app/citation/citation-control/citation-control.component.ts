import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import { CitationService } from '../_services';
import { first } from 'rxjs/operators';

import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmation } from '../../_shared/dialog-confirmation/dialog-confirmation';
import { CitationInvoiceDialog } from '../_shared/citation-invoice/citation-invoice.dialog';

@Component({
  selector: 'app-citation-control',
  templateUrl: './citation-control.component.html',
  styleUrls: ['./citation-control.component.scss']
})
export class CitationControlComponent implements OnInit {
  loading = false;
  data: any = {};
  ID;
  penalties: any;
  total: number; // from penalties com
  constructor(
    private _router: Router,
    private _activatedRoute: ActivatedRoute,
    private _citationService: CitationService,
    private _dialog: MatDialog
  ) {
    this.ID = this._activatedRoute.snapshot.params.id;
    this._loadCitation(this.ID);

  }

  ngOnInit(): void {
    // console.log(`CitationControlComponent ID ${this.ID}, data  `, this.data);
  }

  private _loadCitation(ids) {
    this._citationService.getDetails(ids)
      .pipe(first())
      .subscribe(
        resp => {
          this.data = resp.payload;
        },
        error => {
          console.log(" err ===== ", error);
        });
  }

  getPenalities($event) {
    this.penalties = $event;
    return $event;
  }

  getTotal($event) {
    this.total = $event;
    return $event;
  }

  onApply() {
    const comment = this.penalties.citationRequests[0].penaltiesRequestList.find(element => element.penActionType == 'Voided');
    console.log("comment", comment);
    const dialogRef = this._dialog.open(DialogConfirmation, {
      panelClass: 'custom-mat-show-image-dialog-container',
      width: '468px',
      // height: '500px',
      data: {
        title: { title: "Apply Settle & Void to citation", color: "#481A90" },
        body: `You’re are going to settle & void actions of
        citation "${this.data[0].plateNumberEn}", please confirm`,
        btnConfirmation: { title: "Confirm", color: "#481A90" },
        btnClose: { title: "No, Cancel" },
        comment: (comment) ? true : false
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed result', result);
      if (result) {
        if (result.comment)
          this.penalties.citationRequests.map((citationRequest) => {
            citationRequest.penaltiesRequestList.map((penaltiesRequest) => {
              console.log("penaltiesRequest", penaltiesRequest);
              return penaltiesRequest.comment = result.comment;
            });
          });

        this._citationService.payCitation(this.penalties).pipe(first())
          .subscribe(
            data => {
              console.log("update payCitation  data =========", data);
              this.loading = false;

              if (data["payload"].voided) { // if all is voided not show print and redirect to citation list
                this._router.navigateByUrl(`/enforcement-citations`);
              } else {
                const dialogRefInvoice = this._dialog.open(CitationInvoiceDialog, {
                  panelClass: 'custom-mat-show-image-dialog-container',
                  width: '820px',
                  // height: '500px',
                  data: data["payload"],
                  disableClose: true
                });

                dialogRefInvoice.afterClosed().subscribe(result => {
                  console.log("dialogRefInvoice result", result);
                  this._router.navigateByUrl(`/enforcement-citations`);
                });
              }

            },
            error => {
              console.log("update payCitation err ===== ", error);
              this.loading = false;
            });
      }

    });

  }

  onCancel() {
    this._router.navigateByUrl(`/enforcement-citations`);
  }
}
