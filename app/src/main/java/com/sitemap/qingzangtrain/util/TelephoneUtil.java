package com.sitemap.qingzangtrain.util;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * 权限
 * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
 */

/**
 * com.sitemap.stm.utils
 * 
 * @author chenmeng
 * @Description 电话工具类，手机号、运营商、IMEI、IMSI 获取android手机品牌、商家、版本号等信息
 * @date create at 2016年8月26日 上午11:10:29
 */
public class TelephoneUtil {
	private Context context;
	private static final String TAG = "telephone";
	/** 手机型号 */
	public String TELEPHONE_MODEL;
	/** 手机品牌 */
	public String TELEPHONE_BRAND;
	/** 手机号码 */
	public String TELEPHONE_NUMBER;
	/** 手机的 imei 也是 device_id */
	public String TELEPHONE_IMEI;
	/** 手机的 imsi */
	public String TELEPHONE_IMSI;
	/** 运营商 */
	public String SERVICE_NAME;
	/** 安卓版本 */
	public String ANDROID_VERSION;
	/**UUID */ 
	private static String mUUID = null;
	/**标识符*/ 
	private static final String INSTALLATION = "INSTALLATION";

	public TelephoneUtil(Context context) {
		this.context = context;
		initPhoneMessage();
	}

	/**
	 * 获取手机信息
	 */
	private void initPhoneMessage() {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		TELEPHONE_BRAND = android.os.Build.BRAND.equals("") ? "未知"
				: android.os.Build.BRAND;// 手机品牌
		TELEPHONE_MODEL = android.os.Build.MODEL.equals("") ? "未知"
				: android.os.Build.MODEL; // 手机型号
		ANDROID_VERSION = android.os.Build.VERSION.RELEASE.equals("") ? "未知"
				: android.os.Build.VERSION.RELEASE; // 安卓版本
		TELEPHONE_IMEI = tm.getDeviceId().equals("") ? "未知" : tm.getDeviceId();
	}

	/**
	 * 获取手机唯一标识符uuid
	 * 
	 * @return
	 */
	public String getUUID() {
		if (mUUID == null) {
			File installation = new File(context.getFilesDir(), INSTALLATION);
			try {
				if (!installation.exists())
					writeInstallationFile(installation);
				mUUID = readInstallationFile(installation);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return mUUID;
	}

	/**
	 * 读取保存的uuid
	 * @param installation
	 * @return
	 * @throws IOException
	 */
	private String readInstallationFile(File installation)
			throws IOException {
		RandomAccessFile f = new RandomAccessFile(installation, "r");
		byte[] bytes = new byte[(int) f.length()];
		f.readFully(bytes);
		f.close();
		return new String(bytes);
	}

	/**
	 * 写入文件中
	 * @param installation
	 * @throws IOException
	 */
	private void writeInstallationFile(File installation)
			throws IOException {
		FileOutputStream out = new FileOutputStream(installation);
		String id = UUID.randomUUID().toString();
		out.write(id.getBytes());
		out.close();
	}

	/**
	 * 获取mac地址
	 * 
	 * @return
	 */
	public String getMac() {
		String macSerial = null;
		String str = "";
		try {
			Process pp = Runtime.getRuntime().exec(
					"cat /sys/class/net/wlan0/address ");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			for (; null != str;) {
				str = input.readLine();
				if (str != null) {
					macSerial = str.trim();// 去空格
					break;
				}
			}
		} catch (IOException ex) {
			// 赋予默认值
			ex.printStackTrace();
		}
		return macSerial;
	}

	/**
	 * 调用系统 拨打电话功能
	 * 
	 * @param telephone
	 *            手机号码
	 */
	public void getCallPhone(String telephone) {
		if (TextUtils.isEmpty(telephone)) {
			return;
		}
		Intent intent = new Intent();
		intent.setAction("android.intent.action.DIAL");
		intent.setData(Uri.parse("tel:" + telephone));
		context.startActivity(intent);
	}

	/**
	 * 获取手机号码的归属运营商
	 */
	public String getOperatorName() {
		String type = "";
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String operator = telephonyManager.getSimOperator();
		if (operator != null) {
			if (operator.equals("46000") || operator.equals("46002")) {
				Log.i(TAG, "此卡属于(中国移动)");
				type = "中国移动";
			} else if (operator.equals("46001")) {
				Log.i(TAG, "此卡属于(中国联通)");
				type = "中国联通";
			} else if (operator.equals("46003")) {
				Log.i(TAG, "此卡属于(中国电信)");
				type = "中国电信";
			} else {
				Log.i(TAG, "未知信息");
				type = "未知信息";
			}
		}
		return type;
	}

	/**
	 * 获取手机内存大小
	 * 
	 * @return
	 */
	public String getTotalMemory() {
		String str1 = "/proc/meminfo";// 系统内存信息文件
		String str2;
		String[] arrayOfString;
		long initial_memory = 0;
		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

			arrayOfString = str2.split("\\s+");
			for (String num : arrayOfString) {
				Log.i(str2, num + "\t");
			}

			initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
			localBufferedReader.close();

		} catch (IOException e) {
		}
		return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
	}

	/**
	 * 获取当前可用内存大小
	 * 
	 * @return
	 */
	public String getAvailMemory() {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo mi = new MemoryInfo();
		am.getMemoryInfo(mi);
		return Formatter.formatFileSize(context, mi.availMem);
	}

}
