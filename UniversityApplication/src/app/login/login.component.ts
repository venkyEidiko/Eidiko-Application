import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  
  constructor(private formBuilder: FormBuilder){}

  loginForm: FormGroup = new FormGroup({});

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      emailOrMobile:['',Validators.required],
      password:['',Validators.required]
    },{
      validators: this.EmailOrMobileValidator
    });
  }


  EmailOrMobileValidator(form: FormGroup) {
    const input = form.get('emailOrMobile')?.value;
    const emailPattern: RegExp = /^[a-zA-Z0-9._%+-]+@eidiko-india\.com$/;
    const mobilePattern: RegExp = /^[6-9]\d{9}$/;
    const validEmail = emailPattern.test(input);
    const validMobile = mobilePattern.test(input); 
    if(validEmail || validMobile){
      return null;
    }
    else{
      return {invalidInput:true};
    }
  }

  onLogin(){
    console.log(this.loginForm.value);
  }
  toForgotpassword(){
    console.log("clicked");
    
  }

}
