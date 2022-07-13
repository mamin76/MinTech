import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEssentialUserComponent } from './create-essential-user.component';

describe('CreateEssentialUserComponent', () => {
  let component: CreateEssentialUserComponent;
  let fixture: ComponentFixture<CreateEssentialUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateEssentialUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEssentialUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
