import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material';
import { HttpErrorResponse } from '@angular/common/http';
import { ErrorDialogComponent } from './error-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService {
  public errorMessage: string = '';
  public dialogConfig;


  constructor(private router: Router, private dialog: MatDialog) { }

  public handleError(error: HttpErrorResponse) {
    /*if(error.status === 500){
      this.handle500Error(error);
    }
    else if(error.status === 404){
      this.handle404Error(error)
    }
    else{*/
    this.handleOtherError(error);
    //}
  }

  private handle500Error(error: HttpErrorResponse) {
    this.createErrorMessage(error);
    this.router.navigate(['/500']);
  }

  private handle404Error(error: HttpErrorResponse) {
    this.createErrorMessage(error);
    this.router.navigate(['/404']);
  }

  private handleOtherError(error: HttpErrorResponse) {
    this.createErrorMessage(error);
    this.dialogConfig.data = { 'errorMessage': this.errorMessage };
    this.dialog.open(ErrorDialogComponent, this.dialogConfig);
  }
  private createErrorMessage(error: HttpErrorResponse) {
    this.errorMessage = error.error.message;//error.error ? error.error : error.statusText;
  }
}
