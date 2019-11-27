import { Component, OnInit, ViewChild, TemplateRef, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { MatChipInputEvent, MatInputModule, MatDialog, MatSnackBar } from '@angular/material';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { AddIntentComponent } from '../add-intent/add-intent.component';
import { empty } from 'rxjs';
import { ConceptService } from 'src/app/service/concept.service';
import { AddIntentService } from 'src/app/service/add-intent.service';
// import { constructor } from 'stream';

// export interface Skills {
//   name: string;
// }
// export interface Terms {
//   name: string;
// }

@Component({
  selector: 'app-manage-ontology',
  templateUrl: './manage-ontology.component.html',
  styleUrls: ['./manage-ontology.component.css']
})

export class ManageOntologyComponent implements OnInit {

  @Input() searchModel;

  @Output() searchModelChange: EventEmitter<any> = new EventEmitter();
  public len = [];
  color = 'accent';
  checked = true;
  disabled = false;
  removable: boolean;
  skills: any = [];
  terms: any = [];
  keyword: any;
  form: FormGroup;
  termForm: FormGroup;
  message: any;
  status: boolean;
  buttonStatus = false;
  show: any = false;
  editStatus: boolean;
  intentListToTerms: any;
  intentListToCounterTerms: any;
  concepts = [];
  deletedConcepts = [];
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  searchForm = new FormGroup({
    inputQuery: new FormControl(''),
  });
  constructor(
    private dialog: MatDialog,
    private snackBar: MatSnackBar, private fb: FormBuilder,
    private fb1: FormBuilder, private conceptService: ConceptService, private intentService: AddIntentService) {
    this.form = this.fb.group({
      keySkills: new FormControl([])
    });
  }
  ngOnInit() {
    this.conceptService.getConcepts().subscribe((data) => {
      this.concepts = data;
    });
    this.intentService.getIntent().subscribe((responsive) => {
      this.intentListToTerms = responsive.intentRelatedToTerms;
      console.log(this.intentListToTerms);
      this.len = Object.keys(this.intentListToTerms);
      this.intentListToCounterTerms = responsive.intentRelatedToCounterTerms;
      console.log(this.intentListToCounterTerms);

    });
    this.conceptService.deleteConcepts(this.concepts.pop)
      .subscribe(
        result => console.log(result),
      );
  }

  toggle() {
    this.show = !this.show;

  }



  onAddSkills(event) {
    if (event.key === 'Enter' || event.key === ',') {
      this.addSkillsToArray(this.form.value.keySkills);
      if (this.skills.length > 0) {
        this.buttonStatus = true;
      }
    }
  }

  onBlurMethod() {
    this.addSkillsToArray(this.form.value.keySkills);
  }

  addSkillsToArray(skill) {
    if ((skill || '').trim()) {
      console.log(skill);
      this.skills.push(skill.trim());
    }
    this.form.reset();
    // event.preventDefault();
  }

  onAddTerms(event) {
    if (event.key === 'Enter' || event.key === ',') {
      this.addTermsToArray(this.form.value.keySkills);
      if (this.terms.length > 0) {
        this.buttonStatus = true;
      }
    }
  }

  onBlurTermsMethod() {
    this.addTermsToArray(this.form.value.keySkills);
  }

  addTermsToArray(term) {
    console.log('terms ::', term);
    if ((term || '').trim()) {
      // console.log( this.terms.push(term.trim()));
      this.terms.push(term.trim());
    }
    this.form.reset();
    // event.preventDefault();
  }

  searchConcept(value) {
    this.searchModel = value;
    this.searchModelChange.emit(this.searchModel);

  }
  searchTerm(): any {

  }
  searchIntent(): any {

  }
  editExistingTerms(): any {
    this.buttonStatus = true;
  }
  editExistingConcept(): any {
    this.buttonStatus = true;
    this.editStatus = true;
    this.removable = true;
  }

  openNewIntent() {
    const dialogRef = this.dialog.open(AddIntentComponent, {
      width: '600px',
      height: '400px',
      data: 'Checking data'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.snackBar.open('Intent Added Succesfully', '', {

        duration: 1000,
      });
    });
  }


  openDialog() {

    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: {
        message: 'Are you sure want to save?',
        buttonText: {
          ok: 'Save',
          cancel: 'No'
        },
        skills: this.skills,
        isReviewed: this.checked,
        concepts: this.deletedConcepts
      }
    });
    console.log('skills', this.skills);

    const snack = this.snackBar;

    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        snack.dismiss();
        const a = document.createElement('a');
        a.click();
        a.remove();
        snack.dismiss();
        this.snackBar.open('Concept/Terms/Intent Added succesfully', '', {

          duration: 1000,
        });
      }
    });
  }
}
