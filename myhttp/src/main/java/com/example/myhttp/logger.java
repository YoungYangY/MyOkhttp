package com.example.myhttp;


import android.util.Log;

public class logger {
	public final static boolean logFlag = true;

	private final static String tag = "[Y]";
	private static logger sInstance = new logger();

	private logger() {}

	public static void d(Object str) {
		if (logFlag) {
			String name = getFunctionName();
			if (name != null) {
				Log.d(tag, name + " - " + str);
			} else {
				Log.d(tag, str!=null?str.toString():"");
			}
		}
	}
	
	public static void d(String tag, Object str) {
		if (logFlag) {
			String name = getFunctionName();
			if (name != null) {
				Log.d(tag, name + " - " + str);
			} else {
				Log.d(tag, str!=null?str.toString():"");
			}
		}
	}

	public static void e(Exception ex) {
		if (logFlag) {
			if(ex == null){
				Log.e(tag,"");
			}else{
				Log.e(tag, "error", ex);
			}
		}
	}
	
	public static void e(Object str) {
		if (logFlag) {
			String name = getFunctionName();
			if (name != null) {
				Log.e(tag, name + " - " + str);
			} else {
				Log.e(tag, str!=null?str.toString():"");
			}
		}
	}
	
	public static void e(String tag, Object str) {
		if (logFlag) {
			String name = getFunctionName();
			if (name != null) {
				Log.e(tag, name + " - " + str);
			} else {
				Log.e(tag, str!=null?str.toString():"");
			}
		}
	}

	public static void e(String log, Throwable tr) {
		if (logFlag) {
			String line = getFunctionName();
			Log.e(tag, "{Thread:" + Thread.currentThread().getName() + "}"
					+ "[" + line + ":] " + log + "\n", tr);
		}
	}
	
	public static void e(String tag, String log, Throwable tr) {
		if (logFlag) {
			String line = getFunctionName();
			Log.e(tag, "{Thread:" + Thread.currentThread().getName() + "}"
					+ "[" + line + ":] " + log + "\n", tr);
		}
	}

	private static String getFunctionName() {
		if(sInstance == null){
			sInstance = new logger();
		}
		return sInstance.getName();
	}
	
	private String getName(){
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
		if (sts == null) {
			return null;
		}
		for (StackTraceElement st : sts) {
			if (st.isNativeMethod()) {
				continue;
			}
			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}
			if (st.getClassName().equals(this.getClass().getName())) {
				continue;
			}
			return "[ " + Thread.currentThread().getName() + ": "
					+ st.getFileName() + ":" + st.getLineNumber() + " "
					+ st.getMethodName() + " ]";
		}
		return null;
	}

	public static void i(Object str) {
		if (logFlag) {
			String name = getFunctionName();
			if (name != null) {
				Log.i(tag, name + " - " + str);
			} else {
				Log.i(tag, str!=null?str.toString():"");
			}
		}

	}
	
	public static void i(String tag, Object str) {
		if (logFlag) {
			String name = getFunctionName();
			if (name != null) {
				Log.i(tag, name + " - " + str);
			} else {
				Log.i(tag, str!=null?str.toString():"");
			}
		}

	}

	public static void v(Object str) {
		if (logFlag) {
			String name = getFunctionName();
			if (name != null) {
				Log.v(tag, name + " - " + str);
			} else {
				Log.v(tag, str!=null?str.toString():"");
			}
		}
	}
	
	public static void v(String tag, Object str) {
		if (logFlag) {
			String name = getFunctionName();
			if (name != null) {
				Log.v(tag, name + " - " + str);
			} else {
				Log.v(tag, str!=null?str.toString():"");
			}
		}
	}

	public static void w(Object str) {
		if (logFlag) {
			String name = getFunctionName();
			if (name != null) {
				Log.w(tag, name + " - " + str);
			} else {
				Log.w(tag, str!=null?str.toString():"");
			}
		}
	}
	
	public static void w(String tag, Object str) {
		if (logFlag) {
			String name = getFunctionName();
			if (name != null) {
				Log.w(tag, name + " - " + str);
			} else {
				Log.w(tag, str!=null?str.toString():"");
			}
		}
	}
}
