import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CitationInvoiceDialog } from './citation-invoice.dialog';

describe('CitationInvoiceDialog', () => {
  let component: CitationInvoiceDialog;
  let fixture: ComponentFixture<CitationInvoiceDialog>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CitationInvoiceDialog ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CitationInvoiceDialog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
