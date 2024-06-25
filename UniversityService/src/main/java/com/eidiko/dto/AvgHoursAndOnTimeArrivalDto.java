package com.eidiko.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvgHoursAndOnTimeArrivalDto {

	private String avgArivalPer;
	private String avgPerDay;
	   
	  private String avgArivalPerTeam;
	  private String teamAvgPerDay;
}
