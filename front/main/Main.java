package main;

import java.io.*;

import lexer.*;
import parser.*;

public class Main {
	public static void main(String[] args) throws IOException {
		File f = new File("./front/tests");
		File[] files = f.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.getName().indexOf(".t")!=-1;
			}
		});
		
		
		Lexer lex = new Lexer(files[0]);
		Parser parse = new Parser(lex);
		parse.program();
		
		/*for (File file : files) {
			Lexer lex = new Lexer(file);
			Parser parse = new Parser(lex);
			parse.program();
		}
*/		
		/*Lexer lex = new Lexer();
		Parser parse = new Parser(lex);
		parse.program();
		System.out.write('\n');
		System.out.println("end");*/
	}
}
