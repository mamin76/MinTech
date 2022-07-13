import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import { CitationService } from '../_services';
import { first } from 'rxjs/operators';

import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmation } from '../../_shared/dialog-confirmation/dialog-confirmation';

@Component({
  selector: 'app-citation-void',
  templateUrl: './citation-void.component.html',
  styleUrls: ['./citation-void.component.scss']
})
export class CitationVoidComponent implements OnInit {

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
        title: { title: "Void Citation", color: "#D93636" },
        body: `Please enter and confirm a void
        reason description`,
        btnConfirmation: { title: "Void Citation", color: "#D93636" },
        btnClose: { title: "Cancel" },
        comment: true
      }
    });

    dialogRef.afterClosed().subscribe(result => {
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
              this._router.navigateByUrl(`/enforcement-citations`);
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
