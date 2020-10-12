import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatDialog, DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { APP_DATE_FORMATS, Dateformatter } from 'src/app/utility/dateformatter';
import { environment } from '../../../environments/environment';
import { ErrorHandlerService } from 'src/app/dialogs/error-dialog/error-handler.service';
import { TaskManagerService } from 'src/app/services/task-manager.service';
import { SucessDialogComponent } from 'src/app/dialogs/sucess-dialog/sucess-dialog.component';


@Component({
  selector: 'app-createtask',
  templateUrl: './createtask.component.html',
  styleUrls: ['./createtask.component.css'],
  providers: [
    { provide: DateAdapter, useClass: Dateformatter },
    { provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS }
  ]
})

export class CreatetaskComponent implements OnInit {

  createTaskURL: string = environment.baseUrl + "createTask";
  response: any;
  public newTaskForm: FormGroup;
  private dialogConfig;

  constructor(private router: Router, private dialog: MatDialog,
    private errorService: ErrorHandlerService, private taskMgrSvc: TaskManagerService) { }

  ngOnInit() {
    let regexPatterns = {
      hours: "([01]?[0-9]|2[0-3])",
      minutes: "[0-5][0-9]?"
    };
    this.newTaskForm = new FormGroup({
      taskName: new FormControl('', [Validators.required, Validators.maxLength(60)]),
      taskDate: new FormControl(new Date()),
      description: new FormControl('', [Validators.required, Validators.maxLength(100)]),
      hh: new FormControl('', Validators.pattern(regexPatterns.hours)),
      mm: new FormControl('', Validators.pattern(regexPatterns.minutes)),

    });

    this.dialogConfig = {
      height: '200px',
      width: '400px',
      disableClose: true,
      data: {}
    }
  }

  public hasError = (controlName: string, errorName: string) => {
    return this.newTaskForm.controls[controlName].hasError(errorName);
  }

  public onCancel = () => {
    //this.location.back();
  }

  public createTask = (newTaskFormValue) => {
    if (this.newTaskForm.valid) {
      try {
        this.taskMgrSvc.saveNewTask(newTaskFormValue)
          .subscribe((response) => {
            this.response = response;
            if (this.response) {
              let dialogRef = this.dialog.open(SucessDialogComponent, this.dialogConfig);
              dialogRef.afterClosed()
                .subscribe(result => {
                  this.router.navigate(['/dashboard/manageTask']);
                });
            } else {
              this.errorService.dialogConfig = { ...this.dialogConfig };
              this.errorService.handleError(null);
            }
          },
            error => {
              this.errorService.dialogConfig = { ...this.dialogConfig };
              this.errorService.handleError(error);
            }
          )

      } catch (err) {
        console.log("error");
      }
    }
  }
}
