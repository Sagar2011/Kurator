import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  MatButtonModule, MatCardModule, MatDialogModule, MatInputModule, MatTableModule,
  MatToolbarModule, MatMenuModule, MatIconModule, MatProgressSpinnerModule, MatRadioModule, MatOptionModule,
  MatSelectModule, MatGridListModule, MatFormFieldModule, MatSlideToggleModule, MatTabsModule, MatSidenavModule,
  MatListModule, MatChipsModule, MatButtonToggleModule, MatCheckboxModule, MatDividerModule, MatBottomSheetModule
  , MatNativeDateModule} from '@angular/material';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import {MatDatepickerModule} from '@angular/material/datepicker';


@NgModule({
  declarations: [],
  imports: [
    CommonModule, MatCheckboxModule,
    MatButtonModule, MatCardModule, MatDialogModule, MatInputModule, MatTableModule, MatListModule,
    MatToolbarModule, MatMenuModule, MatIconModule, MatProgressSpinnerModule, MatRadioModule, MatOptionModule,
    MatSelectModule, MatGridListModule, MatFormFieldModule, MatSlideToggleModule, MatTabsModule, MatSidenavModule, MatChipsModule,
    MatSnackBarModule, MatTooltipModule, MatExpansionModule, MatButtonToggleModule, MatCheckboxModule,
    MatAutocompleteModule, MatDividerModule, MatBottomSheetModule, MatDatepickerModule, MatNativeDateModule
  ],
  exports: [MatButtonModule, MatCardModule, MatDialogModule, MatInputModule, MatTableModule, MatListModule,
    MatToolbarModule, MatMenuModule, MatIconModule, MatProgressSpinnerModule, MatRadioModule, MatOptionModule,
    MatSelectModule, MatGridListModule, MatFormFieldModule, MatSlideToggleModule, MatTabsModule, MatSidenavModule, MatChipsModule,
    MatSnackBarModule, MatTooltipModule, MatExpansionModule, MatButtonToggleModule, MatCheckboxModule,
    MatAutocompleteModule, MatDividerModule, MatBottomSheetModule, MatDatepickerModule, MatNativeDateModule]
})
export class MaterialModule { }
