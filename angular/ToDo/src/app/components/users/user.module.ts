import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TodoSharedModule } from 'src/app/modules/todo.shared.module';
import { EditUserComponent } from './edit-user/edit-user.component';
import { NavigationRoutingModule } from '../navigation/navigation.routing';
import { UsersComponent } from './users.component';



@NgModule({
  declarations: [UsersComponent, EditUserComponent],
  imports: [
    TodoSharedModule,
    NavigationRoutingModule,
    NgbModule,
  ]
})
export class UserModule { }
