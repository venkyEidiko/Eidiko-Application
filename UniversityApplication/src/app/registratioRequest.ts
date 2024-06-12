export interface Role {
    roleName: string;
  }
  
  export interface Address {
    doorNumber: string;
    streetName: string;
    landmark: string;
    area: string;
    city: string;
    state: string;
    pincode: number;
  }
  
  export interface RegistrationForm {
    firstName: string;
    lastName: string;
    password: string;
    email: string;
    phoneNu:string;
    employeeId: number;
    gender: string;
    role: Role;
    addresses: Address[];
  }
  