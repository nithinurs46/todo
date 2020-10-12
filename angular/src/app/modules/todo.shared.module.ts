import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from './material.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';

const sharedModulesArr: any[] = [
  CommonModule,
  MaterialModule,
  HttpClientModule,
  FormsModule,
  ReactiveFormsModule,
  FlexLayoutModule
];

@NgModule({
  declarations: [],
  imports: [
    sharedModulesArr
  ],
  exports: [
    sharedModulesArr
  ]
})
export class TodoSharedModule { }
