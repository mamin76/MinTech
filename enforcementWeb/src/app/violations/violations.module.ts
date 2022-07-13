import { NgModule } from '@angular/core';
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
import { MatDialogModule } from '@angular/material/dialog';
import { MatRadioModule } from '@angular/material/radio';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { ViolationsListComponent } from './violations-list/violations-list.component';
import { ViolationsFormComponent } from './violations-form/violations-form.component';
import { PenaltiesFormComponent } from './penalties-form/penalties-form.component';
import { ManageViolationsPenaltiesComponent } from './manage-violations-penalties/manage-violations-penalties.component';
import { ManageViolationsPenaltiesFormComponent } from './manage-violations-penalties-form/manage-violations-penalties-form.component';
import { PenaltiesListComponent } from './penalties-list/penalties-list.component';



@NgModule({
  declarations: [
    ViolationsListComponent,
    ViolationsFormComponent,
    PenaltiesFormComponent,
    ManageViolationsPenaltiesComponent,
    ManageViolationsPenaltiesFormComponent,
    PenaltiesListComponent
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
    MatDialogModule,
    MatRadioModule,
    MatExpansionModule,
    MatProgressSpinnerModule,
    FormsModule,
    BrowserAnimationsModule
  ]
})
export class ViolationsModule { }
