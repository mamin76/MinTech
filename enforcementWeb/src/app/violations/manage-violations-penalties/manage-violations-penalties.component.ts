import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { ViolationPenaliesList } from '../_models';
import { MatSort, Sort } from '@angular/material/sort';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

import { first } from 'rxjs/operators';
import { ViolationPenaltiesService } from '../_services';

// import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmation } from '../../_shared/dialog-confirmation/dialog-confirmation';

@Component({
  selector: 'app-manage-violations-penalties',
  templateUrl: './manage-violations-penalties.component.html',
  styleUrls: ['./manage-violations-penalties.component.scss']
})

export class ManageViolationsPenaltiesComponent implements OnInit {
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  loading = false;
  searchKey: '';
  totalRows = 0;
  pageSize = 10;
  currentPage = 0;
  dataSource: MatTableDataSource<ViolationPenaliesList>;

  succeeded: boolean = false;
  failed: boolean = false;
  isTableExpanded = false;
  // isExpansionDetailRow = (i: number, row: Object) =>
  //   row.hasOwnProperty('detailRow');
  expandedElement: any;
  displayedColumns: string[] = ['index', 'id', 'enName', 'fees', 'actions'];
  dataList: [];

  sortBy: string = 'id';
  sortDirection: string = 'ASC';
  operationId
  constructor(
    private _router: Router,
    private _violationPenaltiesService: ViolationPenaltiesService,
    private _dialog: MatDialog
  ) {
    if (localStorage.getItem('currentUser')) {
      const currentUserJson = JSON.parse(localStorage.getItem('currentUser'));
      this.operationId = currentUserJson.employeeDto.operationId;
    }
  }

  ngOnInit(): void {
    this._loadList(0, this.pageSize);
  }

  private _loadList(page: number, limit: number, query: string = '') {
    this.loading = true;


    // console.log("manage violation penalties _loadList operationId", operationId);

    this._violationPenaltiesService.getList(page, limit, this.operationId)
      .pipe(first())
      .subscribe(
        resp => {
          this.dataList = resp.payload.content;
          this.totalRows = resp.payload.totalElements;
          this.dataSource = new MatTableDataSource<ViolationPenaliesList>(this.dataList);
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
      this._violationPenaltiesService.getList(0, this.pageSize, this.operationId, filterValue.trim().toLowerCase())
        .pipe(first())
        .subscribe(
          resp => {
            this.dataList = resp.payload.content;
            this.totalRows = resp.payload.totalElements;
            this.dataSource = new MatTableDataSource<ViolationPenaliesList>(this.dataList);
          },
          error => {
            console.log(" err ===== ", error);
          });
    } else {
      this._loadList(this.currentPage, this.pageSize);
    }
  }

  onSearchClear() {
    this.searchKey = '';
    this._loadList(this.currentPage, this.pageSize);
  }

  onView(row) {
    this._router.navigateByUrl(`/violation-penalties-form/view/${row.id}`);
  }

  onEdit(row) {
    this._router.navigateByUrl(`/violation-penalties-form/edit/${row.id}`);
  }

  onDeactive(row) {
    const dialogRef = this._dialog.open(DialogConfirmation, {
      panelClass: 'custom-mat-show-image-dialog-container',
      width: '468px',
      // height: '500px',
      data: {
        title: { title: "Are you sure?", color: "#D93636" },
        body: `You’re are going to disable violation penalty
        “${row.enName}”, please confirm`,
        btnConfirmation: { title: "Disable", color: "#D93636" },
        btnClose: { title: "Cancel" },
        comment: false
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this._violationPenaltiesService.delete(row.id).pipe(first())
          .subscribe(
            data => {
              this.loading = false;
              this._loadList(this.currentPage, this.pageSize);

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
    this._loadList(this.currentPage, this.pageSize);
  }

  /** Announce the change in sort state for assistive technology. */
  announceSortChange(sortState: Sort) {
    // This example uses English messages. If your application supports
    // multiple language, you would internationalize these strings.
    // Furthermore, you can customize the message to add additional
    // details about the values being sorted.
    this.sortBy = sortState.active;
    this.sortDirection = sortState.direction;
    this._loadList(this.currentPage, this.pageSize);
  }

  onCancelSearch(): void {
    this.onSearchClear();
  }

  onNew() {
    this._router.navigateByUrl(`/violation-penalties-form`);
  }

}
