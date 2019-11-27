import { TestBed } from '@angular/core/testing';

// import { AuthGuardService } from './auth-guard.service';
import { RouterTestingModule } from '@angular/router/testing';
import { AuthenticationService } from './authentication.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('AuthGuardService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [RouterTestingModule, HttpClientTestingModule],
    providers: [AuthenticationService]
  }));

  // it('should be created', () => {
  //   const service: AuthGuardService = TestBed.get(AuthGuardService);
  //   expect(service).toBeTruthy();
  // });
});
