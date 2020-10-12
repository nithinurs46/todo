import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommpletedtaskComponent } from './commpletedtask.component';

describe('CommpletedtaskComponent', () => {
  let component: CommpletedtaskComponent;
  let fixture: ComponentFixture<CommpletedtaskComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommpletedtaskComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommpletedtaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
