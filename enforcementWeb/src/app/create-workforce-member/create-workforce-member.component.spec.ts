import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateWorkforceMemberComponent } from './create-workforce-member.component';

describe('CreateWorkforceMemberComponent', () => {
  let component: CreateWorkforceMemberComponent;
  let fixture: ComponentFixture<CreateWorkforceMemberComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateWorkforceMemberComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateWorkforceMemberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
