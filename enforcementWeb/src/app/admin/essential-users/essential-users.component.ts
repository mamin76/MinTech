import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { essentialUsersDto } from '../_models/essentialUsersDto';
import { MatSort } from '@angular/material/sort';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { AdminService } from '../_services/admin.service';

@Component({
  selector: 'app-essential-users',
  templateUrl: './essential-users.component.html',
  styleUrls: ['./essential-users.component.scss'],
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
export class EssentialUSersComponent implements OnInit {
  // displayedColumns = ['position', 'name', 'weight'];
  // dataSource = new ExampleDataSource();
  succeeded: boolean;
  failed: boolean;
  isTableExpanded = false;
  isExpansionDetailRow = (i: number, row: Object) =>
    row.hasOwnProperty('detailRow');
  expandedElement: any;
  employee: any;
  membersList: object[] = [];
  errMsg: string = '';

  constructor(
    private router: Router,
    private http: AdminService
  ) {
    // this.dataSource = null;
  }
  searchKey: '';
  totalRows = 0;
  pageSize = 10;
  currentPage = 0;
  dataSource: MatTableDataSource<essentialUsersDto>;

  // isExpansionDetailRow = (i: number, row: Object) => row.hasOwnProperty('detailRow');
  // expandedElement: any;

  displayedColumns = ['name', 'operation', 'role', 'actions'];
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  ngOnInit(): void {
    this.succeeded = this.http.getUserCreationSuccess();
    this.failed = this.http.getUserCreationFailed();
    this.getAllUsers();
  }

  createUser() {
    this.router.navigateByUrl('/create-essential-user');
  }

  getAllUsers() {
    this.http
      .getEssentialUsers(this.searchKey, this.currentPage, this.pageSize)
      .subscribe(
        (res) => {
          this.membersList = res.payload.content;
          this.dataSource = new MatTableDataSource<essentialUsersDto>(
            this.membersList
          );
        },
        (err) => {
          this.errMsg = err.error.error.message;
        }
      );
  }

  applyFilter(e: Event) {
    const filterValue = (e.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  onSearchClear() {
    this.searchKey = '';
    this.dataSource.filter = this.searchKey;
  }

  onView(row) {
    // console.log(row);
    row.isExpanded = !row.isExpanded;
  }

  onEdit(row) {
    this.router.navigateByUrl(`/update-essential-user/${row.id}`);
  }

  onDelete(row) {
    // console.log('delete');
  }

  pageChanged(event: PageEvent) {
    this.pageSize = event.pageSize;
    this.currentPage = event.pageIndex;
    this.getAllUsers();
  }
}
