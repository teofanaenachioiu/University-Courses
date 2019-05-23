import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {Route, RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {AddProbaComponent} from './home/add-proba.component';

const routes: Routes = [
  {path: '', redirectTo: 'concurs/probe', pathMatch: 'full'},
  {path: 'concurs/probe', component: HomeComponent },
  {path: 'concurs/probe/add', component: AddProbaComponent}
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
