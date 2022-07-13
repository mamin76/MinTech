import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { ViolationsList } from '../_models';
import { MatSort, Sort } from '@angular/material/sort';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

import { first } from 'rxjs/operators';
import { ViolationsService } from '../_services';

// import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmation } from '../../_shared/dialog-confirmation/dialog-confirmation';

import { AuthorizationService } from 'src/app/_services';
@Component({
  selector: 'app-violations-list',
  templateUrl: './violations-list.component.html',
  styleUrls: ['./violations-list.component.scss']
})
export class ViolationsListComponent implements OnInit {
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  loading = false;
  searchKey: '';
  totalRows = 0;
  pageSize = 10;
  currentPage = 0;
  dataSource: MatTableDataSource<ViolationsList>;

  succeeded: boolean = false;
  failed: boolean = false;
  isTableExpanded = false;
  // isExpansionDetailRow = (i: number, row: Object) =>
  //   row.hasOwnProperty('detailRow');
  expandedElement: any;
  displayedColumns: string[] = ['index', 'id', 'enName', 'actions'];
  dataList: [];

  sortBy: string = 'id';
  sortDirection: string = 'ASC';

  constructor(
    private _router: Router,
    private _violationsService: ViolationsService,
    private _dialog: MatDialog,
    public authorizationService: AuthorizationService
  ) {
  }

  ngOnInit(): void {
    this._loadList(0, this.pageSize, this.sortBy, this.sortDirection);
  }

  private _loadList(page: number, limit: number, sortBy: string, sortDirection: string, query: string = '') {
    this.loading = true;
    this._violationsService.getList(page + 1, limit, sortBy, sortDirection, query)
      .pipe(first())
      .subscribe(
        resp => {
          this.dataList = resp.payload.content;
          this.totalRows = resp.payload.totalElements;
          this.dataSource = new MatTableDataSource<ViolationsList>(this.dataList);
          this.loading = false;
        },
        error => {
          this.loading = false;
          console.log(" err ===== ", error);
        });
  }

  applyFilter(e: Event) {
    const filterValue = (e.target as HTMLInputElement).value;
    if (filterValue.length >= 3) {
      this._violationsService.getList(1, this.pageSize, this.sortBy, this.sortDirection, filterValue.trim().toLowerCase())
        .pipe(first())
        .subscribe(
          resp => {
            this.dataList = resp.payload.content;
            this.totalRows = resp.payload.totalElements;
            this.dataSource = new MatTableDataSource<ViolationsList>(this.dataList);
          },
          error => {
            console.log(" err ===== ", error);
          });
    } else {
      this._loadList(this.currentPage, this.pageSize, this.sortBy, this.sortDirection);
    }
  }

  onSearchClear() {
    this.searchKey = '';
    this._loadList(this.currentPage, this.pageSize, this.sortBy, this.sortDirection);
  }

  onView(row) {
    this._router.navigateByUrl(`/violation-form/view/${row.id}`);
  }

  onEdit(row) {
    this._router.navigateByUrl(`/violation-form/edit/${row.id}`);
  }

  onDeactive(row) {
    const dialogRef = this._dialog.open(DialogConfirmation, {
      panelClass: 'custom-mat-show-image-dialog-container',
      width: '468px',
      // height: '500px',
      data: {
        title: { title: "Are you sure?", color: "#D93636" },
        body: `You’re are going to disable violation
        “${row.enName}”, please confirm`,
        btnConfirmation: { title: "Disable", color: "#D93636" },
        btnClose: { title: "Cancel" },
        comment: false
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this._violationsService.delete(row.id).pipe(first())
          .subscribe(
            data => {
              this.loading = false;
              this._loadList(this.currentPage, this.pageSize, this.sortBy, this.sortDirection);

            },
            error => {
              this.loading = false;
            });
      }
    });


  }

  pageChanged(event: PageEvent) {
    this.pageSize = event.pageSize;
    this.currentPage = event.pageIndex;
    this._loadList(this.currentPage, this.pageSize, this.sortBy, this.sortDirection);
  }

  /** Announce the change in sort state for assistive technology. */
  announceSortChange(sortState: Sort) {
    // This example uses English messages. If your application supports
    // multiple language, you would internationalize these strings.
    // Furthermore, you can customize the message to add additional
    // details about the values being sorted.
    this.sortBy = sortState.active;
    this.sortDirection = sortState.direction;
    this._loadList(this.currentPage, this.pageSize, this.sortBy, this.sortDirection);
  }

  onCancelSearch(): void {
    this.onSearchClear();
  }

  onDefineViolation() {
    this._router.navigateByUrl(`/violation-form`);
  }
  onManagePenalties() {
    this._router.navigateByUrl(`/penalties-list`);
  }
}
