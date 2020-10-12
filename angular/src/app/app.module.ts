import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './interceptors/auth-interceptor';
import { SucessDialogComponent } from './dialogs/sucess-dialog/sucess-dialog.component';
import { ErrorDialogComponent } from './dialogs/error-dialog/error-dialog.component';
import { TodoSharedModule } from './modules/todo.shared.module';
import { RegistrationModule } from './components/registration/registration.module';
import { AppRoutingModule } from './app-routing.module';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavigationModule } from './components/navigation/navigation.module';
import { LoginLayoutComponent } from './components/layouts/login-layout/login-layout.component';
import { ConfirmDialogComponent } from './dialogs/confirm-dialog/confirm-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    LoginLayoutComponent,
    SucessDialogComponent,
    ErrorDialogComponent,
    ConfirmDialogComponent,
  ],
  imports: [
    TodoSharedModule,
    RegistrationModule,
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    NavigationModule
  ],
  exports: [
    SucessDialogComponent,
    ErrorDialogComponent,
    ConfirmDialogComponent
  ],
  entryComponents: [
    SucessDialogComponent,
    ErrorDialogComponent,
    ConfirmDialogComponent
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
