import { Component } from '@angular/core';
import { SocketService } from 'src/app/services/socket.service';

@Component({
  selector: 'app-notifications-rx',
  templateUrl: './notifications-rx.component.html'
})
export class NotificationsRxComponent {

  public notifications: string[] = [];
  private testA: string;

  constructor(private socketSvc: SocketService) {

  }

  connectClicked() {
    this.socketSvc.connect(this.notifications);
  }


  disconnectClicked() {
    this.socketSvc.disconnect();
  }

  startClicked() {
    this.socketSvc.start();
  }

  stopClicked() {
    this.socketSvc.stop();
  }

}
