import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { ConfirmDialogComponent } from 'src/app/dialogs/confirm-dialog/confirm-dialog.component';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  userData: any;
  totalRecords: number;
  page: number = 1;
  pageSize: number = 5;

  constructor(private userSvc: UserService, private router: Router, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getUserData();
  }

  loadPage(event) {
    this.getUserData();
  }
  getUserData() {
    this.userSvc.getAllUsers(this.page).subscribe((response) => {
      this.mapDisplayResponse(response);
    });
  }

  editUser(user) {
    this.userSvc.userForEdit = user;
    this.router.navigate(['/dashboard/editUser']);
  }

  userCreateForm() {
    this.router.navigate(['/dashboard/addUser']);
  }

  deleteUsers(user) {
    this.userSvc.removeUser(user.username).subscribe(
      response => {
        this.router.navigate(['/dashboard/users']);
      });
  }

  deleteUser(user) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        message: 'Confirm User Delete ?',
        buttonText: {
          ok: 'Yes',
          cancel: 'No'
        }
      }
    });

    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        this.userSvc.removeUser(user.username).subscribe(
          response => {
            this.mapDisplayResponse(response);
          });
      }
    });
  }

  private mapDisplayResponse(response) {
    this.userData = response.recList;
    this.totalRecords = response.recSize;
  }
}
