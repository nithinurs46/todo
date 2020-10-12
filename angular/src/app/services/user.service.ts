import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { User } from '../models/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  validateLoginUrl: string = environment.baseUrl + "authenticate";
  registerUserUrl: string = environment.baseUrl + "registerUser";
  getAllUsersUrl: string = environment.baseUrl + "getAllUsers";
  updateUserUrl: string = environment.baseUrl + "updateUser";
  deleteUserUrl: string = environment.baseUrl + "deleteUser";

  constructor(private http: HttpClient) { }

  register(user: User) {
    return this.http.post(this.registerUserUrl, user);
  }

  public getAllUsers(pageNo: number) {
    return this.http.get<any>(`${this.getAllUsersUrl}` + `?pageNo=` + pageNo);
  }

  saveUpdatedUser(user: User): Observable<any> {
    return this.http.post(this.updateUserUrl, user);
  }

  removeUser(name: string): Observable<any> {
    return this.http.post<any>(`${this.deleteUserUrl}`, {
      username: name,
    });

  }

  private _editUser: User;
  public get userForEdit(): User {
    return this._editUser;
  }
  public set userForEdit(value: User) {
    this._editUser = value;
  }

}
