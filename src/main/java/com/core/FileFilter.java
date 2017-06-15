package com.core;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * �ļ���������
 * @author κ����
 *
 */
public class FileFilter {
	public static File[] search(String file,String base){
		List<File> files=new LinkedList<>();
		file=file.replaceAll("([{].*?)(,)(.*?[}])","$1#$3");
		for(String f:file.split(",")){
			files.addAll(FileFilter.search(f.replace("#",","), base, false));
		}
		File[] res=new File[files.size()];
		for(int i=0;i<files.size();i++){
			res[i]=files.get(i);
		}
		return res;
	}
	private static List<File> search(String file,String base,boolean flag){
		
		String separator="/";
		if(file!=null){
			file=file.replace("\\",separator);
		}
		// ���·������
		if(!(file.matches("^[A-Za-z]:.*")||file.matches(separator))){
			if(base==null){
				base=".";
			}
			if(base!=null){
				base=base.replace("\\",separator);
				if(file.startsWith(".")){
					file=file.substring(2);
				}
				file=(base+separator+file).replaceAll("/+",separator);
			}
		}
		
		// ��ȡ·�����ļ���
		String filename=file;
		String basepath=base;
		int endIndex=file.lastIndexOf(separator);
		if(endIndex>-1){
			filename=file.substring(endIndex+1);
			basepath=file.substring(0,endIndex);
		}
		
		List<File> files=new LinkedList<>();
		// �ݹ�����
		if(basepath.endsWith("**")){
			endIndex=basepath.lastIndexOf(separator);
			List<File> fs=search(filename,basepath.substring(0,endIndex),true);
			files.addAll(fs);
		}else{
			Path path=null;
			try{
				path=Paths.get(basepath,filename);
				if(!path.toFile().exists()){
					path=Paths.get(basepath);
				}
			}catch(Exception e){
				path=Paths.get(basepath);
			}
			File ff=path.toFile();
			if(ff.isDirectory()){
				final String fname=filename;
				File[] fs=path.toFile().listFiles(f->f.isFile()&&FileFilter.match(f.getName(),fname));
				files.addAll(Arrays.asList(fs));
				
				if(flag){
					fs=path.toFile().listFiles(f->f.isDirectory());
					for(File f:fs){
						files.addAll(search(filename,f.getAbsolutePath(),true));
					}
				}
			}else{
				if(FileFilter.match(ff.getName(),filename)){
					files.add(ff);
				}
			}
		}
		return files;
		
	}
	private static boolean match(String name,String filter){
		boolean flag=false;
		try{
			if(name.matches(filter)){
				flag=true;
			}
		}catch(Exception e){
			filter=filter.replace(",","|")
						 .replace(".","\\.")
					     .replace("*",".*?")
					     .replace("{","(").replace("}",")");
		
			try{
				if(name.matches(filter)){
					flag=true;
				}
			}catch(Exception ee){
				flag=false;
			}
		}
		return flag;
	}
	public static void main(String args[]){
		
		String ws=System.getProperty("user.dir");
		System.out.println(String.format("current dir:%s",ws));
		
		List<String> test=new ArrayList<>();
		test.add("**/*.java");
		
		test.add("*");
		test.add("*.*");
		test.add("*.{json,txt}");
		test.add("{log,format}.txt");
		test.add("./{taobao,res}.*");
		test.add("E:/�ĵ�/*.txt");
		
		for(String t:test){
			System.out.println("\n"+t+"\n---------------------");
			Stream.of(search(t,null)).forEach(f->System.out.println(f.getName()));
		}
		
		
		
	}
}
