import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(public authService: AuthService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
  }

  loginForm = this.formBuilder.group({
    username: [null, Validators.required],
    password: [null, Validators.required]
  });

  login() {
    this.authService.login(this.loginForm.get('username')?.value, this.loginForm.get('password')?.value).subscribe();
  }

  isInvalid(property: string) {
    const field = this.loginForm.get(property)
    return field?.invalid && (field?.dirty || field?.touched)
  }

}
