import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UtilityService {

  private _loggedInUser: string;
  public get loggedInUser(): string {
    return this._loggedInUser;
  }
  public set loggedInUser(value: string) {
    this._loggedInUser = value;
  }

  constructor() { }
}
