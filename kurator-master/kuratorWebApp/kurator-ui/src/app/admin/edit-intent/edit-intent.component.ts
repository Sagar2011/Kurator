// import { Component, OnInit } from '@angular/core';
// import { MatChipInputEvent } from '@angular/material';
// import { COMMA, ENTER } from '@angular/cdk/keycodes';
// import { FormGroup, FormBuilder, FormControl } from '@angular/forms';

// export interface IndicatorTerms {
//   name: string;
// }
// export interface CounterIndicatorTerms {
//   name: string;
// }


// @Component({
//   selector: 'app-edit-intent',
//   templateUrl: './edit-intent.component.html',
//   styleUrls: ['./edit-intent.component.css']
// })
// export class EditIntentComponent implements OnInit {
//   // name = new FormControl('');
//   editIntentForm: FormGroup;
//   visible = true;
//   selectable = true;
//   removable = true;
//   addOnBlur = true;
//   intent = 'Knowledge';
//   readonly separatorKeysCodes: number[] = [ENTER, COMMA];
//   iterms: IndicatorTerms[] = [
//     { name: 'Introduce' },
//     { name: 'Start' },
//     { name: 'Define' },
//     { name: 'What' },
//     { name: 'Summary' }
//   ];
//   // citerms: CounterIndicatorTerms[] = [
//   //   {name: 'Code-Example'},
//   //   {name: 'Explanation'},
//   //   {name: 'Comparison'},
//   // ];

//   add(event: MatChipInputEvent): void {
//     const input = event.input;
//     const value = event.value;

//     // Add our indicator terms
//     if ((value || '').trim()) {
//       this.iterms.push({ name: value.trim() });
//     }

//     // Reset the input value
//     if (input) {
//       input.value = '';
//     }
//   }

//   remove(iterm: IndicatorTerms): void {
//     const index = this.iterms.indexOf(iterm);

//     if (index >= 0) {
//       this.iterms.splice(index, 1);
//     }
//   }

//   constructor(private formBuilder: FormBuilder) { }

//   ngOnInit() {
//     this.editIntentForm = new FormGroup({
//       name: new FormControl(''),

//     });
//   }

//   // addCi(event: MatChipInputEvent): void {
//   //   const input = event.input;
//   //   const value = event.value;

//   //   // Add our indicator terms
//   //   if ((value || '').trim()) {
//   //     this.citerms.push({name: value.trim()});
//   //   }

//   //   // Reset the input value
//   //   if (input) {
//   //     input.value = '';
//   //   }
//   // }

//   // removeCi(citerm: IndicatorTerms): void {
//   //   const index = this.citerms.indexOf(citerm);

//   //   if (index >= 0) {
//   //     this.citerms.splice(index, 1);
//   //   }
//   // }


// }


