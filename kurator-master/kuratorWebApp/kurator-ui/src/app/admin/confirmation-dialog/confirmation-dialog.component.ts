import { Component, OnInit, Inject, Input } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { ConceptService } from 'src/app/service/concept.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.css']
})
export class ConfirmationDialogComponent implements OnInit {
  @Input() skills: any;
  @Input()  deletedConcepts: any;
  isReviewed: any;
  listOfConcepts = [];
  deleteConceptList = [];
  ngOnInit() {
  }

  // tslint:disable-next-line: no-inferrable-types
  // tslint:disable-next-line: member-ordering
  message = 'Are you sure?';
  // tslint:disable-next-line: member-ordering
  confirmButtonText = 'Yes';
  // tslint:disable-next-line: member-ordering
  cancelButtonText = 'Cancel';
  constructor(
    @Inject(MAT_DIALOG_DATA) private data: any,
    private dialogRef: MatDialogRef<ConfirmationDialogComponent>,
    private conceptService: ConceptService) {
    if (data) {
      this.message = data.message || this.message;
      this.skills = data.skills;
      this.isReviewed = data.isReviewed;
      if (data.buttonText) {
        this.confirmButtonText = data.buttonText.ok || this.confirmButtonText;
        this.cancelButtonText = data.buttonText.cancel || this.cancelButtonText;
      }
    }
  }

  onConfirmClick(): void {
    this.dialogRef.close(true);
    console.log(this.skills);
    console.log(this.deletedConcepts);
    this.skills.forEach(element => {
      const varObj = { name: '', reviewed: '' };
      varObj.name = element;
      varObj.reviewed = this.isReviewed;
      this.listOfConcepts.push(varObj);
      console.log('data::::::::', this.listOfConcepts);
    });
    this.conceptService.addConcepts(this.listOfConcepts).subscribe((response) => {
      console.log('success');


    }, (error: HttpErrorResponse) => {
      console.error('error');
    });
    this.conceptService.deleteConcepts(this.deleteConceptList).subscribe((data) => {
      console.log('Concept Deleted ::');

    });

  }

}
