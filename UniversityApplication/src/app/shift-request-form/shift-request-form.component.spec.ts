import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShiftRequestFormComponent } from './shift-request-form.component';

describe('ShiftRequestFormComponent', () => {
  let component: ShiftRequestFormComponent;
  let fixture: ComponentFixture<ShiftRequestFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ShiftRequestFormComponent]
    });
    fixture = TestBed.createComponent(ShiftRequestFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
