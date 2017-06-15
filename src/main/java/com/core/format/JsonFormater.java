package com.core.format;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
/**
 * json��ʽ��
 * @author κ����
 *
 */
public class JsonFormater extends Formater{
	public JsonFormater(String field, String express) {
		super(field, express);
	}
	@Override
	public String format(String row, String field) {
		JSONObject mapper=(JSONObject) JSONValue.parse(row);
		Object obj=mapper.get(field);
		return obj==null?null:obj.toString().replaceAll("^\\s+|\\s+$","");
	}
}
