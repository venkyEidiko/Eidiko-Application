import { TestBed } from '@angular/core/testing';

import { ShiftRequestService } from './shift-request.service';

describe('ShiftRequestService', () => {
  let service: ShiftRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShiftRequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
