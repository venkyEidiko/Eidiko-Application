import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilePrflComponent } from './profile-prfl.component';

describe('ProfilePrflComponent', () => {
  let component: ProfilePrflComponent;
  let fixture: ComponentFixture<ProfilePrflComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProfilePrflComponent]
    });
    fixture = TestBed.createComponent(ProfilePrflComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
