import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { AuthorizationService } from '../../services/authorization.service';
import { Store } from '@ngrx/store';
import { Subscription } from 'rxjs';
import { User } from '../../models/models';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.css'
})
export class ProfilePageComponent implements OnInit, OnDestroy{

  constructor(private readonly userService: UserService, 
              private readonly router: Router, 
              private readonly authservice: AuthorizationService,
              private readonly store: Store<{ user: any }>
            ) {}


  sub$!: Subscription
  profile: User | null = null
   

  ngOnInit(): void {
    this.sub$ = this.userService.loadProfilePage().subscribe(
      {next: (data) => {
        this.profile = data;
      }, error: (error) => {this.router.navigate(['/error'])}}
    );

    this.authservice.getToken();
    
  }


  

  logout(){
    
    this.sub$ = this.userService.logout().subscribe(
      {next: () => {
        this.authservice.removeToken(); 
        this.router.navigate(['/login']);
      },
      error: (error) => {}}
    );
  }





ngOnDestroy(): void {
  this.sub$.unsubscribe()
}
}
