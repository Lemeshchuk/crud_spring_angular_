import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from "../services/token-storage.service";
import {Router} from "@angular/router";
import {User} from "../model/user";

@Component({
  selector: 'app-users-page',
  templateUrl: './users-page.component.html',
  styleUrls: ['./users-page.component.css']
})
export class UsersPageComponent implements OnInit {

  currentUser: User = this.tokenStorageService.getUser();

  constructor(private tokenStorageService: TokenStorageService,
              private router: Router) { }

  ngOnInit(): void {
  }

  logout(): void {
    this.tokenStorageService.signOut();
    this.changeRoute();
  }

  changeRoute() {
    this.router.navigate(['/login']);
  }
}
