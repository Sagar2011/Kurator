import { TestBed } from '@angular/core/testing';

import { FavouriteServiceService } from './favourite-service.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('FavouriteServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
    providers: [FavouriteServiceService]
  }));

  it('should be created', () => {
    const service: FavouriteServiceService = TestBed.get(FavouriteServiceService);
    expect(service).toBeTruthy();
  });
});
