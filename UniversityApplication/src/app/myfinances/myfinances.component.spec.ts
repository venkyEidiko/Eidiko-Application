import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyfinancesComponent } from './myfinances.component';

describe('MyfinancesComponent', () => {
  let component: MyfinancesComponent;
  let fixture: ComponentFixture<MyfinancesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MyfinancesComponent]
    });
    fixture = TestBed.createComponent(MyfinancesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
