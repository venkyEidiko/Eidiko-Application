export interface Employee {
    accountNonExpired: boolean;
    accountNonLocked: boolean;
    addresses: Address[];
    attendanceCaptureScheme: any; 
    attendanceNumber: any; 
    attendancePenalisationPolicy: any; 
    authorities: any; 
    bloodGroup: string | null;
    businessUnit: any; 
    contractStatus: any; 
    costCenter: any; 
    credentialsNonExpired: boolean;
    dateOfBirth: string; 
    dateOfJoining: string;
    department: any; 
    dottedLineManager: any;
    email: string;
    emergencyContactNumber: string | null;
    employeeId: number;
    enabled: boolean;
    firstName: string;
    gender: string;
    holidayCalendar: any; 
    inProbation: any; 
    jobTitlePrimary: any; 
    jobTitleSecondry: any; 
    lastName: string;
    leavePlan: any; 
    legalEntity: any; 
    location: any; 
    managerOfManager: any; 
    maritalStatus: any; 
    nationality: any; 
    noticePeriod: any; 
    password: string; 
    payBand: any; 
    payGrade: any; 
    personalEmail: string | null;
    phoneNu: any; 
    physicallyHandicapped: any; 
    reportingHr: any; 
    reportsTo: number;
    residenceNumber: any; 
    role: Role; 
    shift: any; 
    shiftAllowancePolicy: any; 
    shiftweeklyOffRule: any; 
    skype: string | null;
    timeType: any; 
    username: string;
    weeklyOffPolicy: any; 
    workEmail: string | null;
    workNumber: string | null;
    workerType: any; 
  }
  
  export interface Address {
    addressType: string | null;
    doorNumber: string;
    streetName: string;
    landmark: string;
    area: string;
    city: string;
    pincode: number;
    state: string;
    addressLine1:any;
    addressLine2:any;

  }  
  
  export interface Role {
    roleId: number;
    name: string;
   
  }
  