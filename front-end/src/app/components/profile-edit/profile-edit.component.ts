import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from '../../models/models';

@Component({
  selector: 'app-profile-edit',
  templateUrl: './profile-edit.component.html',
  styleUrl: './profile-edit.component.css'
})
export class ProfileEditComponent implements OnInit, OnDestroy {

  constructor(private readonly fb: FormBuilder, 
              private readonly userService: UserService, 
              private readonly activatedRoute: ActivatedRoute, 
              private readonly router: Router) {

                this.form = this.fb.group({
                  email: [''],
                  firstname: [''],
                  lastname: [''],
                  password: ['', [Validators.required]],
                });
              }

  form!: FormGroup
  sub$!: Subscription
  profile: User = {} as User;
  id: string = ''

  
  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params['id']
    this.sub$ = this.userService.loadProfilePage().subscribe(
      (data) => {
        this.profile = data;
        this.form.patchValue({
          email: this.profile.email,
          firstname: this.profile.firstname,
          lastname: this.profile.lastname
        });
      }
    );

    
  
  }

  update() {
    const updatedProfile = this.form.value
    
    this.sub$ = this.userService.updateProfile(this.id, updatedProfile).subscribe(
      (data) => {}
    );
    this.router.navigate(['/profile'])

  }

  ngOnDestroy(): void {
    this.sub$.unsubscribe()
  }

}

