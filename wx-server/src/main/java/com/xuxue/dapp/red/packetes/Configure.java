package com.xuxue.dapp.red.packetes;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 14:40
 * 这里放置各种配置数据
 */
public class Configure {
//这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
	// 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
	// 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改

	private static String key = "";//"12D44F5CAF36461CB65A748858724765";

	//微信分配的公众号ID（开通公众号之后可以获取到）
	private static String appID = "";//"wx7c9b890c6f6e58ae";//"wxf71981f73a634851";//"wx72e19478492eb4e2";
	
	//微信分配的公众号应用密钥（开通公众号之后可以获取到）
	private static String appSecret = "";//"e0c05cf01dff00de3843a6bfa3cfcb38";//"e492ebfb1efe26d2f4709dfd5c8d8366";//"88d8885d62a62d1e0b5d1fdf4d4908c5";

	//微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	private static String mchID = "";//"1255547401";//"1237991202";

	//受理模式下给子商户分配的子商户号
	private static String subMchID = "";

	//HTTPS证书的本地路径
	private static String certLocalPath = "";//"/com/hnxm/coin/util/wx/config/apiclient_cert.p12";

	//HTTPS证书密码，默认密码等于商户号MCHID
	private static String certPassword = "";//"1255547401";//"1237991202";

	//是否使用异步线程的方式来上报API测速，默认为异步模式
	private static boolean useThreadToDoReport = true;

	/**
	 * 发送模板消息.
	 * 
	 */
	public static String SEND_MSG_TEMPLATE_API = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";	
	
	//获取用户基本信息（包括UnionID机制） ?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
	public static String USER_INFO_API = "https://api.weixin.qq.com/cgi-bin/user/info";
	
	//获取access token ?grant_type=client_credential&appid=APPID&secret=APPSECRET
	public static String ACCESS_TOKEN_API = "https://api.weixin.qq.com/cgi-bin/token";
	
	public static String access_token = "";
	public static String jsapi_ticket = "";
	public static final String oauth2_redirect_url = "/manage/zldy.html"; //"hb.jsp";
	public static final String oauth2_redirect_url_zqxxList = "/manage/zqxxList.html"; //"hb.jsp";
	/**
	 * ?appid=wx520c15f417810387
	 * &redirect_uri=http%3A%2F%2Fchong.qq.com%2Fphp%2Findex.php%3Fd%3D%26c%3DwxAdapter%26m%3DmobileDeal%26showwxpaytitle%3D1%26vb2ctag%3D4_2030_5_1194_60
	 * &response_type=code
	 * &scope=snsapi_base
	 * &state=123#wechat_redirect
	 */
	public static String AUTHORIZE_API = "https://open.weixin.qq.com/connect/oauth2/authorize";
	/**
	 * ?appid=APPID
	 * &secret=SECRET
	 * &code=CODE
	 * &grant_type=authorization_code
	 */
	public static String OAUTH2_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	/**
	 * ?access_token=ACCESS_TOKEN
	 * &openid=OPENID
	 * &lang=zh_CN
	 */
	public static String OAUTH2_USERINFO = "https://api.weixin.qq.com/sns/userinfo";
	
	/**
	 * ?appid=APPID
	 * &grant_type=refresh_token
	 * &refresh_token=REFRESH_TOKEN
	 */
	public static String OAUTH2_REFRESH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
	
	/**
	 * https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack
	 * <xml>
            <sign></sign> 签名
            <mch_billno></mch_billno>
            <mch_id></mch_id>
            <wxappid></wxappid>
            <nick_name></nick_name>
            <send_name></send_name>
            <re_openid></re_openid>
            <total_amount></total_amount>
            <min_value></min_value>
            <max_value></max_value>
            <total_num></total_num>
            <wishing></wishing>
            <client_ip></client_ip>
            <act_name></act_name>
            <act_id></act_id>
            <remark></remark>
            <logo_imgurl></logo_imgurl>
            <share_content></share_content>
            <share_url></share_url>
            <share_imgurl></share_imgurl>
            <nonce_str></nonce_str>
        </xml>
	 * @return
	 */
	public static String SENDREDPACK_API = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
	
	/**
	 * <xml>
 		<sign><![CDATA[E1EE61A91C8E90F299DE6AE075D60A2D]]></sign>
 		<mch_billno><![CDATA[0010010404201411170000046545]]></mch_billno>
 		<mch_id><![CDATA[10000097]]></mch_id>
 		<appid><![CDATA[wxe062425f740c30d8]]></appid>
 		<bill_type><![CDATA[MCHT]]></ bill_type> 
		<nonce_str><![CDATA[50780e0cca98c8c8e814883e5caa672e]]></nonce_str>
 		</xml> 
	 */
	public static String QUERY_RED_PACK_API = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo";
	/**
	 * ?type=jsapi
	 * &access_token=ACCESS_TOKEN
	 */
	public static String JS_TICKET = "http://api.weixin.qq.com/cgi-bin/ticket/getticket";
	/**
	 * 企业付款接口
	 * <xml> 
		<mch_appid>wxe062425f740c30d8</mch_appid> 
		<mchid>10000098</mchid> 
		<nonce_str>3PG2J4ILTKCH16CQ2502SI8ZNMTM67VS</nonce_str> 
		<partner_trade_no>100000982014120919616</partner_trade_no> 
		<openid>ohO4Gt7wVPxIT1A9GjFaMYMiZY1s</openid> 
		<check_name>OPTION_CHECK</check_name> 
		<re_user_name>张三</re_user_name> 
		<amount>100</amount> 
		<desc>节日快乐!</desc> 
		<spbill_create_ip>10.2.3.10</spbill_create_ip> 
		<sign>C97BDBACF37622775366F38B629F45E3</sign> 
	  </xml>
	 */
	public static String TRANSFERS_API = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
	
	public static boolean isUseThreadToDoReport() {
		return useThreadToDoReport;
	}

	public static void setUseThreadToDoReport(boolean useThreadToDoReport) {
		Configure.useThreadToDoReport = useThreadToDoReport;
	}

	public static String HttpsRequestClassName = "com.tencent.common.HttpsRequest";

	public static void setKey(String key) {
		Configure.key = key;
	}

	public static void setAppID(String appID) {
		Configure.appID = appID;
	}

	public static void setMchID(String mchID) {
		Configure.mchID = mchID;
	}

	public static void setSubMchID(String subMchID) {
		Configure.subMchID = subMchID;
	}

	public static void setCertLocalPath(String certLocalPath) {
		Configure.certLocalPath = certLocalPath;
	}

	public static void setCertPassword(String certPassword) {
		Configure.certPassword = certPassword;
	}


	public static String getKey(){
		return key;
	}
	
	public static String getAppid(){
		return appID;
	}
	
	public static String getMchid(){
		return mchID;
	}

	public static String getSubMchid(){
		return subMchID;
	}
	
	public static String getCertLocalPath(){
		return certLocalPath;
	}
	
	public static String getCertPassword(){
		return certPassword;
	}


	public static void setHttpsRequestClassName(String name){
		HttpsRequestClassName = name;
	}

	public static String getAppSecret() {
		return appSecret;
	}

	public static void setAppSecret(String appSecret) {
		Configure.appSecret = appSecret;
	}

}
