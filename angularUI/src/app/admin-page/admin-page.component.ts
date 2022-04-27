import {Component, OnInit} from '@angular/core';
import {User} from "../model/user";
import {UserService} from "../services/user.service";
import {TokenStorageService} from "../services/token-storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})

export class AdminPageComponent implements OnInit {

  errorMessage;
  users: User[];
  currentUser: User = this.tokenStorageService.getUser();

  constructor(private userService: UserService, private tokenStorageService: TokenStorageService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getAllUsers();
  }

  private getAllUsers() {
    this.userService.getAllUsers().subscribe(data => {
      this.users = data.map(user => {
        user.age = new Date().getFullYear() - new Date(user.birthday).getFullYear();
        return user
      })
    }, err => {
      if (err.status === 401 || err.status === 403) {
        this.router.navigateByUrl('/login');
        this.tokenStorageService.signOut();
        this.errorMessage = err.error.message;
      }
    });
  }

  logout(): void {
    this.tokenStorageService.signOut();
    this.changeRoute();
  }

  changeRoute() {
    this.router.navigate(['/login']);
  }

  changeRouteToEditUser(user: User) {
    window.sessionStorage.setItem("idEditUser", JSON.stringify(user));
    this.router.navigate(['/userManager']);
  }

  changeRouteToAddUser() {
    sessionStorage.removeItem('idEditUser');
    this.router.navigate(['/userManager']);
  }

  deleteUser(user: User) {
    if (confirm("You want to delete user with username  " + user.username + "?")) {
      this.userService.deleteUsers(user.id).subscribe(data => {
        console.log(data);
        this.getAllUsers();
      })
    }
  }
}
