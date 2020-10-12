package com.todo.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CronUtil {
	private final Date mDate;
	private final Calendar mCal;
	private final String mSeconds = "0";
	private final String mDaysOfWeek = "?";

	private String mMins;
	private String mHours;
	private String mDaysOfMonth;
	private String mMonths;
	private String mYears;

	public CronUtil(Date pDate) {
		this.mDate = pDate;
		mCal = Calendar.getInstance();
		this.generateCronExpression();
	}

	private void generateCronExpression() {
		mCal.setTime(mDate);

		String hours = String.valueOf(mCal.get(Calendar.HOUR_OF_DAY));
		this.mHours = hours;

		String mins = String.valueOf(mCal.get(Calendar.MINUTE));
		this.mMins = mins;

		String days = String.valueOf(mCal.get(Calendar.DAY_OF_MONTH));
		this.mDaysOfMonth = days;

		String months = new java.text.SimpleDateFormat("MM").format(mCal.getTime());
		this.mMonths = months;

		String years = String.valueOf(mCal.get(Calendar.YEAR));
		this.mYears = years;

	}

	public Date getDate() {
		return mDate;
	}

	public String getSeconds() {
		return mSeconds;
	}

	public String getMins() {
		return mMins;
	}

	public String getDaysOfWeek() {
		return mDaysOfWeek;
	}

	public String getHours() {
		return mHours;
	}

	public String getDaysOfMonth() {
		return mDaysOfMonth;
	}

	public String getMonths() {
		return mMonths;
	}

	public String getYears() {
		return mYears;
	}

	public static void createCronExpression(Date date) {

		try {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dt = dateFormat.format(date);

			Date cronDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dt);

			CronUtil calHelper = new CronUtil(cronDate);
			String cron = calHelper.getSeconds() + " " + calHelper.getMins() + " " + calHelper.getHours() + " "
					+ calHelper.getDaysOfMonth() + " " + calHelper.getMonths() + " " + calHelper.getDaysOfWeek() + " "
					+ calHelper.getYears();
			System.out.println(cron);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Date date = new Date();

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			Date parsedDate = dateFormat.parse("2011-10-02 18:48:05.123");
			Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			date = timestamp;
			System.out.println(timestamp.toString());
		} catch (Exception e) { // this generic but you can control another types of exception
			// look the origin of excption
		}
		CronUtil.createCronExpression(date);
	}
}
