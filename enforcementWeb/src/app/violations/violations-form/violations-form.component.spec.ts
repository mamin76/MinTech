import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViolationsFormComponent } from './violations-form.component';

describe('ViolationsFormComponent', () => {
  let component: ViolationsFormComponent;
  let fixture: ComponentFixture<ViolationsFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViolationsFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViolationsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
