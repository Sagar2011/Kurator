import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/internal/operators/catchError';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ConceptService {
  addConcept: any = '/search/concepts';
  url: any = '/search/concept/admin';
  // const httpParams = new HttpParams().set('name', 'data');
  // httpParams.set('name',  'data');

  // let options = { params: httpParams };
  constructor(private http: HttpClient) { }

  public addConcepts(data: any): Observable<any> {
    console.log('uploadValue: ', data);
    return this.http.post(this.addConcept, data);
  }
  public getConcepts(): Observable<any> {
    return this.http.get(`${this.addConcept}`);
  }
  // public deleteConcepts(): Observable<any> {
  //   return this.http.delete(`${this.deleteConcept}`);
  // }
  deleteConcepts(data: any): Observable<any> {
    console.log('data:: :', data);
    return this.http
      .delete(`${this.url}?concept=${data}`);
    // .delete(`concept/admin?concept=${data}`);

  }
}
