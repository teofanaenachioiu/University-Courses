import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RoomPage } from './room.page';

const routes: Routes = [
  {
    path: '',
    component: RoomPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RoomPageRoutingModule {}
