import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SocketService } from 'src/app/services/socket.service';
import { UtilityService } from 'src/app/services/utility.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {


  public _opened: boolean = true;
  public _modeNum: number = 0;
  public _positionNum: number = 0;
  public _dock: boolean = false;
  public _closeOnClickOutside: boolean = false;
  public _closeOnClickBackdrop: boolean = false;
  public _showBackdrop: boolean = false;
  public _animate: boolean = true;
  public _trapFocus: boolean = true;
  public _autoFocus: boolean = true;
  public _keyClose: boolean = false;
  public _autoCollapseHeight: number = null;
  public _autoCollapseWidth: number = null;

  public userId: string;
  public today: number = Date.now();
  logOutUrl: string = environment.baseUrl + "logout";
  constructor(private router: Router, private utilitySvc: UtilityService,
    private socketSvc: SocketService, private http: HttpClient) {
    setInterval(() => { this.today = Date.now() }, 1);
  }

  ngOnInit() {
    this.userId = this.utilitySvc.loggedInUser;
  }

  handleLogout() {
    sessionStorage.removeItem('token');
    this.socketSvc.stop();
    this.socketSvc.disconnect();
    this.http.get(this.logOutUrl).subscribe(response => {
      this.router.navigate(['/logout']);
    })

  }

  public _MODES: Array<string> = ['push'];
  public _POSITIONS: Array<string> = ['left'];

  public _toggleOpened(): void {
    this._opened = !this._opened;
  }

  public _toggleMode(): void {
    this._modeNum++;

    if (this._modeNum === this._MODES.length) {
      this._modeNum = 0;
    }
  }

  public _toggleAutoCollapseHeight(): void {
    this._autoCollapseHeight = this._autoCollapseHeight ? null : 500;
  }

  public _toggleAutoCollapseWidth(): void {
    this._autoCollapseWidth = this._autoCollapseWidth ? null : 500;
  }

  public _togglePosition(): void {
    this._positionNum++;

    if (this._positionNum === this._POSITIONS.length) {
      this._positionNum = 0;
    }
  }

  public _toggleDock(): void {
    this._dock = !this._dock;
  }

  public _toggleCloseOnClickOutside(): void {
    this._closeOnClickOutside = !this._closeOnClickOutside;
  }

  public _toggleCloseOnClickBackdrop(): void {
    this._closeOnClickBackdrop = !this._closeOnClickBackdrop;
  }

  public _toggleShowBackdrop(): void {
    this._showBackdrop = !this._showBackdrop;
  }

  public _toggleAnimate(): void {
    this._animate = !this._animate;
  }

  public _toggleTrapFocus(): void {
    this._trapFocus = !this._trapFocus;
  }

  public _toggleAutoFocus(): void {
    this._autoFocus = !this._autoFocus;
  }

  public _toggleKeyClose(): void {
    this._keyClose = !this._keyClose;
  }

  public _onOpenStart(): void {
    console.info('Sidebar opening');
  }

  public _onOpened(): void {
    console.info('Sidebar opened');
  }

  public _onCloseStart(): void {
    console.info('Sidebar closing');
  }

  public _onClosed(): void {
    console.info('Sidebar closed');
  }

  public _onTransitionEnd(): void {
    console.info('Transition ended');
  }

  public _onBackdropClicked(): void {
    console.info('Backdrop clicked');
  }

  addTask() {
    this.router.navigate(['/addTask']);
  }
}
