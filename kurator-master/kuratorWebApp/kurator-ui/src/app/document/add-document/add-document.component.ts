import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormControl } from '@angular/forms';
import { MatDialogRef, MatAutocomplete, MatAutocompleteSelectedEvent, MatChipInputEvent } from '@angular/material';
import { AddDocumentService } from '../../service/add-document.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { startWith, map } from 'rxjs/operators';
import { documentScope } from '../../data-model/documentScope';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { UserprofileService } from 'src/app/user/userprofile/userprofile.service';
import { TrendingService } from 'src/app/service/trending.service';
@Component({
  selector: 'app-add-document',
  templateUrl: './add-document.component.html',
  styleUrls: ['./add-document.component.css']
})
export class AddDocumentComponent implements OnInit {
  // for chips
  // visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  topicCtrl = new FormControl();
  filteredtopics: Observable<string[]>;
  termTags: string[] = [];
  alltermTags: string[] = ['java', 'javascript', 'python', 'rabbitmq',
    'spring', 'angular', 'react', 'docker', 'kafka', 'neo4j'];
  @ViewChild('termTagsInput', { static: false }) termTagsInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto', { static: false }) matAutocomplete: MatAutocomplete;
  formData: any;
  msg: string = null;
  clickEvent = false;
  user: any;
  constructor(
    private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<AddDocumentComponent>,
    private addDocumentService: AddDocumentService,
    private snackBar: MatSnackBar,
    private userService: UserprofileService,
    private added: TrendingService
  ) {
    // console.log('document scope', this.scope);
    // console.log('document scope', documentScope.PRIVATE, typeof (documentScope));
    // console.log('topics', topics);
    this.filteredtopics = this.topicCtrl.valueChanges.pipe(
      startWith(null),
      map((fruit: string | null) => fruit ? this._filter(fruit) : this.alltermTags.slice()));
  }
  uploaddoc = this.formBuilder.group({
    url: [''],
    visibility: [''],
    conceptTags: ['']
  });
  onNoClick(): void {
    this.dialogRef.close();
    this.added.getValueAdd();
  }
  ngOnInit() {
    this.uploadDoc();
    this.uploaddoc.patchValue({
      visibility: documentScope.PRIVATE
    });
    // this.scope = documentScope.PUBLIC;
    // this.visiblePublic = false;
  }
  onSlide() {
    // this.visiblePublic = !this.visiblePublic;
    // this.scope = documentScope.PRIVATE;
    // console.log('scope', this.scope);
    // this.uploadDoc();
    console.log(this.clickEvent);
    this.clickEvent = !this.clickEvent;
    // if (!this.clickEvent) {
    //   this.uploaddoc.patchValue({
    //     scope: documentScope.PUBLIC
    //   });
    //   console.log('scope', this.uploaddoc.scope);
    // }
  }
  uploadDoc() { }
  onAdd() {
    // const url = things.uploaddoc.post('url').value;
    // topics: this.termTags;
    if (this.clickEvent) {
      console.log(this.clickEvent);
      this.uploaddoc.patchValue({
        visibility: documentScope.PRIVATE
      });
    } else {
      console.log(this.clickEvent);
      this.uploaddoc.patchValue({
        visibility: documentScope.PUBLIC
      });
    }
    this.uploaddoc.patchValue({
      conceptTags: this.termTags
    });
    console.log('upload form value', this.uploaddoc.value);
    try {
      this.addDocumentService.uploadDoc(this.uploaddoc.value).subscribe(
        res => {
          if (res === true) {
            this.msg = 'SUCCESS';
            this.snackBar.open('Document Added Successfully', null, {
              duration: 2000,
            });
            this.onNoClick();
          } else {
            this.msg = 'the url entered is either invalid or already existing,try again';
            this.snackBar.open('The url entered is either invalid or already existing,try again', null, {
              duration: 2000,
            });
            this.onNoClick();
          }
          this.uploaddoc.reset();
        },
        (error: HttpErrorResponse) => {
          if (error.status === 404) {
            this.msg = 'Page Not Found';
          } else {
            this.msg = 'Sorry Server is Down!!!!!!!';
          }
        });
    } catch (error) {
      this.msg = 'Something Went Wrong try again after sometime............';
    }
  }
  onFileSelected(event) {
    console.log(event);
  }
  // for chips
  add(event: MatChipInputEvent): void {
    // Add fruit only when MatAutocomplete is not open
    // To make sure this does not conflict with OptionSelected Event
    if (!this.matAutocomplete.isOpen) {
      const input = event.input;
      const value = event.value;
      // Add our fruit
      if ((value || '').trim()) {
        this.termTags.push(value.trim());
      }
      // Reset the input value
      if (input) {
        input.value = '';
      }
      this.topicCtrl.setValue(null);
    }
  }
  remove(fruit: string): void {
    const index = this.termTags.indexOf(fruit);
    if (index >= 0) {
      this.termTags.splice(index, 1);
    }
  }
  selected(event: MatAutocompleteSelectedEvent): void {
    this.termTags.push(event.option.viewValue);
    this.termTagsInput.nativeElement.value = '';
    this.topicCtrl.setValue(null);
  }
  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.alltermTags.filter(fruit => fruit.toLowerCase().indexOf(filterValue) === 0);
  }
}
