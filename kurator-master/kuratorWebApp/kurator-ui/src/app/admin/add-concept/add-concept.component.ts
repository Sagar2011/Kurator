import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { MatDialogRef, MatAutocomplete, MatAutocompleteSelectedEvent, MatChipInputEvent } from '@angular/material';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-add-concept',
  templateUrl: './add-concept.component.html',
  styleUrls: ['./add-concept.component.css']
})
export class AddConceptComponent implements OnInit {

  formData: any;
  msg: string = null;
  constructor( private formBuilder: FormBuilder, public dialogRef: MatDialogRef<AddConceptComponent>,
    ) { }
  uploaddoc = this.formBuilder.group({
    url: [''],
    name: [''],
    description: ['']
  });

  ngOnInit() {
    console.log(this.uploaddoc);
  }
  onAdd() {
    // const url = this.uploaddoc.post('url').value;
    console.log('upload form value', this.uploaddoc.value);
  }
  onNoClick() {
    this.dialogRef.close();

  }
}
