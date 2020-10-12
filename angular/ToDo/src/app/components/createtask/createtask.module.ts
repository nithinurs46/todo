import { NgModule } from '@angular/core';
import { TodoSharedModule } from 'src/app/modules/todo.shared.module';
import { NavigationRoutingModule } from '../navigation/navigation.routing';
import { CreatetaskComponent } from './createtask.component';



@NgModule({
  declarations: [CreatetaskComponent],
  imports: [
    TodoSharedModule,
    NavigationRoutingModule
  ],
  exports: [

  ]
})
export class CreatetaskModule { }
