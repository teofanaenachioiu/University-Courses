import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {AngularFontAwesomeModule} from 'angular-font-awesome';
import { AddProbaComponent } from './home/add-proba.component';
import {ProbaService} from './home/proba.service';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AddProbaComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AngularFontAwesomeModule
  ],
  providers: [ProbaService],
  bootstrap: [AppComponent]
})
export class AppModule { }
