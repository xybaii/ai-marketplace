import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterService } from '../../services/register.service';
import { booleanApiResponse, registerRequest } from '../../models/models';
import { Subscription } from 'rxjs';
import { AuthorizationService } from '../../services/authorization.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit, OnDestroy{
  constructor(private readonly router: Router, private readonly fb: FormBuilder, private readonly registerService: RegisterService, private readonly authService: AuthorizationService ) { }


  regForm!: FormGroup
  regRequest!: registerRequest
  booleanApiResponse!: booleanApiResponse
  failRegister: string = ''
  sub$!: Subscription

  ngOnInit(): void {

    if (this.authService.isLoggedIn()){
      this.router.navigate(['/product'])
  }

    this.regForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(16)]],
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
    });
  }

  processRegistration(){
    this.regRequest = this.regForm.value

    this.sub$ = this.registerService.processRegistration(this.regRequest).subscribe({
      next: (data) => {
        this.booleanApiResponse = data
        if (this.booleanApiResponse.success) {
          this.router.navigate(['/login'])
        } else {
          this.failRegister = this.booleanApiResponse.message
          
        }        
      },
      error: (error) => {
            this.failRegister = 'Invalid inputs. Please try again.';        
      }
    })
    
  }

  login() {
    this.router.navigate(['/login'])
  }
  ngOnDestroy() {
    if (this.sub$) {
      this.sub$.unsubscribe();
    }
  }

}
