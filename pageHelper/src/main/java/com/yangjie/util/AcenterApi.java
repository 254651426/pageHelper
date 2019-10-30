package com.yangjie.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/AcenterApi")
public class AcenterApi {

	/*
	 * 查询会议列表
	 */
	public PageUtils listPermRoom(String mcu,String page,String username,String row,String mname) throws Exception {
		PageUtils p =new PageUtils();
		TreeMap<String, Object> apiparamsMap = new TreeMap<String, Object>();
		TreeMap<String, String> m = new TreeMap<String, String>();
		List<Map<String, Object>> user = new ArrayList<Map<String, Object>>();
		apiparamsMap.put("method", 900101);
		apiparamsMap.put("AUTH_UID", ResourceBundle.getValue("acenter.username"));
		apiparamsMap.put("AUTH_PASS", ResourceBundle.getValue("acenter.password"));
		apiparamsMap.put("IA_USERID", username); // 用户id
		apiparamsMap.put("IA_ROW", row); // 每页显示的行数
		apiparamsMap.put("IA_PAGE",Integer.parseInt(page)-1); //
		if(mname!=null && !mname.equals("") && !mname.equals("null"))
		{
			apiparamsMap.put("IA_ROOMNAME",mname); //			
		}
		apiparamsMap.put("STATE","3"); //
		JSONObject jsonObject = JSONObject.fromObject(apiparamsMap);
		m.put("data", jsonObject.toString());
		UrlEncodedFormEntity formEntity = HttpClientUtil.getPostFormEntity(m);
		String res = HttpClientUtil
				.doHttpPost("http://" + mcu+":8082"  + "/acenter/dbcall.action?", formEntity);
		JSONObject j = JSONObject.fromObject(res);
		String ret = j.getString("ret");
		String totalPages = j.getString("totalPages");
		String totalcount = j.getString("totalcount");
		p.setTotalPage(Integer.valueOf(totalPages));
		p.setTotalCount(Integer.valueOf(totalcount));
		p.setCurrPage(Integer.parseInt(page));
		if (ret.equals("1")) {
			JSONArray arr_mould = JSONArray.fromObject(j.getString("arr_mould"));
			p.setList(arr_mould);
			return p;
		}
		return p;
	}

	/*
	 *会议登录
	 */
	public String Login(String muc,String username,String password) throws Exception {
		PageUtils p =new PageUtils();
		TreeMap<String, Object> apiparamsMap = new TreeMap<String, Object>();
		TreeMap<String, String> m = new TreeMap<String, String>();
		List<Map<String, Object>> user = new ArrayList<Map<String, Object>>();
		apiparamsMap.put("method", 90001);
		apiparamsMap.put("AUTH_UID", username);
		apiparamsMap.put("AUTH_PASS", password);
		apiparamsMap.put("IA_USERNAME", username);  
		apiparamsMap.put("IA_U_PASSWORD", password);  
		JSONObject jsonObject = JSONObject.fromObject(apiparamsMap);
		m.put("data", jsonObject.toString());
		String ret=null;
		try {
			UrlEncodedFormEntity formEntity = HttpClientUtil.getPostFormEntity(m);
			String res = HttpClientUtil
					.doHttpPost("http://" + muc+":8082" + "/acenter/dbcall.action?", formEntity);
			JSONObject j = JSONObject.fromObject(res);
			ret = j.getString("ret");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "-1";
		}
		return ret;
	}
	
	public static void main(String[] args) throws Exception {
		AcenterApi a = new AcenterApi();
//		List<Long> l = new ArrayList<Long>();
//		l.add((long) 682633);
//		l.add((long) 652745);
//		System.out.println(a.listPermRoom("1"));
	}

}
