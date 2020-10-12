import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { UtilityService } from 'src/app/services/utility.service';
import { SocketService } from 'src/app/services/socket.service';
//import * as $ from 'jquery';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  validateLoginUrl: string = environment.baseUrl + "authenticate";
  registerUserUrl: string = environment.baseUrl + "registerUser";
  response: any;
  loginForm: FormGroup;
  loading = false;
  message: string = '';
  private notifications: string[] = [];

  constructor(private fb: FormBuilder, private http: HttpClient, private router: Router,
    private utilitySvc: UtilityService, private socketSvc: SocketService) {
  }


  ngOnInit() {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  doLogin() {
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;

    const username = this.loginForm.get('username').value;
    const password = this.loginForm.get('password').value;

    let result = this.http.post(this.validateLoginUrl, {
      username: username,
      password: password
    }, { responseType: 'text' as 'json' }).subscribe(
      response => {
        if (response != null) {
          sessionStorage.setItem(
            'token', response.toString()
          );
          this.loading = false;
          this.utilitySvc.loggedInUser = username;

          this.socketSvc.connect(this.notifications);
          this.socketSvc.start();

          this.router.navigate(['dashboard/home']);
        } else {
          this.loading = false;
          //$('#msgs').text('Authentication failed').show();
          this.message = 'Authentication failed'
        }
      },
      error => {
        //$('#msgs').text('Unable to login').show();
        this.message = JSON.parse(error.error).message
        this.loading = false;
        console.log(error);
      });
  }

  btnRegister(){
    this.router.navigateByUrl('/register');
  }
}

