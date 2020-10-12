import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CommpletedtaskComponent } from '../commpletedtask/commpletedtask.component';
import { CreatetaskComponent } from '../createtask/createtask.component';
import { HomeComponent } from '../home/home.component';
import { HomeLayoutComponent } from '../layouts/home-layout/home-layout.component';
import { ManagetaskComponent } from '../managetask/managetask.component';
import { NotificationsRxComponent } from '../notifications-rx/notifications-rx.component';
import { EditUserComponent } from '../users/edit-user/edit-user.component';
import { UsersComponent } from '../users/users.component';

const routes: Routes = [
    {
        path: 'dashboard', component: HomeLayoutComponent,
        children: [
            { path: 'home', component: HomeComponent },
            { path: 'users', component: UsersComponent },
            { path: 'addUser', component: EditUserComponent },
            { path: 'editUser', component: EditUserComponent },
            { path: 'addTask', component: CreatetaskComponent },
            { path: 'manageTask', component: ManagetaskComponent },
            { path: 'completedTask', component: CommpletedtaskComponent, },
            { path: 'manageNotification', component: NotificationsRxComponent, },
        ]
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})

export class NavigationRoutingModule { }
