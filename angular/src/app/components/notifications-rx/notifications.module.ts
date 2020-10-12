import { NgModule } from '@angular/core';
import { TodoSharedModule } from 'src/app/modules/todo.shared.module';
import { NavigationRoutingModule } from '../navigation/navigation.routing';
import { NotificationsRxComponent } from './notifications-rx.component';



@NgModule({
  declarations: [NotificationsRxComponent],
  imports: [
    TodoSharedModule,
    NavigationRoutingModule
  ]
})
export class NotificationsModule { }
