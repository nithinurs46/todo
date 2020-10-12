import { NgModule } from '@angular/core';
import { CommpletedtaskComponent } from './commpletedtask.component';
import { TodoSharedModule } from 'src/app/modules/todo.shared.module';
import { NavigationRoutingModule } from '../navigation/navigation.routing';



@NgModule({
  declarations: [CommpletedtaskComponent],
  imports: [
    TodoSharedModule,
    NavigationRoutingModule
  ],
  exports:[
  ]
})
export class CompletedtaskModule { }
