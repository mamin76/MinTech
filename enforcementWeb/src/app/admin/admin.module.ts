import { NgModule } from '@angular/core';
import { NgxIntlTelInputModule } from 'ngx-intl-tel-input';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSelectModule } from '@angular/material/select';
import { MatNativeDateModule } from '@angular/material/core';
import { MatListModule } from '@angular/material/list';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { ReassignWorkforceComponent } from './reassign-workforce/reassign-workforce.component';
import { EssentialUSersComponent } from './essential-users/essential-users.component';
import { CreateEssentialUserComponent } from './create-essential-user/create-essential-user.component';
import { WorkforceListComponent } from './workforce-list/workforce-list.component';

@NgModule({
  declarations: [
    CreateEssentialUserComponent,
    EssentialUSersComponent,
    ReassignWorkforceComponent,
    WorkforceListComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatPaginatorModule,
    MatIconModule,
    MatInputModule,
    MatSortModule,
    MatTableModule,
    MatButtonModule,
    MatTooltipModule,
    MatSelectModule,
    MatNativeDateModule,
    MatListModule,
    MatGridListModule,
    MatSidenavModule,
    MatFormFieldModule,
    MatCheckboxModule,
    MatProgressSpinnerModule,
    FormsModule,
    BrowserAnimationsModule,
    NgxIntlTelInputModule,
  ],
  providers: [],
})
export class AdminModule {}
