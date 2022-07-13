import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShiftDetailsCardComponent } from './shift-details-card.component';

describe('ShiftDetailsCardComponent', () => {
  let component: ShiftDetailsCardComponent;
  let fixture: ComponentFixture<ShiftDetailsCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShiftDetailsCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShiftDetailsCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
