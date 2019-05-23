import {Component, OnDestroy, OnInit} from '@angular/core';
import {Proba} from './Proba';
import {HttpErrorResponse} from '@angular/common/http';
import {ProbaService} from './proba.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  subscriptions = [];
  probe: Proba[];
  error: HttpErrorResponse;

  constructor(private probeService: ProbaService, private router: Router) {
    console.log('constructor home');
  }

  ngOnInit() {
    console.log('in init');
    this.loadProbe();
  }

  loadProbe(): void {
    console.log('load probe');
    this.subscriptions.push(this.probeService.getAll().subscribe(items => {
        this.probe = items;
      }, error => {
        this.error = error;
        console.log(this.error);
      }
    ));
  }

  retry() {
    this.loadProbe();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  deleteProba(idproba: any) {
    this.subscriptions.push(this.probeService.delete(idproba)
      .subscribe(() => {
        alert('proba stearsa');
        this.retry();
        this.router.navigate(['']);
      }));

  }

}

