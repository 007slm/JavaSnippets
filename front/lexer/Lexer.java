package lexer;

import java.io.*;
import java.util.*;

import symbols.*;

public class Lexer {
	private FileReader reader;
	public static int line = 1;
	char peek = ' ';
	Hashtable words = new Hashtable();
	
	/**
	 * 记录关键字
	 * @param w
	 */
	void reserve(Word w) {
		words.put(w.lexeme, w);
	}

	public Lexer(File file) {
		try {
			this.reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		reserve(new Word("if", Tag.IF));
		reserve(new Word("else", Tag.ELSE));
		reserve(new Word("while", Tag.WHILE));
		reserve(new Word("do", Tag.DO));
		reserve(new Word("break", Tag.BREAK));

		reserve(Word.True);
		reserve(Word.False);

		reserve(Type.Int);
		reserve(Type.Char);
		reserve(Type.Bool);
		reserve(Type.Float);
	}
	
	
	/**
	 * 从输入设备读取一个字符
	 * @throws IOException
	 */
	void readch() throws IOException {
		peek = (char) reader.read();
	}

	/**
	 * 
	 * @param c
	 * @return
	 * @throws IOException
	 */
	boolean readch(char c) throws IOException {
		readch();
		if (peek != c)
			return false;
		peek = ' ';
		return true;
	}
	
	/**
	 * 词法分析过程
	 * 形成表格
	 * @return
	 * @throws IOException
	 */
	public Token scan() throws IOException {
		
		/**
		 * 读取一个有效字符
		 */
		for (;; readch()) {
			if (peek == ' ' || peek == '\t')
				continue;
			else if (peek == '\n')
				line = line + 1;
			else
				break;
		}
		
		switch (peek) {
		/**
		 * 如果是运算符
		 */
		case '&':
			if (readch('&'))
				return Word.and;
			else
				return new Token('&');
		case '|':
			if (readch('|'))
				return Word.or;
			else
				return new Token('|');
		case '=':
			if (readch('='))
				return Word.eq;
			else
				return new Token('=');
		case '!':
			if (readch('='))
				return Word.ne;
			else
				return new Token('!');
		case '<':
			if (readch('='))
				return Word.le;
			else
				return new Token('<');
		case '>':
			if (readch('='))
				return Word.ge;
			else
				return new Token('>');
		}
		
		/**
		 * 数字判定
		 * 整数和实数
		 */
		if (Character.isDigit(peek)) {
			int v = 0;
			do {
				v = 10 * v + Character.digit(peek, 10);
				readch();
			} while (Character.isDigit(peek));
			if (peek != '.')
				return new Num(v);
			float x = v;
			float d = 10;
			for (;;) {
				readch();
				if (!Character.isDigit(peek))
					break;
				x = x + Character.digit(peek, 10) / d;
				d = d * 10;
			}
			return new Real(x);
		}
		
		/**
		 * 字符情况
		 */
		if (Character.isLetter(peek)) {
			StringBuffer b = new StringBuffer();
			do {
				b.append(peek);
				readch();
			} while (Character.isLetterOrDigit(peek));
			String s = b.toString();
			Word w = (Word) words.get(s);
			if (w != null)
				return w;
			w = new Word(s, Tag.ID);
			words.put(s, w);
			return w;
		}
		Token tok = new Token(peek);
		peek = ' ';
		return tok;
	}
}
