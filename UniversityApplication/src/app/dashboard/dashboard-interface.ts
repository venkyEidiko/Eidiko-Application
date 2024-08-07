export interface BirthdayResponse {
    status: string;
    statusCode: number;
    result: Result[];
    error: any;
    password: any;
    email: any;
  }
  
  export interface Result {
    BirthDayToday: Person[];
    WorkAnniversaries: Person[];
  }
  
  export interface Person {
    employeeId: number;
    firstName: string;
    lastName: string;
    noOfYearsCompletedInThisCompany: number;
  }
  