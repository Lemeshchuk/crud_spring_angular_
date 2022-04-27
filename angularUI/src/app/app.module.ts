import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {UserManagerComponent} from './user-manager/user-manager.component';

import { AdminPageComponent } from './admin-page/admin-page.component';
import { RecaptchaModule } from 'ng-recaptcha';
import { UsersPageComponent } from './users-page/users-page.component';
import { BirthdayValidatorDirective } from './validator/birthday-validator.directive';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    UserManagerComponent,
    AdminPageComponent,
    UsersPageComponent,
    BirthdayValidatorDirective
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    RecaptchaModule,
    ReactiveFormsModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
