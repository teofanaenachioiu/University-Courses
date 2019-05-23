import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Proba} from './Proba';
import {ProbaService} from './proba.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-add-proba',
  templateUrl: './add-proba.component.html',
  styleUrls: ['./add-proba.component.css']
})
export class AddProbaComponent implements OnInit, OnDestroy {
  @Input() proba: Proba;
  private subscriptions = [];
  private error: Error;

  constructor(private probaService: ProbaService, private router: Router) {
  }

  ngOnInit() {
    this.proba = new Proba(null, null, null);
  }

  addButtonClicked($event) {
    this.subscriptions.push(this.probaService.save(this.proba).subscribe(
      item => {
        this.proba = item;
        alert('saved');
      },
      error => {
        this.error = error;
        console.log(error);
      }));
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subcription => subcription.unsubscribe());
  }

  back(): void {
    this.router.navigate(['/concurs/probe']);
  }
}
