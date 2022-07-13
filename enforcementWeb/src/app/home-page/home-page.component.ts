import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthorizationService } from '../_services';
@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss'],
})
export class HomePageComponent implements OnInit {
  dark: boolean = true;
  active: boolean = true;
  events: string[] = [];
  opened: boolean = true;
  constructor(
    private router: Router,
    public AuthorizationService: AuthorizationService
  ) {
    console.log("AuthorizationService getRoleName ======= ",this.AuthorizationService.getRoleName())
   }

  ngOnInit(): void { }
  activatedLink(e) {
    // console.log(e);
    // this.dark = true;
    // this.active = true;
  }

  logout() {
    localStorage.setItem('token', '');
    localStorage.setItem('currentUser', '');
    this.router.navigate(['/login']);
  }
}
