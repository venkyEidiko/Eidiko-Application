import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutWallactivityComponent } from './about-wallactivity.component';

describe('AboutWallactivityComponent', () => {
  let component: AboutWallactivityComponent;
  let fixture: ComponentFixture<AboutWallactivityComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AboutWallactivityComponent]
    });
    fixture = TestBed.createComponent(AboutWallactivityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
