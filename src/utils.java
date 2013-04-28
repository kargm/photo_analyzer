public class utils {

	public static String get_weekday_string(int day) {
		String[] weekday = new String[8];
		weekday[0]="Unknown";
		weekday[1]="Sunday";
		weekday[2]="Monday";
		weekday[3]="Tuesday";
		weekday[4]="Wednesday";
		weekday[5]="Thursday";
		weekday[6]="Friday";
		weekday[7]="Saturday";
		return weekday[day];
	}
	
	public static String get_month_string(int month) {
		String[] month_string = new String[13];
		month_string[12] = "Unknown";
		month_string[0] = "January";
		month_string[1] = "February";
		month_string[2] = "March";
		month_string[3] = "April";
		month_string[4] = "May";
		month_string[5] = "June";
		month_string[6] = "July";
		month_string[7] = "August";
		month_string[8] = "September";
		month_string[9] = "October";
		month_string[10] = "November";
		month_string[11] = "Dezember";
		return month_string[month];
	}
	
}
