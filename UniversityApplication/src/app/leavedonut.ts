// Define interfaces for API response and data structures
interface LeaveData {
    availableLeave: number;
    consumedLeave: number;
    totalLeave: number;
    pendingLeave: any[]; // Adjust as per your actual data structure
    monthlyLeaveData: {
      [key: string]: {
        totalLeaveTaken: number;
      };
    };
    
  }
  
  interface LeaveApiResponse {
    result: LeaveData[];
   
  }
  