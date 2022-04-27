import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {UserManagerComponent} from './user-manager/user-manager.component';
import {LoginComponent} from './login/login.component';
import {AdminPageComponent} from "./admin-page/admin-page.component";
import {UsersPageComponent} from "./users-page/users-page.component";

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'userManager', component: UserManagerComponent},
  {path: 'admin', component: AdminPageComponent},
  {path: 'userPage', component: UsersPageComponent},
  {path: '', redirectTo: 'login', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
