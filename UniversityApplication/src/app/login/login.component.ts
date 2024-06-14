import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../login.service';
import { Router } from '@angular/router';
import { loginRequest } from '../loginRequest';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private loginService: LoginService, private router: Router) {}

  loginForm: FormGroup = new FormGroup({});

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    }, {
      validators: this.EmailOrMobileValidator
    });
  }

  EmailOrMobileValidator(form: FormGroup) {
    const input = form.get('email')?.value;
    const emailPattern: RegExp = /^[a-zA-Z0-9._%+-]+@eidiko-india\.com$/;
    const mobilePattern: RegExp = /^[6-9]\d{9}$/;
    const validEmail = emailPattern.test(input);
    const validMobile = mobilePattern.test(input);
    if (validEmail || validMobile) {
      return null;
    } else {
      return { invalidInput: true };
    }
  }

  onLogin() {
    const loginReq: loginRequest = {
      email: this.loginForm.get('email')?.value,
      password: this.loginForm.get('password')?.value
    };

    this.loginService.login(loginReq).subscribe(
      (response: any) => {
        console.log(response);
        this.loginService.setJwtToken(response.jwtToken);
        this.loginService.setEmployeeData(response.employee);
        this.router.navigate(['/layout/home/dashboard']);
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  toForgotpassword() {
    this.router.navigate(['/forgotpassword']);
  }
}
