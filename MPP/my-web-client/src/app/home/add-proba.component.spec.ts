import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddProbaComponent } from './add-proba.component';

describe('AddProbaComponent', () => {
  let component: AddProbaComponent;
  let fixture: ComponentFixture<AddProbaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddProbaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddProbaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
