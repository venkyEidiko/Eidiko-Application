import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResetPasswordConfirmationComponent } from './reset-password-confirmation.component';

describe('ResetPasswordConfirmationComponent', () => {
  let component: ResetPasswordConfirmationComponent;
  let fixture: ComponentFixture<ResetPasswordConfirmationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ResetPasswordConfirmationComponent]
    });
    fixture = TestBed.createComponent(ResetPasswordConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
