import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChartsModule } from 'ng2-charts';
import { MatCardModule } from '@angular/material/card';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { DashboardHomeComponent } from './dashboard-home/dashboard-home.component';



@NgModule({
  declarations: [DashboardHomeComponent],
  imports: [
    CommonModule,
    ChartsModule,
    MatCardModule,
    MatSelectModule,
    MatProgressSpinnerModule
  ]
})
export class DashboardModule { }
