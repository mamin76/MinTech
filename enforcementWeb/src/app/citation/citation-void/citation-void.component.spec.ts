import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CitationVoidComponent } from './citation-void.component';

describe('CitationVoidComponent', () => {
  let component: CitationVoidComponent;
  let fixture: ComponentFixture<CitationVoidComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CitationVoidComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CitationVoidComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
