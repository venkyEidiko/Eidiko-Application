import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';
import { loginRequest } from '../loginrequest';
import { SnackbarService } from '../snackbar.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {


  constructor(
    private snackbarservice: SnackbarService,
    private formBuilder: FormBuilder, private loginService: LoginService, private router: Router) { }

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
    const mobilePattern: RegExp = /^[6-9]\d{9}$/;

    const validMobile = mobilePattern.test(input);
    if (validMobile) {
        return null;
      } else {
        return { invalidInput: true };
      }
    }
  


  userDetails: any;



  onLogin() {
    const loginReq: loginRequest = {
      email: this.loginForm.get('email')?.value,
      password: this.loginForm.get('password')?.value
    };
    console.log("my request sent to backend- ",loginReq);


    this.loginService.login(loginReq).subscribe(
      (response: any) => {

       console.log(response);
        if(response.error==null){
        this.loginService.setJwtToken(response.result[0].jwtToken);
        const empId= response.result[0].employee.employeeId;
        console.log("Login component Login empId : ",empId);
       // this.loginService.setEmployeeData(empId);

        localStorage.setItem('employee-data', JSON.stringify(response.result[0].employee));
        //localStorage.setItem('jwt-token', response.result[0].jwtToken);
        localStorage.setItem('refresh-token', response.result[0].refreshToken);
        this.router.navigate(['/layout/home/dashboard']);
        console.log(response.employee);
        this.userDetails = response.employee;
        }
        else{
          console.log(response.error);
          this.snackbarservice.showError("Login Unsuccessful!Try Again")
          
        }

      },
      (error: any) => {
        this.snackbarservice.showError("Login Unsuccessful! Try Again")
      }
    );
  }

  toForgotpassword() {
    this.router.navigate(['/forgotpassword']);
  }
}
  