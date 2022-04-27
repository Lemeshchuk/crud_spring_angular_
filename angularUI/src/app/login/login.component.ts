import {Component, OnInit} from '@angular/core';
import {AuthService} from '../services/auth.service';
import {TokenStorageService} from '../services/token-storage.service';
import {Router} from '@angular/router';
import {User} from "../model/user";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  user: User;

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private router: Router) {
  }

  ngOnInit(): void {

    sessionStorage.removeItem('idEditUser');
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
    }
  }

  onSubmit(): void {

    this.authService.login(this.form).subscribe(
      data => {
        if (data.headers.get("token") != null) {

          this.tokenStorage.saveToken(data.headers.get("token"));
          this.tokenStorage.saveUser(data.body);
          this.user = data.body;

          if (this.user.defaultRolePage == "/admin") {
            this.changeRouteToAdmin();
          } else {
            this.changeRouteToUsersPage();
          }

          this.isLoginFailed = false;
          this.isLoggedIn = true;
        } else {
          this.isLoginFailed = true;
        }
      },
      err => {
        this.errorMessage = err.error.message;
      }
    );
  }

  registration(): void {
    this.changeRouteToRegistration();
  }

  changeRouteToAdmin() {
    this.router.navigate(['/admin']);
  }

  changeRouteToRegistration() {
    this.router.navigate(['/userManager']);
  }

  changeRouteToUsersPage() {
    this.router.navigate(['/userPage'])
  }
}
