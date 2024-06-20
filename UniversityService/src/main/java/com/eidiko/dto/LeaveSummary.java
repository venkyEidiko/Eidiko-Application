package com.eidiko.dto;

import java.util.List;
import java.util.Map;
import com.eidiko.entity.EmpLeave;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class LeaveSummary {
	private String leaveType;
	private double consumedLeave; 
	private double availableLeave;
	private double totalLeave;
	private List<EmpLeave> pendingLeave;
	 private Map<String, MonthlyLeaveData> monthlyLeaveData;
}
