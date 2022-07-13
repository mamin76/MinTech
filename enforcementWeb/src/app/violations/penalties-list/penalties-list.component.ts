import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { PenaltiesList } from '../_models';
import { MatSort, Sort } from '@angular/material/sort';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

import { first } from 'rxjs/operators';
import { PenaltiesService } from '../_services';

// import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmation } from '../../_shared/dialog-confirmation/dialog-confirmation';

import { AuthorizationService } from 'src/app/_services';

@Component({
  selector: 'app-penalties-list',
  templateUrl: './penalties-list.component.html',
  styleUrls: ['./penalties-list.component.scss']
})
export class PenaltiesListComponent implements OnInit {
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  loading = false;
  searchKey: '';
  totalRows = 0;
  pageSize = 10;
  currentPage = 0;
  dataSource: MatTableDataSource<PenaltiesList>;

  succeeded: boolean = false;
  failed: boolean = false;
  isTableExpanded = false;
  // isExpansionDetailRow = (i: number, row: Object) =>
  //   row.hasOwnProperty('detailRow');
  expandedElement: any;
  displayedColumns: string[] = ['index', 'id', 'enName', 'method', 'type', 'fees', 'actions'];
  dataList: [];

  sortBy: string = 'id';
  sortDirection: string = 'ASC';

  constructor(
    private _router: Router,
    private _penaltiesService: PenaltiesService,
    private _dialog: MatDialog,
    public authorizationService: AuthorizationService

  ) { }

  ngOnInit(): void {
    this._loadList(0, this.pageSize, this.sortBy, this.sortDirection);
  }

  private _loadList(page: number, limit: number, sortBy: string, sortDirection: string, query: string = '') {
    this.loading = true;
    this._penaltiesService.getList(page, limit, query)
      .pipe(first())
      .subscribe(
        resp => {
          this.dataList = resp.payload.content;
          this.totalRows = resp.payload.totalElements;
          this.dataSource = new MatTableDataSource<PenaltiesList>(this.dataList);
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
      this._penaltiesService.getList(0, this.pageSize, filterValue.trim().toLowerCase())
        .pipe(first())
        .subscribe(
          resp => {
            this.dataList = resp.payload.content;
            this.totalRows = resp.payload.totalElements;
            this.dataSource = new MatTableDataSource<PenaltiesList>(this.dataList);
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
    this._router.navigateByUrl(`/penalty-form/view/${row.id}`);
  }

  onEdit(row) {
    this._router.navigateByUrl(`/penalty-form/edit/${row.id}`);
  }

  onDeactive(row) {
    const dialogRef = this._dialog.open(DialogConfirmation, {
      panelClass: 'custom-mat-show-image-dialog-container',
      width: '468px',
      // height: '500px',
      data: {
        title: { title: "Are you sure?", color: "#D93636" },
        body: `You’re are going to disable penalty
        “${row.enName}”, please confirm`,
        btnConfirmation: { title: "Disable", color: "#D93636" },
        btnClose: { title: "Cancel" },
        comment: false
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this._penaltiesService.delete(row.id).pipe(first())
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

  onDefinePenalty() {
    this._router.navigateByUrl(`/penalty-form`);
  }

  onBack(): void {
    this._router.navigateByUrl(`/enforcement-violations`);
  }

  getMethodText(method, type) {
    console.log('type', type);
    if (method === 'EMAIL')
      return 'Email';
    else
      return (type === 'ONE_PRICE') ? 'Montarey One Time' : 'Montarey Counted';
  }
}
