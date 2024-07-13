
package com.eidiko.excelreader;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import com.eidiko.exception_handler.FormatMismatchException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ExcelReader {

	private static final LocalTime COMPANY_START_MORNING_TIME = LocalTime.of(10, 0);

	private static final LocalTime COMPANY_START_AFTERNOON_TIME = LocalTime.of(14, 0);

	private static final LocalTime COMPANY_START_NIGHT_TIME = LocalTime.of(21, 0);

	public static final String[] HEADERS = { "empName", "employeeId", "date", "in-time", "out-time", "status" };

	public static boolean checkExcelFormat(MultipartFile file) {
		String contentType = file.getContentType();
		return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(contentType);
	}

	public List<EmployeeAttendance> saveAttendanceData(MultipartFile file) throws IOException, FormatMismatchException {

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
					// Check for missing headers
					List<String> missingHeaders = new ArrayList<>();
					for (String header : HEADERS) {
						if (!headerMap.containsKey(header.toLowerCase())) {
							missingHeaders.add(header);
						}
					}
					if (!missingHeaders.isEmpty()) {
						throw new FormatMismatchException("Missing headers: " + String.join(", ", missingHeaders));
					}

					continue; // Skip header row
				}

				EmployeeAttendance attendance = new EmployeeAttendance();

				LocalDateTime inDateTime = null;
				LocalDateTime outDateTime = null;

				for (String header : HEADERS) {
					Integer cellIndex = headerMap.get(header.toLowerCase());
					if (cellIndex != null) {
						Cell cell = row.getCell(cellIndex);
						switch (header) {
						case "empName":
							if (cell != null && cell.getCellType() == CellType.STRING) {
								attendance.setEmpName(cell.getStringCellValue());
							} else {
								throw new FormatMismatchException("Invalid data in empName column");
							}
							break;
						case "employeeId":
							if (cell != null) {

								if (cell.getCellType() == CellType.NUMERIC) {
									attendance.setEmployeeId((long) cell.getNumericCellValue());
								} else {
									throw new FormatMismatchException("Invalid data in employeeId column");
								}
							} else {
								throw new FormatMismatchException("employeeId is missing");
							}
							break;
						case "date":
							if (cell != null && cell.getCellType() == CellType.NUMERIC) {
								attendance.setDate(cell.getLocalDateTimeCellValue().toLocalDate());
							} else {
								throw new FormatMismatchException("Invalid data in date column");
							}
							break;
						case "in-time":
							if (cell != null && cell.getCellType() == CellType.NUMERIC) {
								inDateTime = cell.getLocalDateTimeCellValue();
								attendance.setInTime(
										outputFormat.format(java.sql.Time.valueOf(inDateTime.toLocalTime())));
							} else {
								throw new FormatMismatchException("Invalid data in in-time column");
							}
							break;
						case "out-time":
							if (cell != null && cell.getCellType() == CellType.NUMERIC) {
								outDateTime = cell.getLocalDateTimeCellValue();
								attendance.setOutTime(
										outputFormat.format(java.sql.Time.valueOf(outDateTime.toLocalTime())));
							} else {
								throw new FormatMismatchException("Invalid data in out-time column");
							}
							break;
						case "status":
							if (cell != null && cell.getCellType() == CellType.STRING) {
								attendance.setWorkingDayStatus(cell.getStringCellValue());
							} else {
								throw new FormatMismatchException("Invalid data in status column");
							}
							break;
						default:
							break;
						}
					}
				}

				if (inDateTime != null && outDateTime != null) {
					Duration effectiveDuration = Duration.between(inDateTime, outDateTime);
					String effectiveHours = formatDuration(effectiveDuration);
					attendance.setEffectiveHours(effectiveHours);
					attendance.setGrossHours(effectiveHours); // Assuming gross hours are same as effective hours
					// Determine the shift based on the in-time
					LocalTime shiftStartTime;
					if (inDateTime.toLocalTime().isBefore(COMPANY_START_MORNING_TIME)) {
						shiftStartTime = COMPANY_START_NIGHT_TIME;
					} else if (inDateTime.toLocalTime().isBefore(COMPANY_START_AFTERNOON_TIME)) {
						shiftStartTime = COMPANY_START_MORNING_TIME;
					} else if (inDateTime.toLocalTime().isBefore(COMPANY_START_NIGHT_TIME)) {
						shiftStartTime = COMPANY_START_AFTERNOON_TIME;
					} else {
						shiftStartTime = COMPANY_START_NIGHT_TIME;
					}
					if (inDateTime.toLocalTime().isAfter(shiftStartTime)) {
						Duration lateDuration = Duration.between(shiftStartTime, inDateTime.toLocalTime());
						attendance.setStatus("Late by " + formatDuration(lateDuration));
					} else if (inDateTime.equals(outDateTime)) {
						attendance.setStatus(null);
					} else {
						attendance.setStatus("OnTime");
					}
				} else {
					attendance.setEffectiveHours("00:00:00");
					attendance.setGrossHours("00:00:00");
					attendance.setStatus("No in-time or out-time");
				}

				log.info("Attendance entity {}", attendance);
				attendanceList.add(attendance);
			}
		}
		return attendanceList;
	}

	private String formatDuration(Duration duration) {
		long hours = duration.toHours();
		long minutes = duration.toMinutes() % 60;
		long seconds = duration.getSeconds() % 60;
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
}
