package com.wanpg.together.util;

import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;

import com.wanpg.together.base.BaseApplication;


public class DeviceUtil {

    /**
     * 获得android手机的IMEI码，如果是电信手机，在此码后面加上“c”,以补充位数为15位
     *
     * @return 15位的字符串
     */
    public static String getImei() {
        TelephonyManager tm = (TelephonyManager) BaseApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();

        if (imei.length() == 14) {
            imei = imei + "c";
        }

        if (imei.length() == 15) {
            return imei;
        }
        return null;
    }

    /**
     * 获得android手机的IMSI码
     *
     * @return 15位的字符串
     */
    public static String getImsi() {
        TelephonyManager tm = (TelephonyManager) BaseApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);

        return tm.getSubscriberId();
    }

    /**
     * 检查SDCard是否能用
     *
     * @return
     */
    public static boolean isSDCardAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
