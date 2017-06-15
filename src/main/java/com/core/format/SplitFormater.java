package com.core.format;
/**
 * �ָ�����ʽ��
 * @author κ����
 *
 */
public class SplitFormater extends Formater{
	public SplitFormater(String fields, String express) {
		super(fields, express);
	}
	@Override
	public String format(String row, String field) {
		String[] rows=row.split(token(this.express));
		int index=index(field);
		if(index>-1){
			return rows[index].replaceAll("^\\s+|\\s+$","");
		}
		return null;
	}
	private String token(String express){
		if(express.equals("|")){
			return "\\"+express;
		}
		if(express.equals("s")){
			return "\\"+express+"+";
		}
		return express;
	}
}
