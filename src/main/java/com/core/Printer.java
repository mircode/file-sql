package com.core;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * ��ӡ��
 * ��ʽ��������ӡ
 * 
 * @author κ����
 *
 */
public class Printer {
	
	// ʱ���
	long time=0;
	// ��ӡ��¼����
	long linenums=0;
	// �����С
	int buff_size=500;
	
	// ��ͷ
	String header=null;
	// ����
	List<String> buff=new LinkedList<>();
	
	// �����
	PrintStream out=null;
	
	// �ָ���
	String token="|";
	
	List<String> tmp=new LinkedList<>();
	
	// ���캯��
	public Printer(){
		this(System.currentTimeMillis());
	}
	public Printer(long time){
		this.time=time;
	}
	public Printer(String header){
		this(header,null,System.currentTimeMillis());
	}
	public Printer(String header,String token){
		this(header,token,System.currentTimeMillis());
	}
	public Printer(String header,String token,long time){
		this.header=header;
		this.time=time;
		this.token=token==null?"|":token;
	}
	
	// ��ʽ��ӡ
	public void table(String row){
		this.table(row,null);
	}
	public void table(String row,PrintStream out){
		this.table(header,row,out);
	}
	public void table(String header,String row,PrintStream out){
		this.header=header;
		this.out=out;
		linenums++;
		buff.add(row);
		
		if(buff.size()>=this.buff_size){
			if(linenums<=this.buff_size){
				printbuff(header,buff,out);
			}else{
				printbuff(null,buff,out);
			}
			tmp.clear();
			tmp.addAll(buff);
			buff.clear();
		}
	}
	public void close(){
		if(buff.size()>0){
			if(linenums<=this.buff_size){
				printbuff(header,buff,out);
			}else{
				printbuff(null,buff,out);
			}
			buff.clear();
		}else if(buff.size()==0){
			if(tmp.size()>1){
				tmp=tmp.subList(1, tmp.size());
			}
			printbuff(null,tmp,out,true);
		}
		
		long end=System.currentTimeMillis();
		stdout("%d row in set (%.2f sec)",new Object[]{linenums,(end-time)/1000.0});
	}
	
	// ������ӡ
	public void table(List<String> list){
		this.table(list,header);
	}
	public void table(List<String> list,String header){
		this.header=header;
		if(list.size()>0){
			printbuff(header,list,null);
		}
		long end=System.currentTimeMillis();
		stdout("%d row in set (%.2f sec)",new Object[]{list.size(),(end-time)/1000.0});
	}
	public void table(List<String> list,String header,PrintStream out){
		printbuff(header,list,out);
		if(list.size()>0){
			printbuff(header,list,null);
		}
		long end=System.currentTimeMillis();
		stdout("%d row in set (%.2f sec)",new Object[]{list.size(),(end-time)/1000.0});
	}
	private void printbuff(String header,List<String> lines,PrintStream out){
		printbuff(header,lines,out,false);
	}
	// ��������
	private void printbuff(String header,List<String> lines,PrintStream out,boolean close){
		List<Integer> maxlen=new ArrayList<>();
		
		boolean flag=false;
		if(header!=null){
			lines.add(header);
			flag=true;
		}
		// ����ÿһ�������
		for(String row:lines){
			String args[]=row.split("["+token+"]");
			for(int i=0;i<args.length;i++){
				int len=args[i].length();
				if(maxlen.size()<args.length){
					maxlen.add(len);
				}else{
					Integer max=maxlen.get(i);
					if(max<len){
						maxlen.set(i,len);
					}
				}
			}
		}
		if(flag){
			lines.remove(lines.size()-1);
		}
		
		// �������ݸ�ʽ
		String data_fmt="";
		String line_fmt="";
		List<String> chs=new ArrayList<>();
		for(int i=0;i<lines.get(0).split("["+token+"]").length;i++){
			data_fmt+="|"+"%-"+maxlen.get(i)+"s";
			line_fmt+="+"+"%-"+maxlen.get(i)+"s";
			String res="";
			for(int j=0;j<maxlen.get(i);j++){
				res+="-";
			}
			chs.add(res);
		}
		// ��׼���
		if(header!=null){
			stdout(line_fmt+"+",chs.toArray());
			stdout(data_fmt+"|",header.split("["+token+"]"));
			stdout(line_fmt+"+",chs.toArray());
		}
		if(!close){
			for(String line:lines){
				stdout(data_fmt+"|",line.split("["+token+"]"));
			}
		}
		if(lines.size()<this.buff_size){
			stdout(line_fmt+"+",chs.toArray());
		}
		//�ļ�
		if(header!=null){
			fileout(line_fmt+"+",chs.toArray(),out);
			fileout(data_fmt+"|",header.split("["+token+"]"),out);
			fileout(line_fmt+"+",chs.toArray(),out);
		}
		if(!close){
			for(String line:lines){
				fileout(data_fmt+"|",line.split("["+token+"]"),out);
			}
		}
		if(lines.size()<this.buff_size){
			fileout(line_fmt+"+",chs.toArray(),out);
		}
	}
	// ��׼���
	private void stdout(String format,Object args[]){
		System.out.println(String.format(format, args));
	}
	// �ļ����
	private void fileout(String format,Object args[],PrintStream out){
		if(out!=null){
			out.println(String.format(format, args));
		}
	}
	// ����
	public static void main(String args[]){
		
		// ����
		List<String> list=new ArrayList<>();
		list.add("tabl1|data1");
		list.add("tabl2|data2");
		list.add("tabl3|data3");
		
		// ������ӡ
		new Printer("table_batch|data").table(list);
		
		// ��ʽ��ӡ
		Printer printer=new Printer("table_stream|data");
		list.stream().forEach(row->printer.table(row));
		printer.close();
	}
	
}
