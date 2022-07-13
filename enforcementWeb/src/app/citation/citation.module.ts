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
import { NgxQRCodeModule } from '@techiediaries/ngx-qrcode';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { CitationListComponent } from './citation-list/citation-list.component';
import { CitationSettleComponent } from './citation-settle/citation-settle.component';
import { CitationVoidComponent } from './citation-void/citation-void.component';

import { ShowImagesDialog } from './citation-list/show-images-dialog/show-images-dialog';
import { CitationControlComponent } from './citation-control/citation-control.component';
import { CitationPenaltiesComponent } from './_penalties/citation-penalties/citation-penalties.component';
import { CitationPenaltiesTotalComponent } from './_penalties/citation-penalties-total/citation-penalties-total.component';
import { CitationInvoiceDialog } from './_shared/citation-invoice/citation-invoice.dialog';
import { CitationControlMultiComponent } from './citation-control-multi/citation-control-multi.component';

@NgModule({
  declarations: [
    CitationListComponent,
    CitationSettleComponent,
    CitationVoidComponent,
    ShowImagesDialog,
    CitationControlComponent,
    CitationPenaltiesComponent,
    CitationPenaltiesTotalComponent,
    CitationInvoiceDialog,
    CitationControlMultiComponent
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
    NgxQRCodeModule,
    MatProgressSpinnerModule,
    FormsModule,
    BrowserAnimationsModule
  ]
})
export class CitationModule { }
