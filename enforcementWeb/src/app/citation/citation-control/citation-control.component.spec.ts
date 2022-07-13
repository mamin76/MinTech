import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CitationControlComponent } from './citation-control.component';

describe('CitationControlComponent', () => {
  let component: CitationControlComponent;
  let fixture: ComponentFixture<CitationControlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CitationControlComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CitationControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
