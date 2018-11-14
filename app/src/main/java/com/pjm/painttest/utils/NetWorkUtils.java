package com.pjm.painttest.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//4G为LTE，联通的3G为UMTS或HSDPA，电信的3G为EVDO，移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
public class NetWorkUtils {

	/**
	 * 没有连接网络
	 */
	public static final int NETWORK_NONE = -1;
	/**
	 * 移动网络
	 */
	public static final int NETWORK_MOBILE = 0;
	/**
	 * wifi网络
	 */
	public static final int NETWORK_WIFI = 1;
    /**
     * 2G
     */
	public static final int NETWORK_2_G = 2;
    /**
     * 3G
     */
	public static final int NETWORK_3_G = 3;
    /**
     * 4G
     */
	public static final int NETWORK_4_G = 4;


	/**
	 * 检测网络是否连接
	 */
	public static boolean isNetConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm != null){
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				Network[] networks = cm.getAllNetworks();
				NetworkInfo networkInfo;
				for (Network mNetwork : networks) {
					networkInfo = cm.getNetworkInfo(mNetwork);
					if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
						return true;
					}
				}
			} else {
				NetworkInfo[] info = cm.getAllNetworkInfo();
				if (info != null) {
					for (NetworkInfo ni : info) {
						if (ni.isConnected()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}


	public static int getNetWorkState(Context mContext) {
		if (mContext == null) {
			throw new UnsupportedOperationException("please use NetUtils before init it");
		}
        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivityManager != null){
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                        return NETWORK_WIFI;
                    } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                        return getNetWorkType(mContext);
                    }
                } else {
                    return NETWORK_NONE;
                }
            }else{
                //获取所有网络连接的信息
                Network[] networks = connectivityManager.getAllNetworks();
                //通过循环将网络信息逐个取出来
                for (Network network : networks) {
                    //获取ConnectivityManager对象对应的NetworkInfo对象
                    NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                    if (networkInfo.isConnected()) {
                        if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
							return getNetWorkType(mContext);
                        } else {
                            return NETWORK_WIFI;
                        }
                    }
                }
            }
        }
		return NETWORK_NONE;
	}

	//4G为LTE，联通的3G为UMTS或HSDPA，电信的3G为EVDO，移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
    public static int getNetWorkType(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if(telephonyManager != null){

            switch (telephonyManager.getNetworkType()) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return NETWORK_2_G;

                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return NETWORK_3_G;

                case TelephonyManager.NETWORK_TYPE_LTE:
                    return NETWORK_4_G;

                default:
                    return NETWORK_NONE;
            }
        }
        return NETWORK_NONE;
    }

	/**
	 * 判断当前网络是否是wifi网络
	 * @param context
	 * @return boolean
	 */
	public static boolean isWifi(Context context) {
		return  NETWORK_WIFI == getNetWorkState(context);
	}

	/**
	 * 判断WiFi网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if(mConnectivityManager != null){
                NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (mWiFiNetworkInfo != null) {
                    return mWiFiNetworkInfo.isAvailable();
                }
            }
		}
		return false;
	}

	/**
	 * 判断数据流量是否可用
	 * @param context
	 * @return
	 */
	public  static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(mConnectivityManager != null){
                NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (mMobileNetworkInfo != null) {
                    return mMobileNetworkInfo.isAvailable();
                }
            }
		}
		return false;
	}

    /*
     * @category 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
     * @return
     */
    public static final boolean ping() {
        String result = null;
        try {
            String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuilder stringBuffer = new StringBuilder();
            String content ;
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            L.e("------ping---- 》result content : " + stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            L.d("----result--- > result = " + result);
        }
        return false;
    }
}
