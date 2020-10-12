import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TodoSharedModule } from 'src/app/modules/todo.shared.module';
import { RegistrationComponent } from './registration.component';



@NgModule({
  declarations: [RegistrationComponent],
  imports: [
    TodoSharedModule
  ],
  exports: [
    RegistrationComponent
  ]
})
export class RegistrationModule { }
