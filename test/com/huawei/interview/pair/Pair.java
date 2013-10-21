package com.huawei.interview.pair;


/**
 * 	用java写一个函数判断字符串中"{"与"}"匹配？ 
	提示："{"与"}"必须同时出现，"{"必须在"}"前面，允许嵌套
 * @author 007slm
 *  大括号 brace
 *  
 *  
 *  逻辑先找到一个right brace 然后向前找left brace
 *  
 *  (做完后其实才想到是考验堆栈的压栈和出栈操作的)
 */
public class Pair {
	private String strCpy;
	public Pair(String str){
		this.strCpy=str;
	}
	
	public String replaceBrace(){
		if(strCpy.contains("}")){
			int indexOfRightBrace=strCpy.indexOf("}");
			int indexOfLeftBrace=strCpy.substring(0, indexOfRightBrace).lastIndexOf("{");
			if(indexOfLeftBrace!=-1){
				//存在成对的匹配TODO
				strCpy=strCpy.replaceFirst("}", "]");
				char[] strCpyArray =strCpy.toCharArray();
				strCpyArray[indexOfLeftBrace]="[".toCharArray()[0];
				strCpy=String.copyValueOf(strCpyArray);
				replaceBrace();
			}else{
				throw new RuntimeException("缺少和}位置在("+indexOfRightBrace+")匹配的{");
			}
		}else{
			if(strCpy.contains("{")){
				throw new RuntimeException("缺少和{位置在("+strCpy.indexOf("{")+")匹配的{");
			}else{
				System.out.println("成功匹配"+strCpy);
				return strCpy;
			}
		}
		return "";
	}
	public static void main(String[] args) {
		String str="{fadsfsad}{fsdafdf}{sdfadf}{sdf{{{{d}sfa}{}}afssf}}";
		Pair p = new Pair(str);
		p.replaceBrace();
	}
}
