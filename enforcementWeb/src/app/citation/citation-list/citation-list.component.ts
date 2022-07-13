import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { CitationList } from '../_models/citationList';
import { MatSort, Sort } from '@angular/material/sort';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';

import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { first } from 'rxjs/operators';
import { CitationService } from '../_services';

import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { ShowImagesDialog } from './show-images-dialog/show-images-dialog';
@Component({
  selector: 'app-list',
  templateUrl: './citation-list.component.html',
  styleUrls: ['./citation-list.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition(
        'expanded <=> collapsed',
        animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')
      ),
    ]),
  ],
})
export class CitationListComponent implements OnInit {
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  loading: boolean = false;
  searchKey: '';
  totalRows = 0;
  pageSize = 10;
  currentPage = 0;
  dataSource: MatTableDataSource<CitationList>;

  succeeded: boolean = false;
  failed: boolean = false;
  isTableExpanded = false;
  // isExpansionDetailRow = (i: number, row: Object) =>
  //   row.hasOwnProperty('detailRow');
  expandedElement: any;
  displayedColumns: string[] = ['index', 'id', 'plateNumberEn', 'violationName', 'actions'];
  dataList: [];

  sortBy: string = 'id';
  sortDirection: string = 'ASC';

  isListMultiSelect = false;

  displayedColumnsMulti: string[] = ['select', 'index', 'id', 'plateNumberEn', 'violationName'];
  dataSourceMulti: MatTableDataSource<CitationList>;

  dataListMulti: [] = [];
  // multi Select
  selection = new SelectionModel<CitationList>(true, []);

  constructor(
    private _router: Router,
    private _citationService: CitationService,
    public dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this._loadCitationList(0, this.pageSize, this.sortBy, this.sortDirection);
  }

  private _loadCitationList(offset: number, limit: number, sortBy: string, sortDirection: string, query: string = '') {
    this.loading = true;
    this._citationService.getList(offset, limit, sortBy, sortDirection, query)
      .pipe(first())
      .subscribe(
        resp => {
          this.dataList = resp.payload.content;
          console.log('resp.payload', resp.payload);
          this.totalRows = resp.payload.totalElements;

          this.dataSource = new MatTableDataSource<CitationList>(this.dataList);
          this.loading = false;
        },
        error => {
          console.log(" err ===== ", error);
          this.loading = false;
        });
  }

  applyFilter(e: Event) {

    const filterValue = (e.target as HTMLInputElement).value;
    console.log("Citation List on Search applyFilter filterValue", filterValue);

    // this.dataSource.filter = filterValue.trim().toLowerCase();
    console.log("Citation List on Search applyFilter filterValue.trim().toLowerCase()", filterValue.trim().toLowerCase());
    if (filterValue.length >= 3) {
      this.isListMultiSelect = true;
      this.loading = true;
      this._citationService.getList(0, this.pageSize, this.sortBy, this.sortDirection, filterValue)
        .pipe(first())
        .subscribe(
          resp => {
            this.dataListMulti = resp.payload.content;
            console.log('resp.payload', resp.payload);
            this.totalRows = resp.payload.totalElements;

            this.dataSourceMulti = new MatTableDataSource<CitationList>(this.dataListMulti);
            this.loading = false;
          },
          error => {
            this.loading = false;
            console.log(" err ===== ", error);
          });
    } else if (!filterValue.length) {
      this.isListMultiSelect = false;
    }
  }

  onSearchClear() {
    this.searchKey = '';
    console.log("Citation List on Search Clear", this.searchKey);
    this.dataSource.filter = this.searchKey;
    this.isListMultiSelect = false;

  }
  onControl(row) {
    // console.log('onControl(row)', row);
    // this._router.navigate(['/citation-control'], {
    //   state: row
    // });
    this._router.navigateByUrl(`/citation-control/${row.id}`);
  }

  onShowImage(row) {
    // console.log('onShowImage(row)', row);
    const dialogRef = this.dialog.open(ShowImagesDialog, {
      panelClass: 'custom-mat-show-image-dialog-container',
      width: '960px',
      height: '500px',
      data: row.imagesIds
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed', result);
    });

    //this._router.navigateByUrl(`//${row.id}`);

  }

  onSettle(row) {
    // console.log('onSettle(row)', row);
    this._router.navigateByUrl(`/citation-settle/${row.id}`);
  }

  onVoid(row) {
    // console.log('onVoid(row)', row);
    this._router.navigateByUrl(`/citation-void/${row.id}`);
  }

  pageChanged(event: PageEvent) {
    console.log('pageChanged event', event);
    this.pageSize = event.pageSize;
    this.currentPage = event.pageIndex;

    console.log('pageChanged this.pageSize', this.pageSize);
    console.log('pageChanged this.currentPage', this.currentPage);

    this._loadCitationList(this.currentPage, this.pageSize, this.sortBy, this.sortDirection);
  }

  /** Announce the change in sort state for assistive technology. */
  announceSortChange(sortState: Sort) {
    // This example uses English messages. If your application supports
    // multiple language, you would internationalize these strings.
    // Furthermore, you can customize the message to add additional
    // details about the values being sorted.

    //id  ,plateNumberEn, enName
    console.log("sortState", sortState);
    this.sortBy = sortState.active;
    this.sortDirection = sortState.direction;

    this._loadCitationList(this.currentPage, this.pageSize, this.sortBy, this.sortDirection);

  }

  // multi Select

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    if (!this.dataSource) return false;
    const numSelected = this.selection.selected.length;
    const numRows = (this.dataSourceMulti) ? this.dataSourceMulti.data.length : 0;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSourceMulti.data.forEach(row => this.selection.select(row));
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: CitationList): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;
  }

  onControlCitations(): void {
    if (!this.selection["_selected"].length) return;
    let ids = "";
    this.selection["_selected"].map((item) => {
      (ids.length) ? ids += `,${item.id}` : ids = `${item.id}`;
    });
    this._router.navigateByUrl(`/citation-control-multi/${ids}`);

  }
  onCancelSearch(): void {
    this.onSearchClear();
    console.log("onControlCitations");
  }
}