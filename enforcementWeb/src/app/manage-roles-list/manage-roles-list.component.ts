import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { workforcesDto } from '../admin/_models/workforcesDto';
import { MatSort } from '@angular/material/sort';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { DataService } from '../data.service';
import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';

@Component({
  selector: 'app-manage-roles-list',
  templateUrl: './manage-roles-list.component.html',
  styleUrls: ['./manage-roles-list.component.scss'],
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
export class ManageRolesListComponent implements OnInit {
  isTableExpanded = false;
  isExpansionDetailRow = (i: number, row: Object) =>
    row.hasOwnProperty('detailRow');
  expandedElement: any;
  searchKey: '';
  totalRows = 0;
  pageSize = 10;
  currentPage = 0;
  dataSource: MatTableDataSource<any>;
  rolesList: any[] = [];
  roleName: string = '';

  constructor(private router: Router, private dataService: DataService) {}

  // isExpansionDetailRow = (i: number, row: Object) => row.hasOwnProperty('detailRow');
  // expandedElement: any;

  displayedColumns = ['roleName', 'actions'];
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  ngOnInit(): void {
    this.getRolesList();
  }

  getRolesList() {
    // pageCount/limit/roleName
    this.dataService
      .getOperationsRolesBy(this.currentPage, this.pageSize, this.roleName)
      .subscribe((res) => {
        this.rolesList = res.payload.content;
        this.dataSource = new MatTableDataSource<any>(this.rolesList);
      });
  }

  createNewRole() {
    this.router.navigateByUrl('/create-roles');
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
    row.isExpanded = !row.isExpanded;
  }

  onEdit(row) {
    this.router.navigateByUrl(`/create-roles`);
  }

  onDelete(row) {
    this.dataService.deleteRoles(row.roleName).subscribe(
      (res) => {
        if (res.success) this.getRolesList();
      },
      (error) => {
        if (error.erorr.code == 400) return;
      }
    );
  }

  pageChanged(event: PageEvent) {
    this.pageSize = event.pageSize;
    this.currentPage = event.pageIndex;
  }
}
