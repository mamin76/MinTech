import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-shift-details-card',
  templateUrl: './shift-details-card.component.html',
  styleUrls: ['./shift-details-card.component.scss'],
})
export class ShiftDetailsCardComponent implements OnInit {
  @Input() title: string;
  @Input() name: string;
  @Input() membersCount: number;
  @Input() day: string;
  @Input() shiftStartTime: string;
  @Input() shiftEndTime: string;
  @Input() roleName: string;
  time: string = '';
  link: string = '';

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.link = `startDate=${this.day}&endDate=${this.day}&startTime=${this.shiftStartTime}&endTime=${this.shiftEndTime}&roleName=${this.roleName}`;
    this.time = `${this._timeFormating(
      this.shiftStartTime
    )} to ${this._timeFormating(this.shiftEndTime)}`;

    // console.log('link', this.link);
  }

  viewShiftDetails() {
    this.router.navigateByUrl(`/shift-detail?${this.link}`);
  }

  private _timeFormating(time: string) {
    const timeSpitted = time.split(':');
    return Number(timeSpitted[0]) > 12
      ? `${Number(timeSpitted[0]) - 12}:${timeSpitted[1]} PM`
      : `${time} AM`;
  }
}
