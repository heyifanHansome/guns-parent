package com.stylefeng.guns.modular.lijun.util;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
/** 
 * 说明：参数封装Map
 * 创建人：FH Q313596790
 * 修改时间：2014年9月20日
 * @version
 */
public class PageData extends HashMap implements Map{
	
	private static final long serialVersionUID = 1L;
	
	Map map = null;
	HttpServletRequest request;
	public PageData(HttpServletRequest request){
		this.request = request;
//		System.out.println("uri-------"+request.getRequestURI());
		/*Enumeration<String>parmetNames=request.getParameterNames();
		while (parmetNames.hasMoreElements()) {
			String string = (String) parmetNames.nextElement();
			System.out.println(string+":"+Arrays.toString(request.getParameterValues(string)));
		}*/
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap(); 
		Iterator entries = properties.entrySet().iterator(); 
		Map.Entry entry; 
		String name = "";  
		String value = "";  
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next(); 
			name = (String) entry.getKey(); 
			Object valueObj = entry.getValue();
			if(null == valueObj){ 
				value = ""; 
			}else if(valueObj instanceof String[]){ 
				String[] values = (String[])valueObj;
//				System.out.println(name+":"+values.length);
//				for(int i=0;i<values.length;i++){ 
//					 value = values[i] + ",";
//				}
//				value = value.substring(0, value.length()-1);
				value=StringUtils.join(values, ",");
			}else{
				value = valueObj.toString(); 
			}
			returnMap.put(name, value); 
		}
		/*分页计算动态SQL必须用int类型去计算-----------start*/
		entries = returnMap.entrySet().iterator();
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next(); 
			if(entry.getKey().equals("pageSize")){
				entry.setValue(Integer.valueOf(!Tool.isNull(entry.getValue())?entry.getValue().toString():"10"));
			}
			if(entry.getKey().equals("pageStart")){
				entry.setValue(Integer.valueOf(!Tool.isNull(entry.getValue())&&Integer.valueOf(entry.getValue().toString())<1?"1":!Tool.isNull(entry.getValue())?entry.getValue().toString():"1"));
			}
			try {
				if(entry.getKey().equals("keywords")&&!Tool.isNull(entry.getValue()))entry.setValue(URLDecoder.decode(entry.getValue().toString(), "UTF-8"));
				if(entry.getKey().equals("msg")&&!Tool.isNull(entry.getValue()))entry.setValue(URLDecoder.decode(entry.getValue().toString(), "UTF-8"));
			} catch (Exception e) {
				throw new RuntimeException(e.getCause());
			}
		}
		/*分页计算动态SQL必须用int类型去计算-----------end*/
		map = returnMap;
	}
	
	public PageData() {
		map = new HashMap();
	}
	
	@Override
	public Object get(Object key) {
		Object obj = null;
		if(map.get(key) instanceof Object[]) {
			Object[] arr = (Object[])map.get(key);
			obj = request == null ? arr:(request.getParameter((String)key) == null ? arr:arr[0]);
		} else {
			obj = map.get(key);
		}
		return obj;
	}
	
	public String getString(Object key) {
		return (String)get(key);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object put(Object key, Object value) {
		return map.put(key, value);
	}
	
	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return map.containsValue(value);
	}

	public Set entrySet() {
		// TODO Auto-generated method stub
		return map.entrySet();
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return map.isEmpty();
	}

	public Set keySet() {
		// TODO Auto-generated method stub
		return map.keySet();
	}

	@SuppressWarnings("unchecked")
	public void putAll(Map t) {
		// TODO Auto-generated method stub
		map.putAll(t);
	}

	public int size() {
		// TODO Auto-generated method stub
		return map.size();
	}

	public Collection values() {
		// TODO Auto-generated method stub
		return map.values();
	}
	
}
