/*
package com.eidiko.excelreader;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import com.eidiko.entity.EmployeeAttendance;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ExcelReader {

	private static final LocalTime COMPANY_START_TIME = LocalTime.of(10, 0);
	public static final String[] HEADERS = { "empName", "empId", "date", "in-time", "out-time", "status" };

	public static boolean checkExcelFormat(MultipartFile file) {
		String contentType = file.getContentType();
		return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(contentType);
	}

	public List<EmployeeAttendance> saveAttendanceData(MultipartFile file) throws IOException {

		// Define the output format (12-hour format)
		SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm:ss a");

		List<EmployeeAttendance> attendanceList = new ArrayList<>();
		try (InputStream inputStream = file.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {

			Sheet sheet = workbook.getSheetAt(0);
			Map<String, Integer> headerMap = new HashMap<>();
			int rowNumber = 0;

			for (Row row : sheet) {
				if (rowNumber == 0) {
					for (Cell cell : row) {
						headerMap.put(cell.getStringCellValue().toLowerCase(), cell.getColumnIndex());
					}
					rowNumber++;
					continue; // Skip header row
				}
				EmployeeAttendance attendance = new EmployeeAttendance();
				attendance.setEmpId(UUID.randomUUID().toString()); // Set unique identifier

				try {
					validateRow(row, headerMap); // Validate the row before processing
					 LocalTime inTime=null;
		                LocalTime outTime=null;
					for (String header : HEADERS) {
						Integer cellIndex = headerMap.get(header.toLowerCase());
						if (cellIndex != null) {
							Cell cell = row.getCell(cellIndex);
							switch (header) {
							case "empName":
								attendance.setEmpName(cell.getStringCellValue());
								break;
							case "empId":
								attendance.setEmpId(cell.getStringCellValue());
								break;
							case "date":
								attendance.setDate(cell.getLocalDateTimeCellValue().toLocalDate());
								break;
							case "in-time":
                                 inTime = cell.getLocalDateTimeCellValue().toLocalTime();
                              //  attendance.setInTime(outputFormat.format(java.sql.Time.valueOf(inTime)));

								break;
							case "out-time":
								 outTime = cell.getLocalDateTimeCellValue().toLocalTime();
								//attendance.setOutTime(outputFormat.format(java.sql.Time.valueOf(outTime)));
								break;
							case "status":
								attendance.setWorkingDayStatus(cell.getStringCellValue());
								break;
							default:
								break;
							}
						}
					}
               
					
					Duration effectiveDuration = Duration.between(inTime, outTime);
					if (effectiveDuration.isZero()) {
						
						attendance.setInTime(null);
						attendance.setOutTime(null);
					}
					else
					{
						 inTime = row.getCell(headerMap.get("in-time")).getLocalDateTimeCellValue().toLocalTime();
						 outTime = row.getCell(headerMap.get("out-time")).getLocalDateTimeCellValue()
								.toLocalTime();

					}
					String effectiveHours = formatDuration(effectiveDuration);
                     
					
					attendance.setEffectiveHours(effectiveHours);
					attendance.setGrossHours(effectiveHours); // Assuming gross hours are same as effective hours

					if (inTime.isAfter(COMPANY_START_TIME)) {
						Duration lateDuration = Duration.between(COMPANY_START_TIME, inTime);
						attendance.setStatus("Late by " + formatDuration(lateDuration));
					} 
					else if(effectiveDuration.isZero()){
						attendance.setStatus(null);
					}
					else {
						attendance.setStatus("OnTime");
					}

					attendanceList.add(attendance);

				} catch (IllegalArgumentException e) {
					log.error("Error processing row {}: {}", rowNumber, e.getMessage());
					throw new IllegalArgumentException(e.getMessage());
				}

				rowNumber++;
			}
		}

		return attendanceList;
	}

	private void validateRow(Row row, Map<String, Integer> headerMap) {
		for (String header : HEADERS) {
			Integer cellIndex = headerMap.get(header.toLowerCase());
			if (cellIndex != null) {
				Cell cell = row.getCell(cellIndex);
				if (cell == null) {
					throw new IllegalArgumentException("Missing cell for " );
				}
				switch (header) {
				case "empName":
				case "empId":
				case "status":
					if (cell.getCellType() != CellType.STRING) {
						throw new IllegalArgumentException("Invalid data type for " + header);
					}
					break;
				case "date":
				case "in-time":
				case "out-time":
					if (cell.getCellType() != CellType.NUMERIC) {
						throw new IllegalArgumentException("Invalid data type for " + header);
					}
					break;
				default:
					break;
				}
			}
		}
	}

	private String formatDuration(Duration duration) {
		long hours = duration.toHours();
		long minutes = duration.toMinutes() % 60;
		long seconds = duration.getSeconds() % 60;
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
}
*/
/*
package com.eidiko.excelreader;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import com.eidiko.entity.EmployeeAttendance;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ExcelReader {

	private static final LocalTime COMPANY_START_TIME[] = {

			LocalTime.of(10, 0), LocalTime.of(14, 0), LocalTime.of(21, 0) };

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss a");

	public static final String[] HEADERS = { "empName", "employeeId", "date", "in-time", "out-time", "status" };

	public static boolean checkExcelFormat(MultipartFile file) {
		String contentType = file.getContentType();
		return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(contentType);
	}

	public List<EmployeeAttendance> saveAttendanceData(MultipartFile file) throws IOException, ParseException {

		// SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm:ss a");

		List<EmployeeAttendance> attendanceList = new ArrayList<>();
		try (InputStream inputStream = file.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {

			Sheet sheet = workbook.getSheetAt(0);
			Map<String, Integer> headerMap = new HashMap<>();
			int rowNumber = 0;

			for (Row row : sheet) {
				if (rowNumber == 0) {
					for (Cell cell : row) {
						headerMap.put(cell.getStringCellValue().toLowerCase(), cell.getColumnIndex());
					}
					rowNumber++;
					continue; // Skip header row
				}
				EmployeeAttendance attendance = new EmployeeAttendance();
				try {
					validateRow(row, headerMap); // Validate the row before processing
					LocalDateTime inTime = null;
					LocalDateTime outTime = null;

					SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm:ss a");
					String formateIntime = outputFormat.format(inTime);
					String formateoutTime = outputFormat.format(outTime);

					 Date parsedDate = outputFormat.parse(formateIntime);
					 Date parsedDate1 = outputFormat.parse(formateoutTime);
					  LocalTime l1 = parsedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
					  LocalTime l2 = parsedDate1.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

					for (String header : HEADERS) {
						Integer cellIndex = headerMap.get(header.toLowerCase());
						if (cellIndex != null) {
							Cell cell = row.getCell(cellIndex);
							switch (header) {
							case "empName":
								attendance.setEmpName(cell.getStringCellValue());
								break;
							case "employeeId":
								attendance.setEmployeeId((long) cell.getNumericCellValue());
								break;
							case "date":
								attendance.setDate(cell.getLocalDateTimeCellValue().toLocalDate());
								break;
							case "in-time":
								inTime = LocalDateTime.parse(cell.getStringCellValue(), DATE_TIME_FORMATTER);
								attendance.setInTime(inTime);
								 attendance.setInTime(outputFormat.format(java.sql.Time.valueOf(inTime)));
								//attendance.setInTime(inTime);
								break;
							case "out-time":
								outTime = LocalDateTime.parse(cell.getStringCellValue(), DATE_TIME_FORMATTER);
								attendance.setOutTime(outTime);
								 attendance.setOutTime(outputFormat.format(java.sql.Time.valueOf(outTime)));
								// attendance.setOutTime(outTime);
								break;
							case "status":
								attendance.setWorkingDayStatus(cell.getStringCellValue());
								break;
							default:
								break;
							}
						}
					}

					Duration effectiveDuration = (inTime != null && outTime != null) ? Duration.between(inTime, outTime)
							: Duration.ZERO;

					if (effectiveDuration.isZero()) {
						attendance.setInTime(null);
						attendance.setOutTime(null);
						attendance.setStatus(null);
					} else {
						String effectiveHours = formatDuration(effectiveDuration);
						attendance.setEffectiveHours(effectiveHours);
						attendance.setGrossHours(effectiveHours); // Assuming gross hours are same as effective hours

						for (LocalTime time : COMPANY_START_TIME) {

							if (time.isAfter(l1)) {
								Duration lateDuration = Duration.between(l1, l1);
								attendance.setStatus("Late by " + formatDuration(lateDuration));
							
								
							} else {
								attendance.setStatus("OnTime");
								
							}
						}
					}

					attendanceList.add(attendance);

				} catch (IllegalArgumentException e) {
					log.error("Error processing row {}: {}", rowNumber, e.getMessage());
					throw new IllegalArgumentException(e.getMessage());
				}

				rowNumber++;
			}
		}

		return attendanceList;
	}

	private void validateRow(Row row, Map<String, Integer> headerMap) {
		for (String header : HEADERS) {
			Integer cellIndex = headerMap.get(header.toLowerCase());
			if (cellIndex != null) {
				Cell cell = row.getCell(cellIndex);
				if (cell == null) {
					throw new IllegalArgumentException("Missing cell for " + header);
				}
				switch (header) {
				case "empName":
//                    case "empId":
				case "status":
					if (cell.getCellType() != CellType.STRING) {
						throw new IllegalArgumentException("Invalid data type for " + header);
					}
					break;
				case "date":
				case "in-time":
				case "out-time":
					if (cell.getCellType() != CellType.NUMERIC) {
						throw new IllegalArgumentException("Invalid data type for " + header);
					}
					break;
				default:
					break;
				}
			}
		}
	}

	private String formatDuration(Duration duration) {
		long hours = duration.toHours();
		long minutes = duration.toMinutes() % 60;
		long seconds = duration.getSeconds() % 60;
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
}

*/


package com.eidiko.excelreader;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import com.eidiko.entity.EmployeeAttendance;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ExcelReader {

	private static final LocalTime[] COMPANY_START_TIMES = { LocalTime.of(10, 0), LocalTime.of(14, 0),
			LocalTime.of(21, 0) };

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	public static final String[] HEADERS = { "empName", "employeeId", "date", "in-time", "out-time", "status" };

	public static boolean checkExcelFormat(MultipartFile file) {
		String contentType = file.getContentType();
		return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(contentType);
	}

	public List<EmployeeAttendance> saveAttendanceData(MultipartFile file) throws IOException {
		List<EmployeeAttendance> attendanceList = new ArrayList<>();
		try (InputStream inputStream = file.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {

			Sheet sheet = workbook.getSheetAt(0);
			Map<String, Integer> headerMap = new HashMap<>();
			int rowNumber = 0;

			for (Row row : sheet) {
				if (rowNumber == 0) {
					for (Cell cell : row) {
						headerMap.put(cell.getStringCellValue().toLowerCase(), cell.getColumnIndex());
					}
					rowNumber++;
					continue; // Skip header row
				}

				EmployeeAttendance attendance = new EmployeeAttendance();
				try {
					validateRow(row, headerMap); // Validate the row before processing
					LocalDateTime inTime = null;
					LocalDateTime outTime = null;

					for (String header : HEADERS) {
						Integer cellIndex = headerMap.get(header.toLowerCase());
						if (cellIndex != null) {
							Cell cell = row.getCell(cellIndex);
							switch (header) {
							case "empName":
								attendance.setEmpName(cell.getStringCellValue());
								break;
							case "employeeId":
								if (cell.getCellType() == CellType.NUMERIC) {
									attendance.setEmployeeId((long) cell.getNumericCellValue());
								} else if (cell.getCellType() == CellType.STRING) {
									attendance.setEmployeeId(Long.parseLong(cell.getStringCellValue()));
								} else {
									throw new IllegalArgumentException("Invalid data type for employeeId");
								}
								break;
							case "date":
								if (cell.getCellType() == CellType.NUMERIC) {
									attendance.setDate(cell.getLocalDateTimeCellValue().toLocalDate());
								} else if (cell.getCellType() == CellType.STRING) {
									attendance.setDate(LocalDate.parse(cell.getStringCellValue(), DATE_TIME_FORMATTER));
								} else {
									throw new IllegalArgumentException("Invalid data type for date");
								}
								break;
							case "in-time":
								if (cell.getCellType() == CellType.NUMERIC) {
									inTime = cell.getLocalDateTimeCellValue();
								} else if (cell.getCellType() == CellType.STRING) {
									inTime = LocalDateTime.parse(cell.getStringCellValue(), DATE_TIME_FORMATTER);
								} else {
									throw new IllegalArgumentException("Invalid data type for in-time");
								}
								attendance.setInTime(inTime);
								break;
							case "out-time":
								if (cell.getCellType() == CellType.NUMERIC) {
									outTime = cell.getLocalDateTimeCellValue();
								} else if (cell.getCellType() == CellType.STRING) {
									outTime = LocalDateTime.parse(cell.getStringCellValue(), DATE_TIME_FORMATTER);
								} else {
									throw new IllegalArgumentException("Invalid data type for out-time");
								}
								attendance.setOutTime(outTime);
								break;
							case "status":
								attendance.setWorkingDayStatus(cell.getStringCellValue());
								break;
							default:
								break;
							}
						}
					}

					// if (inTime != null && outTime != null) {
					Duration effectiveDuration = Duration.between(inTime, outTime);
					String effectiveHours = formatDuration(effectiveDuration);
					attendance.setEffectiveHours(effectiveHours);
					attendance.setGrossHours(effectiveHours);

					for (LocalTime startTime : COMPANY_START_TIMES) {
						if (inTime.toLocalTime().isAfter(startTime)) {
							Duration lateDuration = Duration.between(startTime, inTime.toLocalTime());
							attendance.setStatus("Late by " + formatDuration(lateDuration));

							break;
						} else if (inTime.equals(outTime)) {
							attendance.setStatus(null);
							break;
						} else {
							attendance.setStatus("OnTime");
							break;
						}
					}
//					} else {
//						attendance.setStatus(null);
//
//					}

					attendanceList.add(attendance);

				} catch (IllegalArgumentException e) {
					log.error("Error processing row {}: {}", rowNumber, e.getMessage());
					throw new IllegalArgumentException(e.getMessage());
				}

				rowNumber++;
			}
		}

		return attendanceList;
	}

	private void validateRow(Row row, Map<String, Integer> headerMap) {
		for (String header : HEADERS) {
			Integer cellIndex = headerMap.get(header.toLowerCase());
			if (cellIndex != null) {
				Cell cell = row.getCell(cellIndex);
				if (cell == null) {
					throw new IllegalArgumentException("Missing cell for " + header);
				}
				switch (header) {
				case "empName":
				case "status":
					if (cell.getCellType() != CellType.STRING) {
						throw new IllegalArgumentException("Invalid data type for " + header);
					}
					break;
				case "employeeId":
					if (cell.getCellType() != CellType.NUMERIC && cell.getCellType() != CellType.STRING) {
						throw new IllegalArgumentException("Invalid data type for " + header);
					}
					break;
				case "date":
				case "in-time":
				case "out-time":
					if (cell.getCellType() != CellType.NUMERIC && cell.getCellType() != CellType.STRING) {
						throw new IllegalArgumentException("Invalid data type for " + header);
					}
					break;
				default:
					break;
				}
			}
		}
	}

	private String formatDuration(Duration duration) {
		long hours = duration.toHours();
		long minutes = duration.toMinutes() % 60;
		long seconds = duration.getSeconds() % 60;
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
}
