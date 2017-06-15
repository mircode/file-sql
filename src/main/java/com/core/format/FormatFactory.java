package com.core.format;

import java.nio.file.Paths;

import com.core.Config;

/**
 * ��ʽ������
 * @author κ����
 *
 */
public class FormatFactory {
	public static Formater formater(String fields,String express) throws Exception{
		return formater(fields,express,Config.getScriptDir());
	}
	public static Formater formater(String fields,String express,String config) throws Exception{
		if(express.endsWith(".class")){
			return new ClazzFormater(fields,express.replace(".class",""),config);
		}
		if(express.startsWith("function")){
			return new ScriptFormater(fields,express);
		}
		if(express.endsWith(".js")){
			return new ScriptFormater(fields,Paths.get(config,express).toString());
		}
		if(express.startsWith("~")){
			return new RgxFormater(fields,express.substring(1));
		}
		if(express.equals("json")){
			return new JsonFormater(fields,express);
		}
		if(express.equals("tb")){
			return new TableFormater(fields,"|");
		}
		if(express.equals("csv")){
			return new TableFormater(fields,",");
		}
		return new SplitFormater(fields,express);
	}
	public static void main(String args[]) throws Exception{
		// �ָ���
		Formater formater=FormatFactory.formater("id,user","|");
		String field=formater.format("1|taobao","user");
		System.out.println("split:"+field);
		
		// ����
		formater=FormatFactory.formater("id,user","~(.*?)[|](.*)");
		field=formater.format("1|taobao","user");
		System.out.println("regex:"+field);
		
		// json
		formater=FormatFactory.formater("id,user,segment","json");
		field=formater.format("{id:1,user:'taobao',segment:{name:'PEK'}}","user");
		System.out.println("json:"+field);
				
		String path=Paths.get(System.getProperty("user.dir"),"target/test-classes").toString();
		
		// class�ļ�
		formater=FormatFactory.formater("id,user","Format.class",path);
		field=formater.format("1|taobao","user");
		System.out.println("class file:"+field);
		
		// �ű�
		formater=FormatFactory.formater("id,user","function format(row,field,fields){ var fields=fields.split(','); for(var i in fields){ if(fields[i]==field){ return row.split('|')[i];};};return null;};");
		field=formater.format("1|taobao","user");
		System.out.println("script:"+field);
				
		// js�ļ�
		formater=FormatFactory.formater("id,user","fmt.js",path);
		field=formater.format("1|taobao","user");
		System.out.println("script file:"+field);
	}
}
