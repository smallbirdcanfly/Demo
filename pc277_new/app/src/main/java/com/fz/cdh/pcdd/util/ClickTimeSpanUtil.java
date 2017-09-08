package com.fz.cdh.pcdd.util;

public class ClickTimeSpanUtil {
	private static long lastClickTime;

	/**
	 * 是否在默认时间内双击 默认时间为700ms
	 * 
	 * @return
	 */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 700) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/**
	 * 是否在设定时间内双击
	 * 
	 * @param intervalTime
	 *            设定的双击有效时间
	 * @return
	 */
	public static boolean isFastDoubleClick(long intervalTime) {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < intervalTime) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/**
	 * 
	 * @param resumeTime
	 * @param currentTime
	 * @param intervalTime
	 * @return
	 */
	public static boolean isFastWakeClick(long resumeTime, long currentTime,
			long intervalTime) {
		long timeD = currentTime - resumeTime;
		if (100 < timeD && timeD < intervalTime) {
			return true;
		}
		return false;
	}

}
