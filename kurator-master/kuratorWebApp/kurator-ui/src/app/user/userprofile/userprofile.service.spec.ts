import { TestBed } from '@angular/core/testing';

import { UserprofileService } from './userprofile.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';


describe('UserprofileService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
    providers: [UserprofileService]
  }));

  it('should be created', () => {
    const service: UserprofileService = TestBed.get(UserprofileService);
    expect(service).toBeTruthy();
  });
});
