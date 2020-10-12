import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {
  user: User;
  userForm: FormGroup;
  userCreation: boolean = true;
  message: string = '';
  constructor(private formBuilder: FormBuilder, private router: Router, private userSvc: UserService) {
  }

  ngOnInit(): void {
    this.initializeForm();
    var index = "/dashboard/editUser".localeCompare(this.router.url);
    if (index == 0) {
      this.userCreation = false;
      this.fetchUser();
    }
  }

  saveUser() {
    if (this.userCreation) {
      this.userSvc.register(this.userForm.value)
        .subscribe(
          data => {
            this.message = 'User created successfully';
          },
          error => {
            this.message = JSON.parse(error.error).message;
            console.log(error);
          });
    } else {
      this.userSvc.saveUpdatedUser(this.userForm.value).subscribe(
        response => {
          this.user = response;
          this.message = 'User updated successfully';
        });
    }

  }

  initializeForm() {
    this.userForm = this.formBuilder.group({
      username: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  fetchUser() {
    /*this.userForm = this.formBuilder.group({
      username: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
    this.userForm.setValue({
      username: this.userSvc.userForEdit.username,
      password: this.userSvc.userForEdit.password,
      firstName: this.userSvc.userForEdit.firstName,
      lastName: this.userSvc.userForEdit.lastName,
   });
  }*/
    this.userForm.patchValue(this.userSvc.userForEdit, { onlySelf: true });
  }

  btnBack() {
    this.router.navigateByUrl('/dashboard/users');
  }



}
