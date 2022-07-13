import { Component, OnInit, ViewChild, ChangeDetectorRef, AfterContentChecked } from '@angular/core';

import { Router } from '@angular/router';
import { workforcesDto } from '../_models/workforcesDto';
import { MatSort } from '@angular/material/sort';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { AdminService } from '../_services/admin.service';
import { DomSanitizer } from '@angular/platform-browser';
import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-workforce-list',
  templateUrl: './workforce-list.component.html',
  styleUrls: ['./workforce-list.component.scss'],
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
export class WorkforceListComponent implements OnInit {
  // displayedColumns = ['position', 'name', 'weight'];
  // dataSource = new ExampleDataSource();
  succeeded: boolean = false;
  failed: boolean = false;
  isTableExpanded = false;
  isExpansionDetailRow = (i: number, row: Object) =>
    row.hasOwnProperty('detailRow');
  expandedElement: any;
  membersList: any[];
  loading = false;

  constructor(
    private sanitizer: DomSanitizer,
    private router: Router,
    private adminService: AdminService,
    private changeDetector: ChangeDetectorRef,

  ) {
    // this.dataSource = null;
  }
  searchKey: '';
  totalRows = 0;
  pageSize = 5;
  currentPage = 0;
  dataSource: MatTableDataSource<workforcesDto>;

  // isExpansionDetailRow = (i: number, row: Object) => row.hasOwnProperty('detailRow');

  displayedColumns = ['name', 'operation', 'role', 'actions'];
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  ngOnInit(): void {
    this.getMembersList();
  }

  getMembersList() {
    this.adminService
      .getEmployeesList(this.currentPage, this.pageSize, '')
      .subscribe((res) => {
        console.log(res.payload);
        this.membersList = res.payload.content;
        this.dataSource = new MatTableDataSource<workforcesDto>(
          this.membersList
        );
      });
  }

  getImage(el) {
    if (el.isExpanded == true && !el.image && !el.isLoaded) {
      el.isLoaded = true;
      console.log("getImage from workforce-list image el", el);
      this.loading = true;
      this.adminService.getImage(el.id)
        .pipe(first())
        .subscribe(
          resp => {
            // console.log('success dialog image resp', resp.payload);
            el.image = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + resp.payload);
            this.loading = false;
          },
          error => {
            // console.log(" dialog image err ===== ", error);
            el.image = '../../../assets/Icons/user-circle.svg';
            this.loading = false;
          });
    }

    return true;
  }

  detailExpand(el) {
    return el.isExpanded ? 'expanded' : 'collapsed';
  }

  reassignWorkforce() {
    this.router.navigateByUrl('/assign-workforce');
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
    console.log(row);
    // this.router.navigateByUrl(`/add-workforce`);
  }

  onDelete(row) {
    // console.log('delete');
  }

  pageChanged(event: PageEvent) {
    this.pageSize = event.pageSize;
    this.currentPage = event.pageIndex;
    this.getMembersList();
  }
  ngAfterContentChecked(): void {
    this.changeDetector.detectChanges();
  }
}
