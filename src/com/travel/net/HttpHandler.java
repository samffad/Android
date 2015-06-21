package com.travel.net;

import android.app.Activity;

public class HttpHandler {
//	public final static int USER_NAME_EXISTS = 309;// , ע��ҳ��
//	public final static int USER_NAME_ID_MISMATCH = 311;// ,��ͬʱ���ɣģΣ��岻ƥ��ʱ��ʾ
//	public final static int INVALID_ACCOUNT = 314;// ,��¼ҳ��
//	public final static int NO_SUCH_PRODUCT = 316;// ,����Ʒ����ҳ
//	public final static int USER_ACTION_EXIST = 317;// ,����ԤԼ ��ʾ�Ѿ�ԤԼ������
//	public final static int USER_AUTH_FAIL = 318;// ,�����У������У��
//	public final static int SYSTEM_EROER=350; //
//	public final static int NO_RESULT=205;  //û�з��ؽ��
//	public final static int REQUEST_DENIED=325;
//	
//	public final static int MISSING_USERNAME = 301; // ��¼ҳ�棨û���û�����
//	public final static int MISSING_PWD = 305; // ��¼ҳ�棨û�����룩
//	public final static int MISSING_LOGIN_TIMESTAMP = 306;// , ��¼ҳ�棨û�е�¼ʱ�����
//	public final static int MISSING_LOGIN_TYPE = 307;// , ��¼ҳ�棨û�е�¼���ͣ�
//
//	public final static int WRONG_PWD = 308;// , ��¼ҳ�棬�������
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
//				CollectUtils.showNotify(activity,"��¼���ʹ���", true);
//				break;
//			case MISSING_PWD:
//				CollectUtils.showNotify(activity,"���벻����", true);
//				break;
//			case MISSING_USERNAME:
//				CollectUtils.showNotify(activity,"�û���������", true);
//				break;
//			case WRONG_PWD:
//				CollectUtils.showNotify(activity,"�������", true);
//				break;
//			case USER_NAME_EXISTS:
//				CollectUtils.showNotify(activity,"�û����Ѵ���", true);
//				break;
//			case USER_NAME_ID_MISMATCH:
//				CollectUtils.showNotify(activity,"�û���Ϣ��ƥ��", true);
//				break;
//			case INVALID_ACCOUNT:
//				CollectUtils.showNotify(activity,"�˺���Ч", true);
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
	 * ������Ļص��ӿ�
	 */
	public interface Invalid {
		
		/**
		 * ������Ļص�����.
		 *
		 * @param code HTTP������
		 */
		public void invalidCallback(int code);
	}
}
