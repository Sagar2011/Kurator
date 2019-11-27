import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AddTermService {
  addTerm: any = 'search/concepts';
  constructor(private http: HttpClient) { }

  public addTerms(data: any): Observable<any> {
    console.log('uploadValue: ', data);

    return this.http.post(this.addTerm, data);
  }
}
