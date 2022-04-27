import {Component, OnInit} from '@angular/core';
import {AuthService} from '../services/auth.service';
import {Router} from "@angular/router";
import {TokenStorageService} from "../services/token-storage.service";
import {User} from "../model/user";
import {UserService} from "../services/user.service";
import {Role} from "../model/role";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './user-manager.component.html',
  styleUrls: ['./user-manager.component.css']
})
export class UserManagerComponent implements OnInit {

  isRegisterSuccessful = false;
  isRegisterSignUpFailed = false;
  errorMessage;
  errorReason;
  isAddedUser = false;
  idEditUser: string;
  editUser = false;

  user: User = new User();
  roles: Role[];
  currentUser: User = this.tokenStorage.getUser();

  form = new FormGroup({
    role: new FormControl('', Validators.required)
  });

  constructor(private authService: AuthService, private router: Router, private tokenStorage: TokenStorageService,
              private userService: UserService) {
  }

  ngOnInit(): void {

    this.idEditUser = sessionStorage.getItem("idEditUser");

    if (this.idEditUser != null) {
      this.editUser = true;
      this.user = JSON.parse(sessionStorage.getItem("idEditUser"));
    } else {
      this.user.role = new Role();
    }

    if (this.user) {
      this.user.confirmPassword = this.user.password;
    } else {
      this.user = new User();
    }

    this.authService.getAllRoles().subscribe(
      data => {
        this.roles = data.body;
      },
      err => {
        this.errorMessage = err.error.message;
        this.isRegisterSignUpFailed = true;
      }
    );

    if (this.tokenStorage.getToken() != null) {
      this.isAddedUser = true;
    }
  }

  changeRole(e) {
    console.log(e.target.value);
    this.user.role.name = e.target.value;
  }

  onSubmit(): void {
    if (this.isAddedUser && this.idEditUser == null) {
      this.userService.createUser(this.user).subscribe(data => {
          console.log(data);
          this.forward()
        },
        err => {
          this.errorMessage = err.error.message;
          this.errorReason = "Create failed - Username or Email is already exist!"
        }
      );
    } else if (this.idEditUser != null) {
      this.userService.editUser(this.user).subscribe(
        data => {
          console.log(data);
          this.forward()
        },
        err => {
          this.errorMessage = err.error.message;
          this.errorReason = "Update failed"
        }
      );
    } else {
      this.authService.register(this.user).subscribe(
        data => {
          console.log(data);
          this.isRegisterSuccessful = true;
          this.isRegisterSignUpFailed = false;
          this.forward()
        },
        err => {
          this.errorMessage = err.error.message;
          this.errorReason = "Registration failed"
        }
      );
    }
  }

  public resolved(captchaResponse: string) {
    window.sessionStorage.setItem("captchaResponse", captchaResponse);
  }

  forward(): void {
    if (this.tokenStorage.getToken() != null) {
      this.changeRouteToAdmin();
    } else {
      this.changeRouteToLogin();
    }
  }

  changeRouteToLogin() {
    this.router.navigate(['/login']);
  }

  changeRouteToAdmin() {
    this.router.navigate(['/admin']);
  }

  validateBirthday(birthday) {
    const date = new Date(birthday.value)
    return date.getFullYear() >= 1940 && date.getFullYear() <= 2010;
  }

  logout(): void {
    this.tokenStorage.signOut();
    this.changeRouteToLogin();
  }

  isPasswordConfirmed() {
    return this.user.password == this.user.confirmPassword && this.user.password;
  }

  roleSelector(role: Role) {
    if (!this.user.role) {
      return false;
    }
    return role.name == this.user.role.name;
  }
}
