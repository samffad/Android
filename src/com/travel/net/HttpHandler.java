package com.travel.net;

import android.app.Activity;

public class HttpHandler {
//	public final static int USER_NAME_EXISTS = 309;// , 注册页面
//	public final static int USER_NAME_ID_MISMATCH = 311;// ,（同时传ＩＤＮａｍｅ不匹配时提示
//	public final static int INVALID_ACCOUNT = 314;// ,登录页面
//	public final static int NO_SUCH_PRODUCT = 316;// ,　产品详情页
//	public final static int USER_ACTION_EXIST = 317;// ,详情预约 标示已经预约　　　
//	public final static int USER_AUTH_FAIL = 318;// ,　所有ｔｏｋｅｎ校验
//	public final static int SYSTEM_EROER=350; //
//	public final static int NO_RESULT=205;  //没有返回结果
//	public final static int REQUEST_DENIED=325;
//	
//	public final static int MISSING_USERNAME = 301; // 登录页面（没有用户名）
//	public final static int MISSING_PWD = 305; // 登录页面（没有密码）
//	public final static int MISSING_LOGIN_TIMESTAMP = 306;// , 登录页面（没有登录时间戳）
//	public final static int MISSING_LOGIN_TYPE = 307;// , 登录页面（没有登录类型）
//
//	public final static int WRONG_PWD = 308;// , 登录页面，密码错误
//	
	public final static int SUCCESS = 1;
	public final static int FAIL = 2;
//    public final static int T=9;
//	public  static String CHANNEL="AAAAAA";
    private Activity activity;
	
	/**
	 * Invalid code.
	 *
	 * @param code the code
	 * @param invalid the invalid
	 * @param activity the activity
	 */
	public void InvalidCode(int code, Invalid invalid,Activity activity) {
        this.activity=activity;
        if(code == 0)
        	code = 500;
		if (code < 300) 
		{
			invalid.invalidCallback(SUCCESS);
		} 
		else if (code >= 300 && code < 400) 
		{
			switch (code) 
			{
//			case MISSING_LOGIN_TIMESTAMP:
//				CollectUtils.showNotify(activity,activity.getString(R.string.syserror), true);
//				break;
//			case MISSING_LOGIN_TYPE:
//				CollectUtils.showNotify(activity,"登录类型错误", true);
//				break;
//			case MISSING_PWD:
//				CollectUtils.showNotify(activity,"密码不存在", true);
//				break;
//			case MISSING_USERNAME:
//				CollectUtils.showNotify(activity,"用户名不存在", true);
//				break;
//			case WRONG_PWD:
//				CollectUtils.showNotify(activity,"密码错误", true);
//				break;
//			case USER_NAME_EXISTS:
//				CollectUtils.showNotify(activity,"用户名已存在", true);
//				break;
//			case USER_NAME_ID_MISMATCH:
//				CollectUtils.showNotify(activity,"用户信息不匹配", true);
//				break;
//			case INVALID_ACCOUNT:
//				CollectUtils.showNotify(activity,"账号无效", true);
//				break;
			default:
				break;
			}
//			invalid.invalidCallback(T);
		} 
		else 
		{
			invalid.invalidCallback(FAIL);
//			CollectUtils.showNotify(activity,activity.getString(R.string.syserror), true);
		}
	}
	
	
	/**
	 * 网络差错的回调接口
	 */
	public interface Invalid {
		
		/**
		 * 网络差错的回调方法.
		 *
		 * @param code HTTP返回码
		 */
		public void invalidCallback(int code);
	}
}
