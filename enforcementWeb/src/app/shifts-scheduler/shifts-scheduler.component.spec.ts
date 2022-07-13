import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShiftsSchedulerComponent } from './shifts-scheduler.component';

describe('ShiftsSchedulerComponent', () => {
  let component: ShiftsSchedulerComponent;
  let fixture: ComponentFixture<ShiftsSchedulerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShiftsSchedulerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShiftsSchedulerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
