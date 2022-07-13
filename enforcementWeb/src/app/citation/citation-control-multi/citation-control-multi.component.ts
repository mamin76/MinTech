import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import { CitationService } from '../_services';
import { first } from 'rxjs/operators';

import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmation } from '../../_shared/dialog-confirmation/dialog-confirmation';
import { CitationInvoiceDialog } from '../_shared/citation-invoice/citation-invoice.dialog';


@Component({
  selector: 'app-citation-control-multi',
  templateUrl: './citation-control-multi.component.html',
  styleUrls: ['./citation-control-multi.component.scss']
})
export class CitationControlMultiComponent implements OnInit {

  loading = false;
  data: any = {};
  IDs;
  penalties: any;
  total: number; // from penalties com
  panelOpenState = false;

  totals: any = [];
  penaltiesItems: any = [];

  constructor(
    private _router: Router,
    private _activatedRoute: ActivatedRoute,
    private _citationService: CitationService,
    private _dialog: MatDialog
  ) {
    this.IDs = this._activatedRoute.snapshot.params.ids;
    console.log()
    this._loadCitation(this.IDs);

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

  getPenalities($event, idx) {
    // console.log("idx ============ ", idx, ", $event", $event);
    this.penaltiesItems[idx] = $event;
    console.log("idx ============ ", idx, ", this.penaltiesItems", this.penaltiesItems);

    return $event;
  }

  getTotal($event, idx) {
    this.totals[idx] = $event;
    this.total = this.totals.reduce((a, b) => a + b);
    return this.total;
  }

  onApply() {
    // const comment = this.penalties.citationRequests[0].penaltiesRequestList.find(element => element.penActionType == 'Voided');
    // console.log("comment", comment);
    const dialogRef = this._dialog.open(DialogConfirmation, {
      panelClass: 'custom-mat-show-image-dialog-container',
      width: '468px',
      // height: '500px',
      data: {
        title: { title: "Apply Settle & Void to citation", color: "#481A90" },
        body: ` You’re are going to settle & void actions of
        citation "${this.data[0].plateNumberEn}", please confirm `,
        btnConfirmation: { title: "Confirm", color: "#481A90" },
        btnClose: { title: "No, Cancel" },
        comment: false
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed result', result);

      console.log("The dialog was closed panalitiesItems", this.penaltiesItems);
      // this.penalties = this.penaltiesItems.reduce((a, b) => a.citationRequests.concat(b.citationRequests));
      
      console.log("The dialog was closed penalties", this.penalties);

      if (result) {
        // if (result.comment)
        //   this.penalties.citationRequests.map((citationRequest) => {
        //     citationRequest.penaltiesRequestList.map((penaltiesRequest) => {
        //       console.log("penaltiesRequest", penaltiesRequest);
        //       return penaltiesRequest.comment = result.comment;
        //     });
        //   });

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
