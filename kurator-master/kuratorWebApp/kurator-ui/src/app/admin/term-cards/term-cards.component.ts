import { Component, OnInit, Input } from '@angular/core';
import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { MatChipInputEvent } from '@angular/material';

export interface Fruit {
  name: string;
}

@Component({
  selector: 'app-term-cards',
  templateUrl: './term-cards.component.html',
  styleUrls: ['./term-cards.component.css']
})
export class TermCardsComponent implements OnInit {
  @Input() intent: any;
  @Input() terms: any;
  @Input() counterTerms: any;
  @Input() colln: any;
  visible = true;
  selectable = true;
  removable = true;
  removable1 = true;
  addOnBlur = true;
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  fruits: Fruit[] = [
    { name: 'Java' },
    { name: 'Spring' },
    { name: 'HTML' },
    // {name: 'API'},
    // {name: 'Angular'},
    // {name: 'React'},
  ];

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our fruit
    if ((value || '').trim()) {
      this.fruits.push({ name: value.trim() });
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  removeiterm(iterm: any): void {
    const index = this.fruits.indexOf(iterm);

    if (index >= 0) {
      this.fruits.splice(index, 1);
    }
  }
  removeciterm(citerm: any): void {
    const index = this.fruits.indexOf(citerm);

    if (index >= 0) {
      this.fruits.splice(index, 1);
    }
  }

  onEditIndicatorTerm(): any {
    this.removable = true;
  }
  onEditCounterIndicatorTerm(): any {
    this.removable1 = true;
  }
  onCancelIntent() {

  }
  openDialog() {

  }

  constructor() { }

  ngOnInit() {
    console.log('counterTerms::', this.counterTerms);
  }

}
