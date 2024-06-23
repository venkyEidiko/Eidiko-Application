import { TestBed } from '@angular/core/testing';

import { MonthlyLeaveService } from './monthly-leave.service';

describe('MonthlyLeaveService', () => {
  let service: MonthlyLeaveService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MonthlyLeaveService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
