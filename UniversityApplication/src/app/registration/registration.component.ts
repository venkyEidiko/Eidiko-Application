import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../services/login.service';
import { Address, RegistrationForm } from '../registratioRequest';
import { Router } from '@angular/router';
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { SnackbarService } from '../snackbar.service';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
})
export class RegistrationComponent implements OnInit {
  registrationForm!: FormGroup;
  singupResponse: any;
  showPassword = false;

  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    public router :Router,
    private snackbarservice:SnackbarService
  ) { }

  ngOnInit(): void {
   
    this.registrationForm = this.formBuilder.group({
      firstName: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
      lastName: [''],
      role: ['Employee', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8),
        Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]+$/)]],
      confirmPassword: ['', []],
      email: ['',
        [
          Validators.required,
          Validators.email,
          Validators.pattern(/@eidiko-india\.com$/),
        ],
      ],
      gender: ['Male', [Validators.required]],
      employeeId: ['', [Validators.required, Validators.max(9999)]],
      phoneNu: ['', [Validators.required, Validators.pattern(/^[6-9]\d{9}$/)]],
      doorNu: ['', [Validators.required,minValueValidator(1)]],
      streetName: ['', [Validators.required,Validators.pattern(/^[A-Za-z0-9\s]+$/)]],
      landmark: ['', [Validators.required,Validators.pattern(/^[A-Za-z0-9\s]+$/)]],
      area: ['', [Validators.required,Validators.pattern(/^[A-Za-z\s]+$/)]],
      city: ['', [Validators.required,Validators.pattern(/^[A-Za-z\s]+$/)]],
      state: ['', [Validators.required,Validators.pattern(/^[A-Za-z\s]+$/)]],
      pincode: ['', [Validators.required,Validators.pattern(/^\d{6}$/)]],
    }, { validators: this.passwordMatchValidator });
  }

  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    if (password !== confirmPassword) {
      return { passwordsNotMatching: true };
    }
    return null;
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  onSingup() {
    const formValues = this.registrationForm.value;

    const addresses: Address[] = [{
      addressType:"Current Address",
      doorNumber: formValues.doorNu,
      streetName: formValues.streetName,
      landmark: formValues.landmark,
      area: formValues.area,
      city: formValues.city,
      state: formValues.state,
      pincode: formValues.pincode
    }];

    const registrationData: RegistrationForm = {
      firstName: formValues.firstName,
      lastName: formValues.lastName,
      password: formValues.password,
      email: formValues.email,
      phoneNu: formValues.phoneNu,
      employeeId: formValues.employeeId,
      gender: formValues.gender,
      role: {roleName: formValues.role },
      addresses: addresses
    };
    console.log(registrationData);
    this.loginService.register(registrationData).subscribe(
      (response: any) => {
        console.log("reg ",response);
        if(response.error == null && response.statusCode==201){
          this.snackbarservice.showSuccess("Registration Successful!")
        this.router.navigate(['/'])
        }
        else{
          console.log(response.error);
          
            this.snackbarservice.showError("Registration Unsuccessful!Try Again")
          
        
        }
      },
      (error: any) => {
        console.error('Registration error, route to error page', error);
      }
    );
  }
  onLogin(){
    this.router.navigate(['/']);
  }
}
export function minValueValidator(min: number): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    if (control.value !== null && control.value < min) {
      return { minValue: { requiredValue: min, actualValue: control.value } };
    }
    return null;
  };
}

