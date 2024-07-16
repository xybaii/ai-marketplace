import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../../services/login.service';
import { booleanApiResponse, loginRequest } from '../../models/models';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { AuthorizationService } from '../../services/authorization.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit, OnDestroy{

  constructor(private readonly router: Router, private readonly fb: FormBuilder, private readonly loginService: LoginService, private readonly authService: AuthorizationService ) { }

  loginForm!: FormGroup
  loginRequest!: loginRequest
  booleanApiResponse!: booleanApiResponse
  failLogin: string = ''
  sub$!: Subscription

  
  ngOnInit(): void {

    if (this.authService.isLoggedIn()){
      this.router.navigate(['/product'])
  }
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    })
    
  }

  processLogin() {
    


    this.loginRequest = this.loginForm.value;
  
    this.sub$ = this.loginService.processLogin(this.loginRequest).subscribe({
      next: (data) => {
        this.booleanApiResponse = data
        if (this.booleanApiResponse.success) {
          this.router.navigate(['/profile'])
        } else {
          this.failLogin = this.booleanApiResponse.message
          
        }        
      },
      error: (error) => {
        
        if (error.status === 403) {
            this.failLogin = 'Invalid username or password. Please try again.';
        } else {
            this.failLogin = error?.error?.description || 'Failed to login. Please try again.';
        }
      }
    });
  }

  signUp() {
    this.router.navigate(['/register'])
  }

  
  ngOnDestroy() {
    if (this.sub$) {
      this.sub$.unsubscribe();
    }
  }

}
