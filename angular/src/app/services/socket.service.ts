import { Injectable } from '@angular/core';
import {RxStomp} from "@stomp/rx-stomp";
import * as SockJS from 'sockjs-client';
import {map} from "rxjs/operators";
import { environment } from 'src/environments/environment';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SocketService {
  private client: RxStomp;
  notificationUrl: string = environment.baseUrl + "notifications";
  horizontalPosition: MatSnackBarHorizontalPosition = 'end';
  verticalPosition: MatSnackBarVerticalPosition = 'bottom';

  constructor(private _snackBar: MatSnackBar) { }
  
  private eventCallback = new Subject<string>(); // Source
  eventCallback$ = this.eventCallback.asObservable(); // Stream 

  public connect(notifications) {
    if (!this.client || this.client.connected) {
      this.client = new RxStomp();
      this.client.configure({
        webSocketFactory: () => new SockJS(this.notificationUrl),
        debug: (msg: string) => console.log(msg)
      });
      this.client.activate();

      this.watchForNotifications(notifications);
      
    }
  }

  private watchForNotifications(notifications) {
    this.client.watch('/user/notification/item')
      .pipe(
        map((response) => {
          const jsonData = JSON.parse(response.body);
          console.log('Got ' + jsonData.body);
          return jsonData;
        }))
      .subscribe(jsonData => {
        notifications.push(jsonData.body)
        this.eventCallback.next(notifications);
        this.showSnackBar(jsonData.title, jsonData.body);
        console.info('connected!');
      });
  }

  public disconnect() {
    if (this.client && this.client.connected) {
      this.client.deactivate();
      this.client = null;
      console.info("disconnected :-/");
    }
  }

  public start() {
    if (this.client && this.client.connected) {
      this.client.publish({destination: '/todo/start'});
    }
  }

  public stop() {
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
