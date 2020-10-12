import { NgModule } from '@angular/core';
import { TodoSharedModule } from 'src/app/modules/todo.shared.module';
import { HomeComponent } from './home.component';
import { NavigationRoutingModule } from '../navigation/navigation.routing';
import { AccordionModule, ChartModule, PanelModule, TabViewModule } from 'primeng';



@NgModule({
  declarations: [HomeComponent],
  imports: [
    TodoSharedModule,
    NavigationRoutingModule,
    ChartModule,
    AccordionModule,
    PanelModule,
    TabViewModule
  ]
})
export class HomeModule { }
