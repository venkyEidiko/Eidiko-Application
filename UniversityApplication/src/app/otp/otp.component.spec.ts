import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OtpComponent } from './otp.component';

describe('OtpComponent', () => {
  let component: OtpComponent;
<<<<<<< HEAD
  let fixture: ComponentFixture<OtpComponent>; 
=======
  let fixture: ComponentFixture<OtpComponent>;
>>>>>>> 6b9cec8c11ef7c26b146dabc4ec9b32a35a8185e

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OtpComponent]
    });
    fixture = TestBed.createComponent(OtpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
