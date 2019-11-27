import { Component, OnInit, Input } from '@angular/core';
import { MatDialog, MatChipInputEvent, MatSnackBar } from '@angular/material';
// import { EditIntentComponent } from '../edit-intent/edit-intent.component';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { ConceptService } from 'src/app/service/concept.service';

// export interface Term {
//   name: string;
// }

@Component({
  selector: 'app-intent-cards',
  templateUrl: './intent-cards.component.html',
  styleUrls: ['./intent-cards.component.css']
})
export class IntentCardsComponent implements OnInit {
  @Input() skills;
  @Input() terms;
  @Input() removable;
  visible = true;
  selectable = true;
  addOnBlur = true;
  deletedSkills = [];
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];

  constructor(private conceptService: ConceptService, private snack: MatSnackBar) { }
  ngOnInit() { }

  public remove(keyword: any): void {
    this.deletedSkills = [];
    this.deletedSkills.push(keyword);
    this.conceptService.deleteConcepts(this.deletedSkills).subscribe(() => {
      this.snack.open('deleted concepts', '', { duration: 1000 });
      // for removing the content of the chips from skills sets
      this.skills = this.skills.filter(item => item !== keyword);
    });
  }
}


