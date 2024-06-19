package com.littlegiants.android.edutree;

import java.util.Enumeration;
import java.util.Properties;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.Point;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Display;

public class DeviceInformation {
	public static boolean isConnectedToMobileInternet(Context c) {
		// mobile
		State mobile = ((ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		return mobile == State.CONNECTED
				|| mobile == State.CONNECTING;
	}

	public static boolean isInternetAvailable(Context c) {
		ConnectivityManager connectivityManager = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

	/**
	 * use {@link SystemUtil#isGlass()} instead
	 * 
	 * @return
	 */
	@Deprecated
	public static boolean isGlass() {
		return SystemUtil.isGlass();
	}

	public static String getInfosAboutDevice(Activity a) {
		String s = "";
		try {
			PackageInfo pInfo = a.getPackageManager().getPackageInfo(
					a.getPackageName(), PackageManager.GET_META_DATA);
			s += "\n APP Package Name: " + a.getPackageName();
			s += "\n APP Version Name: " + pInfo.versionName;
			s += "\n APP Version Code: " + pInfo.versionCode;
			s += "\n";
		} catch (NameNotFoundException e) {
		}
		s += "\n OS Version: " + System.getProperty("os.version") + " ("
				+ Build.VERSION.INCREMENTAL + ")";
		s += "\n OS API Level: " + Build.VERSION.SDK;
		s += "\n Device: " + Build.DEVICE;
		s += "\n Model (and Product): " + Build.MODEL + " ("
				+ Build.PRODUCT + ")";
		// TODO add application version!

		// more from
		// http://developer.android.com/reference/android/os/Build.html :
		s += "\n Manufacturer: " + Build.MANUFACTURER;
		s += "\n Other TAGS: " + Build.TAGS;

		s += "\n screenWidth: "
				+ a.getWindow().getWindowManager().getDefaultDisplay()
						.getWidth();
		s += "\n screenHeigth: "
				+ a.getWindow().getWindowManager().getDefaultDisplay()
						.getHeight();
		s += "\n Keyboard available: "
				+ (a.getResources().getConfiguration().keyboard != Configuration.KEYBOARD_NOKEYS);

		s += "\n Trackball available: "
				+ (a.getResources().getConfiguration().navigation == Configuration.NAVIGATION_TRACKBALL);
		s += "\n SD Card state: " + Environment.getExternalStorageState();
		Properties p = System.getProperties();
		Enumeration keys = p.keys();
		String key = "";
		while (keys.hasMoreElements()) {
			key = (String) keys.nextElement();
			s += "\n > " + key + " = " + (String) p.get(key);
		}
		return s;
	}

	public static boolean isPositioningViaWifiEnabled(Context context) {
		ContentResolver cr = context.getContentResolver();
		String enabledProviders = Settings.Secure.getString(cr,
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (!TextUtils.isEmpty(enabledProviders)) {
			// not the fastest way to do that :)
			String[] providersList = TextUtils.split(enabledProviders, ",");
			for (String provider : providersList) {
				if (LocationManager.NETWORK_PROVIDER.equals(provider)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isScreenOn(Context context) {
		return ((PowerManager) context.getSystemService(Context.POWER_SERVICE))
				.isScreenOn();
	}

	public static boolean isConnectedToWifi(Context c) {
		// via vwifi
		State wifi = ((ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		return wifi == State.CONNECTED
				|| wifi == State.CONNECTING;
	}

	/**
	 * @return true if the current thread is the UI thread
	 */
	public static boolean isUiThread() {
		return Looper.getMainLooper().getThread() == Thread.currentThread();
	}

	/**
	 * @param a
	 * @return the size with size.x=width and size.y=height
	 */
	public static Point getScreenSize(Activity a) {
		return getScreenSize(a.getWindowManager().getDefaultDisplay());
	}

	@SuppressLint("NewApi")
	public static Point getScreenSize(Display d) {
		Point size = new Point();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			d.getSize(size);
		} else {
			size.x = d.getWidth();
			size.y = d.getHeight();
		}
		return size;
	}

}
