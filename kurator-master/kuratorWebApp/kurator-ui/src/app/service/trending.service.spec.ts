import { TestBed } from '@angular/core/testing';

import { TrendingService } from './trending.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('TrendingService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports : [HttpClientTestingModule]
  }));

  it('should be created', () => {
    const service: TrendingService = TestBed.get(TrendingService);
    expect(service).toBeTruthy();
  });
});
