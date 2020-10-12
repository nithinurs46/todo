import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.prod';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Task } from '../models/task';

@Injectable({
  providedIn: 'root'
})
export class TaskManagerService {
  getTaskURL: string = environment.baseUrl + "getTasks";
  updateTaskURL: string = environment.baseUrl + "updateCompletion";
  createTaskURL: string = environment.baseUrl + "createTask";
  deleteTaskURL: string = environment.baseUrl + "deleteTask";
  
  constructor(private http: HttpClient, private router: Router) { }

  getTasksList(pageNo): Observable<any> {
    return this.http.get<any>(`${this.getTaskURL}`+`?pageNo=`+pageNo);
  }

  updateTask(name: string, status: string): Observable<any> {
    return this.http.post<any>(`${this.updateTaskURL}`, {
      // request body parameters should match the TaskForm(java) obj name
      taskName: name,
      completed: status
    });
  }
  
  saveNewTask(task:Task) : Observable<any>{
    return this.http.post(this.createTaskURL, task);
  }

  removeTask(name: string): Observable<any> {
    return this.http.post<any>(`${this.deleteTaskURL}`, {
      // request body parameters should match the TaskForm(java) obj name
      taskName: name,
    });
      
  }

}
