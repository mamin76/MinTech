import { Component, OnInit } from '@angular/core';
import { ChartDataSets, ChartOptions, ChartType } from 'chart.js';
import { Color, Label, MultiDataSet, SingleDataSet } from 'ng2-charts';
import { DashboardService } from '../_services';
import { first } from 'rxjs/operators';
interface SelectDropdown {
  value: string;
  title: string;
}
interface SelectDropdownIdName {
  id: string;
  name: string;
}
@Component({
  selector: 'app-dashboard-home',
  templateUrl: './dashboard-home.component.html',
  styleUrls: ['./dashboard-home.component.scss']
})
export class DashboardHomeComponent implements OnInit {
  loading: boolean = false;
  selectedOperationId: string = "";
  selectedYear: string = `${new Date().getFullYear()}`;
  selectedMonth: string = "01";

  months: SelectDropdown[] = [
    { value: '01', title: 'January' },
    { value: '02', title: 'February' },
    { value: '03', title: 'March' },
    { value: '04', title: 'April' },
    { value: '05', title: 'May' },
    { value: '06', title: 'June' },
    { value: '07', title: 'July' },
    { value: '08', title: 'August' },
    { value: '09', title: 'September' },
    { value: '10', title: 'October' },
    { value: '11', title: 'November' },
    { value: '12', title: 'December' }
  ];

  years: SelectDropdown[] = [];
  operations: SelectDropdownIdName[] = [];

  sectionOne: any = {
    sumOfCitations: 0,
    sumOfPaidCitations: 0
  };
  sectionTwo: any = {
    sumOfPaidCitations: 0,
    sumOfCitations: 0,
    activeShifts: 0,
    assignedWorkforces: 0
  }
  /**************** Bar Chart **************** */
  barChartOptions: ChartOptions = {
    responsive: true,
  };
  barChartLabels: Label[] = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
  barChartType: ChartType = 'bar';
  barChartLegend = true;
  barChartPlugins = [];

  barChartData: ChartDataSets[] = [
    { data: [], label: '' }
  ];
  /*********************************** */
  /**************** Pie chart*************** */
  pieChartOptions: ChartOptions = {
    responsive: true,
  };
  pieChartLabels: Label[] = ['Paid', 'Voided', 'Unpaid'];
  pieChartData: SingleDataSet = [];
  pieChartType: ChartType = 'pie';
  pieChartLegend = true;
  pieChartPlugins = [];

  pieChartColors: Color[] = [{
    backgroundColor: ['#7FE4A1', '#FFA78B', '#FFE48F'],
    // borderColor: ['orange', 'yellow','blue' ,'rgba(148,159,177,0.2)']
  }];
  /***************************************************************** */
  constructor(private _dashboardService: DashboardService) {

  }

  ngOnInit(): void {
    this._fillYears();
    this._loadOperationList();

    // this._loadSectionOne();
  }

  private _fillYears(): void {
    this.years.push({ value: `${new Date().getFullYear()}`, title: `${new Date().getFullYear()}` })
    for (let i = 1; i <= 9; ++i) {
      this.years.push({ value: `${new Date().getFullYear() - i}`, title: `${new Date().getFullYear() - i}` })
    }
  }

  private _loadOperationList(): void {
    this.loading = true;
    this._dashboardService.getOperationList()
      .pipe(first())
      .subscribe(
        resp => {
          this.operations = resp.payload;
          this.selectedOperationId = this.operations[0].id;
          console.log('dashboard-hoem Operations', this.operations);
          this.loading = false;
          this._loadAll();
        },
        error => {
          this.loading = false;
          console.log(" err ===== ", error);
        });
  }

  private _loadSectionOne(): void {
    const byMonth = `${this.selectedYear}-${this.selectedMonth}`;
    this._dashboardService.getSectionOne(byMonth, this.selectedOperationId)
      .pipe(first())
      .subscribe(
        resp => {
          this.sectionOne = resp.payload;

          console.log('dashboard-hoem sectionOne', this.sectionOne);
          this.loading = false;

        },
        error => {
          console.log(" err ===== ", error);
          this.loading = false;

        });
  }

  private _loadSectionTwo(): void {
    this._dashboardService.getSectionTwo(this.selectedOperationId)
      .pipe(first())
      .subscribe(
        resp => {
          this.sectionTwo = resp.payload;
          console.log('dashboard-hoem _loadSectionTwo', this.sectionTwo);
          this.loading = false;
        },
        error => {
          console.log(" err ===== ", error);
          this.loading = false;
        });
  }

  private _loadSectionThreePie(): void {
    const byMonth = `${this.selectedYear}-${this.selectedMonth}`;

    this._dashboardService.getSectionThree(byMonth)
      .pipe(first())
      .subscribe(
        resp => {
          const payload = resp.payload;
          this.pieChartData[0] = payload.paidCitations;
          this.pieChartData[1] = payload.voidedCitations;
          this.pieChartData[2] = payload.openCitations;
          this.loading = false;

          console.log('dashboard-hoem getSectionThree pieChartData', this.pieChartData);
        },
        error => {
          this.loading = false;
          console.log(" err ===== ", error);
        });
  }

  private _loadSectionThreeBars(): void {
    const year = `${this.selectedYear}`;

    this._dashboardService.getSectionThreeBars(year)
      .pipe(first())
      .subscribe(
        resp => {
          const data = resp.payload.revenue;
          // this.barChartData[0].data = data
          Object.entries(data).forEach(
            ([key, value]) => this.barChartData[0].data[Number(key) - 1] = value
          );
          console.log('dashboard-hoem getSectionThree barchar', this.barChartData);
          this.loading = false;

        },
        error => {
          console.log(" err ===== ", error);
          this.loading = false;

        });
  }

  private _loadAll(): void {
    console.log("load all");
    this.loading = true;
    this._loadSectionOne();
    this._loadSectionTwo();
    this._loadSectionThreePie();
    this._loadSectionThreeBars();
  }

  onSelectionChange($event) {
    this._resetChartsData();
    this._loadAll();
  }

  getCitationPieRight(idx) {
    return Number(this.pieChartData[idx]) || 0;
  }

  getCitationPieRightPercentage(idx) {
    if (this.pieChartData.length) {
      const total = Number(this.pieChartData[0]) + Number(this.pieChartData[1]) + Number(this.pieChartData[2]);

      const value = (Number(this.pieChartData[idx]) / total) * 100;
      return `${value.toFixed(2)} %`
    }

    return `0 %`;
  }

  private _resetChartsData() {
    this.pieChartData = [];
    this.barChartData[0].data = [];

  }
}
