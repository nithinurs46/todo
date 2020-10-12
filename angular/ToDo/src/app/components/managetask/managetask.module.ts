import { NgModule } from '@angular/core';
import { TableModule } from 'primeng/table';
import { TodoSharedModule } from 'src/app/modules/todo.shared.module';
import { NavigationRoutingModule } from '../navigation/navigation.routing';
import { ManagetaskComponent } from './managetask.component';



@NgModule({
  declarations: [ManagetaskComponent],
  imports: [
    TodoSharedModule,
    TableModule,
    NavigationRoutingModule
  ],
  exports:[
    ManagetaskComponent
  ]
})
export class ManagetaskModule { }
