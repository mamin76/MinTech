import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CitationSettleComponent } from './citation-settle.component';

describe('CitationSettleComponent', () => {
  let component: CitationSettleComponent;
  let fixture: ComponentFixture<CitationSettleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CitationSettleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CitationSettleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
