import { TestBed } from '@angular/core/testing';

import { RecentViewService } from './recent-view.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('RecentViewService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule]
  }));

  it('should be created', () => {
    const service: RecentViewService = TestBed.get(RecentViewService);
    expect(service).toBeTruthy();
  });
});
