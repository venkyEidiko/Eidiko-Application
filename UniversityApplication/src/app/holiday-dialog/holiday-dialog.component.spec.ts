import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HolidayDialogComponent } from './holiday-dialog.component';

describe('HolidayDialogComponent', () => {
  let component: HolidayDialogComponent;
  let fixture: ComponentFixture<HolidayDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HolidayDialogComponent]
    });
    fixture = TestBed.createComponent(HolidayDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
