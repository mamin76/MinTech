import { SelectionModel } from '@angular/cdk/collections';
import { Location } from '@angular/common';
import {
  Component,
  OnInit,
  AfterViewInit,
  ViewChild,
  ElementRef,
} from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Shift } from '../_models/shift.model';
import { ShiftSchedulerService } from '../_services';

@Component({
  selector: 'app-shift-details',
  templateUrl: './shift-details.component.html',
  styleUrls: ['./shift-details.component.scss'],
})
export class ShiftDetailsComponent implements OnInit, AfterViewInit {
  // @ViewChild('deleteModal') deleteModal: ElementRef;
  @ViewChild('startDate') StartDate: ElementRef;
  @ViewChild('endDate') EndDate: ElementRef;
  dataSource = new MatTableDataSource<Shift>();
  selection = new SelectionModel<Shift>(true, []);
  editShiftTime: boolean = false;
  editWorkforce: boolean = false;
  supervisorsList: any[] = [];
  shiftCardDetail: any;
  shiftDetail: Shift[] = [];
  display: string = 'none';
  months: any[] = [
    '01',
    '02',
    '03',
    '04',
    '05',
    '06',
    '07',
    '08',
    '09',
    '10',
    '11',
    '12',
  ];
  startDate: any;
  endDate: any;
  startTime: any;
  endTime: any;
  roleName: any;
  success: boolean = false;
  successMsg: string = '';
  failed: boolean = false;
  errMsg: string = '';
  newShiftData: object[] = [];

  constructor(
    // private modalService: NgbModal,
    private router: Router,
    private shiftService: ShiftSchedulerService,
    private location: Location,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.startDate = this.route.snapshot.queryParamMap.get('startDate');
    this.endDate = this.route.snapshot.queryParamMap.get('endDate');
    this.startTime = this.route.snapshot.queryParamMap.get('startTime');
    this.endTime = this.route.snapshot.queryParamMap.get('endTime');
    this.roleName = this.route.snapshot.queryParamMap.get('roleName');
    this.getShift();
    this.getSupervisorsList();
  }

  ngAfterViewInit(): void {
    // this.shiftDetail = this.shiftCardDetail.shiftDetails;
  }

  goBack() {
    this.location.back();
  }

  getSupervisorsList() {
    this.shiftService.getSupervisors().subscribe((res) => {
      this.supervisorsList = res.payload;
    });
  }

  getShift() {
    this.shiftService
      .getShiftDetail({
        startDate: this.startDate,
        endDate: this.endDate,
        startTime: this.startTime,
        endTime: this.endTime,
        roleName: this.roleName,
      })
      .subscribe(
        (res) => {
          this.shiftCardDetail = res.payload;
          this.dataSource.data = res.payload.shiftDetails;
        },
        (error) => {
          console.log(error.message);
        }
      );
  }

  deleteShift() {
    this.display = 'block';
  }

  confirmDelete() {
    this.shiftService.deleteShift(this.shiftCardDetail.id).subscribe(
      (res) => {
        if (res.success) this.router.navigateByUrl('/shifts-schedule');
      },
      (error) => {
        this.errMsg = error.error.error.message;
        this.failed = true;
        this.cancelDelete();
      }
    );
  }

  cancelDelete() {
    this.display = 'none';
  }

  editShiftDate() {
    this.editShiftTime = true;
  }

  handleEditWorkforce() {
    this.editShiftTime = false;
    this.editWorkforce = true;
  }

  handleSaveWorkforce() {
    this.shiftService
      .updateShiftDetails(
        this.shiftCardDetail.id,
        this.shiftCardDetail.startDate,
        this.newShiftData
      )
      .subscribe(
        (res) => {
          this.successMsg = res.payload;
          this.failed = false;
          this.success = true;
        },
        (err) => {
          this.errMsg = err.error.error.message;
          this.success = false;
          this.failed = true;
        }
      );
    this.editShiftTime = false;
    this.editWorkforce = false;
  }

  handleSaveShift() {
    this.editWorkforce = true;
    const day = new Date().getDate();
    const month = new Date().getMonth();
    const today = this.StartDate.nativeElement.value.split('-')[2];
    const thisMonth = this.months.indexOf(
      this.StartDate.nativeElement.value.split('-')[1]
    );
    this.EndDate.nativeElement.value;
    if (day < today) {
      this.shiftService
        .updateShiftDate(
          this.shiftCardDetail.id,
          this.StartDate.nativeElement.value,
          this.EndDate.nativeElement.value
        )
        .subscribe(
          (res) => {
            this.successMsg = res.payload;
            this.failed = false;
            this.success = true;
          },
          (error) => {
            this.errMsg = error.error.error.message;
            this.success = false;
            this.failed = true;
          }
        );
    } else {
      this.errMsg = 'Shift already Started please pick a date';
      this.success = false;
      this.failed = true;
    }
    this.editShiftTime = false;
  }

  /** table functionality */
  displayedColumns: string[] = [
    'select',
    'employee',
    'supervisor',
    'shiftStartTime',
    'shiftEndTime',
  ];

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    if (this.isAllSelected()) {
      this.selection.clear();
      return;
    }

    this.selection.select(...this.dataSource.data);
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: any): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${
      row.id + 1
    }`;
  }
}

// workforceShiftDetails:
// [{
//   shiftDetailsId: '100',
//   startTime: '09:00:00',
//   endTime: '10:00:00',
//   employeeId: '48',
//   supervisorId: '93',
// }],

// [
//     {
//         "id": 452,
//         "day": "2022-02-16",
//         "status": null,
//         "shiftStartTime": "08:00:00",
//         "shiftEndTime": "23:59:00",
//         "employee": {
//             "id": 11,
//             "name": "testWorkforce"
//         },
//         "supervisor": {
//             "id": 4,
//             "name": "Soso"
//         }
//     },
//     {
//         "id": 453,
//         "day": "2022-02-16",
//         "status": null,
//         "shiftStartTime": "08:00:00",
//         "shiftEndTime": "23:59:00",
//         "employee": {
//             "id": 83,
//             "name": "Z"
//         },
//         "supervisor": {
//             "id": 9,
//             "name": "ramy"
//         }
//     }
// ]
