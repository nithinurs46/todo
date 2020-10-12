import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  registrationForm: FormGroup;
  response: any;
  loading = false;
  submitted = false;
  message: string = '';

  constructor(private formBuilder: FormBuilder,
    private userSvc: UserService, private router: Router) { }

  get f() { return this.registrationForm.controls; }

  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  registerUser() {
    this.submitted = true;

    if (this.registrationForm.invalid) {
      return;
    }

    this.loading = true;
    this.userSvc.register(this.registrationForm.value)
      .subscribe(
        data => {
          //$('#msgs').text('User created successfully').show();
          this.loading = false;
          this.message = 'User created successfully';
        },
        error => {
          //$('#msgs').text('Error during user creation').show();
          this.loading = false;
          this.message = JSON.parse(error.error).message;
          console.log(error);
        });
  }

  btnLogin(){
    this.router.navigateByUrl('/login');
  }

}