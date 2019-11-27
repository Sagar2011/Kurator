import { Component, OnInit } from '@angular/core';
import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { MatChipInputEvent, MatSnackBar, MatDialog, MatDialogRef } from '@angular/material';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { AddIntentService } from 'src/app/service/add-intent.service';
export interface Indicator {
  ter: string;
  citer: string;
}
// export interface CounterIndicator {

// }
export interface Intent {
  intentName: string;
  indicator: Indicator[];

  // counterIndicator: CounterIndicator[];
}

@Component({
  selector: 'app-add-intent',
  templateUrl: './add-intent.component.html',
  styleUrls: ['./add-intent.component.css']
})
export class AddIntentComponent implements OnInit {
  formStatus: any = true;
  // durationInSeconds = 5;
  intentResult: any;
  intentResult1: any;
  form = new FormGroup({
    intent: new FormControl(''),
    terms: new FormControl([

    ]),
    citerms: new FormControl([
    ])
  });



  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  disabled: any;
  durationInSeconds = 5;
  get terms() {
    return this.form.get('terms');
  }
  get citerms() {
    return this.form.get('citerms');
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our fruit
    if ((value || '').trim()) {
      this.terms.setValue([...this.terms.value, value.trim()]);
      this.terms.updateValueAndValidity();

    }

    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  remove(term: string): void {
    const index = this.terms.value.indexOf(term);

    this.terms.value.splice(index, 1);
    this.terms.updateValueAndValidity();

  }



  constructor(
    snackBar: MatSnackBar, public fb: FormBuilder,
    private addIntentService: AddIntentService, private dialog: MatDialogRef<AddIntentComponent>) {


  }
  ngOnInit() { }

  addCounterTerm(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our fruit
    if ((value || '').trim()) {
      this.citerms.setValue([...this.citerms.value, value.trim()]);
      this.citerms.updateValueAndValidity();
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  removeCounter(citerm: string): void {
    const index = this.citerms.value.indexOf(citerm);
    this.citerms.value.splice(index, 1);
    this.citerms.updateValueAndValidity();

  }

  onCancel() {
    this.dialog.close();

  }



  onAddIntent() {
    console.log(' hey:: ', this.form.value);
    const intent = this.form.value.intent;
    const citerms = this.form.value.citerms;
    const terms = this.form.value.terms;
    const intentMap = new Object();
    const intentCmap = new Object();
    intentMap[this.form.get('intent').value] = this.form.get('terms').value;
    intentCmap[this.form.get('intent').value] = this.form.get('citerms').value;
    this.intentResult = { intentRelatedToTerms: intentMap };
    this.intentResult1 = { intentRelatedToCounterTerms: intentCmap };
    const resultantIntent = { ...this.intentResult, ...this.intentResult1 };
    console.log('Res::', resultantIntent);
    console.log(this.intentResult);
    console.log(this.intentResult1);
    this.addIntentService.addIntent(resultantIntent).subscribe(res => {
      console.log('success');
      // this.formStatus = false;
      this.dialog.close();
    });
  }
}
