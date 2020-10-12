import { NgModule } from '@angular/core';
import {
  MatInputModule,
  MatButtonModule,
  MatIconModule,
  MatMenuModule,
  MatToolbarModule,
  MatFormFieldModule,
  MatCardModule,
  MatCheckboxModule,
  MatTabsModule,
  MatNativeDateModule,
  MatSidenavModule,
  MatListModule,
  MatExpansionModule,
  MatDatepickerModule,
  MatDialogModule,
  MatSelectModule, MatTooltipModule, MatSnackBarModule, MatGridListModule,

} from '@angular/material';

const materialModulesArr: any[] = [
  MatInputModule,
  MatFormFieldModule,
  MatButtonModule,
  MatIconModule,
  MatMenuModule,
  MatToolbarModule,
  MatCardModule,
  MatCheckboxModule,
  MatTabsModule,
  MatNativeDateModule, MatSidenavModule, MatListModule, MatExpansionModule,
  MatDatepickerModule, MatDialogModule, MatSelectModule, MatTooltipModule, 
  MatSnackBarModule, MatGridListModule
]
@NgModule({
  imports: [
    materialModulesArr
  ],
  exports: [
    materialModulesArr
  ]
})
export class MaterialModule { }
