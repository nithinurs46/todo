import {Component} from '@angular/core';

import * as SockJS from 'sockjs-client';
import * as StompJs from '@stomp/stompjs';

import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
}  from '@angular/material/snack-bar';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html'
})
export class NotificationsComponent {

  public notifications: string[] = [];

  private client: StompJs.Client;
  notificationUrl: string = environment.baseUrl + "notifications";
  
  message;

  horizontalPosition: MatSnackBarHorizontalPosition = 'end';
  verticalPosition: MatSnackBarVerticalPosition = 'bottom';

  constructor(private _snackBar: MatSnackBar) { }
  
  ngOnInit() {
   }

  connectClicked() {
    if (!this.client || this.client.connected) {
      this.client = new StompJs.Client({
        webSocketFactory: () => new SockJS(this.notificationUrl),
        debug: (msg: string) => console.log(msg)
      });

      this.client.onConnect = () => {

        this.client.subscribe('/user/notification/item', (response) => {
          //const text: string = JSON.parse(response.body).body;
          console.log('Got ' + response.body);
          this.notifications.push(response.body);
          var jsonData = JSON.parse(response.body);
          this.showSnackBar(jsonData.title, jsonData.body);
        });

        console.info('connected!');
      };

      this.client.onStompError = (frame) => {
        console.error(frame.headers['message']);
        console.error('Details:', frame.body);
      };

      this.client.activate();
    }
  }

  disconnectClicked() {
    if (this.client && this.client.connected) {
      this.client.deactivate();
      this.client = null;
      console.info("disconnected :-/");
    }
  }

  startClicked() {
    if (this.client && this.client.connected) {
      this.client.publish({destination: '/todo/start'});
    }
  }

  stopClicked() {
    if (this.client && this.client.connected) {
      this.client.publish({destination: '/todo/stop'});
    }
  }

  showSnackBar(title,data){
    let snackBarRef =  this._snackBar.open(data, title, {
      duration: 30000,
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });

    /*snackBarRef.afterDismissed().subscribe(() => {
      console.log('The snack-bar was dismissed');
    });
    
    
    snackBarRef.onAction().subscribe(() => {
      console.log('The snack-bar action was triggered!');
    });
    
    snackBarRef.dismiss();*/
  }
}
