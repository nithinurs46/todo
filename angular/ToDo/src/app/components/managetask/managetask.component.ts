import { Component, OnInit, SimpleChanges } from '@angular/core';
import { MatDialog } from '@angular/material';
import { LazyLoadEvent } from 'primeng/api';
import { ConfirmDialogComponent } from 'src/app/dialogs/confirm-dialog/confirm-dialog.component';
import { Task } from 'src/app/models/task';
import { TaskManagerService } from 'src/app/services/task-manager.service';

@Component({
  selector: 'app-managetask',
  templateUrl: './managetask.component.html',
  styleUrls: ['./managetask.component.css'],

})


export class ManagetaskComponent implements OnInit {

  tasks: Task[];
  selectedTask: Task;
  constructor(private taskMgrSvc: TaskManagerService, private dialog: MatDialog) { }
  cols: any[];
  loading: boolean = false;
  totalRecords: number;
  recPerPage: number;


  ngOnInit(): void {
    //this.populateTasks();
    this.cols = [
      { field: 'taskName', header: 'Task Name' },
      { field: 'taskDate', header: 'Date' },
      { field: 'description', header: 'Description' },
    ];
  }

  ngOnChanges(changes: SimpleChanges) {
    this.cols = [
      { field: 'taskName', header: 'Task Name' },
      { field: 'taskDate', header: 'Date' },
      { field: 'description', header: 'Description' },
    ];
    this.loadTasks(null);
  }


  populateTasks() {
    this.taskMgrSvc.getTasksList(1).subscribe((response) => {
      //this.tasks = response;
      this.mapDisplayResponse(response);
    });

  }

  markCompletion(rowdata) {
    let status = rowdata.completed;
    if ('true' == rowdata.completed) {
      status = 'false';
    } else {
      status = 'true';
    }
    this.taskMgrSvc.updateTask(rowdata.taskName, status).subscribe((response) => {
      // this.populateTasks();
      //this.tasks = response;
      this.mapDisplayResponse(response);
    });
  }

  loadTasks(event: LazyLoadEvent) {
    this.loading = true;
    //in a real application, make a remote request to load data using state metadata from event
    //event.first = First row offset
    //event.rows = Number of rows per page
    //event.sortField = Field name to sort with
    //event.sortOrder = Sort order as number, 1 for asc and -1 for dec
    //filters: FilterMetadata object having field as key and filter value, filter matchMode as value

    //imitate db connection over a network
    let lastIndex: number = 0;
    this.recPerPage = 10;
    let currPageNo: number = 1;
    if (event != null) {
      lastIndex = event.first;
      this.recPerPage = event.rows;
      currPageNo = lastIndex / this.recPerPage + 1;
    }
    this.taskMgrSvc.getTasksList(currPageNo).subscribe((response) => {
      //this.tasks = response;
      this.mapDisplayResponse(response);

    });

  }


  deleteTask(rowdata) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        message: 'Confirm Task Delete ?',
        buttonText: {
          ok: 'Yes',
          cancel: 'No'
        }
      }
    });

    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        this.taskMgrSvc.removeTask(rowdata.taskName).subscribe((response) => {
          this.mapDisplayResponse(response);
        });
      }
    });
  }
  private mapDisplayResponse(response) {
    this.tasks = response.recList;
    this.totalRecords = response.recSize;
    this.loading = false;
  }

}
