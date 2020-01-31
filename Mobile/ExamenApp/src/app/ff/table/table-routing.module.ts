import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TablePage } from './table.page';

const routes: Routes = [
  {
    path: '',
    component: TablePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TablePageRoutingModule {}
