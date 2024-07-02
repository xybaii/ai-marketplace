import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { User } from '../../../models/models';
import { Subscription, last } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrl: './edit-profile.component.css'
})
export class EditProfileComponent implements OnInit, OnDestroy {

  form!: FormGroup
  sub$!: Subscription
  profile!: User
  id: string = ''
  constructor(private readonly fb: FormBuilder, private readonly userService: UserService, private readonly activatedRoute: ActivatedRoute, private readonly router: Router) {}
  
  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params['id']
    this.sub$ = this.userService.loadProfilePage().subscribe(
      (data) => {
        this.profile = data;

        this.form = this.fb.group({
          email: this.fb.control(this.profile.email),
          firstname: this.fb.control(this.profile.firstname),
          lastname: this.fb.control(this.profile.lastname),
          password: this.fb.control('', [Validators.required]),
        })
      }
    );

  
  }

  update() {
    const updatedProfile = this.form.value
    console.log("submit update")
    this.sub$ = this.userService.updateProfile(this.id, updatedProfile).subscribe(
      (data) => {
        console.log(data + " updating profile")
      }
    );
    this.router.navigate(['/profile'])

  }

  ngOnDestroy(): void {
    this.sub$.unsubscribe()
  }

}
