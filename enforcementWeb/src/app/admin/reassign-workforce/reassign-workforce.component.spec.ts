import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReassignWorkforceComponent } from './reassign-workforce.component';

describe('ReassignWorkforceComponent', () => {
  let component: ReassignWorkforceComponent;
  let fixture: ComponentFixture<ReassignWorkforceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReassignWorkforceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReassignWorkforceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
