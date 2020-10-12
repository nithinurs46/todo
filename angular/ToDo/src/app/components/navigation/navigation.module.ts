import { NgModule } from '@angular/core';
import { TodoSharedModule } from 'src/app/modules/todo.shared.module';
import { CompletedtaskModule } from '../commpletedtask/completedtask.module';
import { CreatetaskModule } from '../createtask/createtask.module';
import { HomeModule } from '../home/home.module';
import { HomeLayoutComponent } from '../layouts/home-layout/home-layout.component';
import { ManagetaskModule } from '../managetask/managetask.module';
import { NotificationsModule } from '../notifications-rx/notifications.module';
import { UserModule } from '../users/user.module';
import { NavigationComponent } from './navigation.component';
import { NavigationRoutingModule } from './navigation.routing';



@NgModule({
  declarations: [HomeLayoutComponent, NavigationComponent
  ],
  imports: [
    TodoSharedModule,
    NavigationRoutingModule,
    CreatetaskModule,
    ManagetaskModule,
    CompletedtaskModule,
    NotificationsModule,
    HomeModule,
    UserModule
  ],
  exports: [

  ]
})
export class NavigationModule { }
