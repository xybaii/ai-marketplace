import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from '../../../models/models';
import { AuthorizationService } from '../../../services/authorization.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit, OnDestroy{

  constructor(private readonly userService: UserService, 
              private readonly router: Router, 
              private readonly authservice: AuthorizationService,
              private readonly activatedRoute: ActivatedRoute
            ) {}
  ngOnDestroy(): void {
    this.sub$.unsubscribe()
  }

  sub$!: Subscription
  profile!: User
  id: string = ''
 

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params['id']
    this.sub$ = this.userService.loadProfilePage().subscribe(
      (data) => {
        this.profile = data;
        console.log(this.profile.id + "id " )
      }
    );
  }



  logout(){
    
    this.sub$ = this.userService.logout().subscribe(
      () => {
        console.log("Logout successful on server-side (if applicable)");
        this.router.navigate(['/']); // Redirect to login or home page
        this.removeToken(); // Delete cookie (if applicable)
      },
      (error) => {
        console.error("Error during logout:", error);
      
      }
    );
  }

  removeToken() {
    try {
      this.authservice.removeToken(); // Attempt to delete cookie using auth service
      console.log("Token removed from cookie (if applicable)");
    } catch (error) {
      console.error("Error removing token from cookie:", error);
      // Handle cookie deletion errors gracefully (e.g., inform user about potential issues)
    }
}

edit(){
  
}
}
