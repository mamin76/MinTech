import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import { CitationService } from '../_services';
import { first } from 'rxjs/operators';

import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmation } from '../../_shared/dialog-confirmation/dialog-confirmation';
import { CitationInvoiceDialog } from '../_shared/citation-invoice/citation-invoice.dialog';

@Component({
  selector: 'app-citation-settle',
  templateUrl: './citation-settle.component.html',
  styleUrls: ['./citation-settle.component.scss']
})
export class CitationSettleComponent implements OnInit {

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
          console.log('resp.payload', resp.payload);

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
    const dialogRef = this._dialog.open(DialogConfirmation, {
      panelClass: 'custom-mat-show-image-dialog-container',
      width: '468px',
      // height: '500px',
      data: {
        title: { title: "Settle Citation", color: "#481A90" },
        body: `
        You’re are going to settle citation "${this.data[0].plateNumberEn}", please confirm settlement`,
        btnConfirmation: { title: "Settle", color: "#481A90" },
        btnClose: { title: "No, Cancel" },
        comment: false
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {

        this.penalties.citationRequests.map((citationRequest) => {
          citationRequest.penaltiesRequestList.map((penaltiesRequest) => {
            return penaltiesRequest.comment = "Settle from portal web";
          });
        });

        this._citationService.payCitation(this.penalties).pipe(first())
          .subscribe(
            data => {
              console.log("update payCitation  data =========", data);
              this.loading = false;
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
