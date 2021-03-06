package com.huangwu.common.json;

import com.huangwu.exception.JSONException;
import com.huangwu.exception.JSONException;

import java.io.*;

public class JSONTokener {

	private long character;
	private boolean eof;
	private long index;
	private long line;
	private char previous;
	private Reader reader;
	private boolean usePrevious;

	/**
	 * Construct a JSONTokener from a Reader.
	 * 
	 * @param reader
	 *            A reader.
	 */
	public JSONTokener(Reader reader) {
		this.reader = reader.markSupported() ? reader : new BufferedReader(reader);
		eof = false;
		usePrevious = false;
		previous = 0;
		index = 0;
		character = 1;
		line = 1;
	}

	/**
	 * Construct a JSONTokener from an InputStream.
	 * 
	 * @param inputStream
	 *            The source.
	 */
	public JSONTokener(InputStream inputStream) throws JSONException {
		this(new InputStreamReader(inputStream));
	}

	/**
	 * Construct a JSONTokener from a string.
	 * 
	 * @param s
	 *            A source string.
	 */
	public JSONTokener(String s) {
		this(new StringReader(s));
	}

	/**
	 * Back up one character. This provides a sort of lookahead capability, so
	 * that you can test for a digit or letter before attempting to parse the
	 * next number or identifier.
	 */
	public void back() throws JSONException {
		if (usePrevious || index <= 0) {
			throw new JSONException("Stepping back two steps is not supported");
		}
		index -= 1;
		character -= 1;
		usePrevious = true;
		eof = false;
	}

	/**
	 * Get the hex value of a character (base16).
	 * 
	 * @param c
	 *            A character between '0' and '9' or between 'A' and 'F' or
	 *            between 'a' and 'f'.
	 * @return An int between 0 and 15, or -1 if c was not a hex digit.
	 */
	public static int dehexchar(char c) {
		if (c >= '0' && c <= '9') {
			return c - '0';
		}
		if (c >= 'A' && c <= 'F') {
			return c - ('A' - 10);
		}
		if (c >= 'a' && c <= 'f') {
			return c - ('a' - 10);
		}
		return -1;
	}

	public boolean end() {
		return eof && !usePrevious;
	}

	/**
	 * Determine if the source string still contains characters that next() can
	 * consume.
	 * 
	 * @return true if not yet at the end of the source.
	 */
	public boolean more() throws JSONException {
		this.next();
		if (end()) {
			return false;
		}
		back();
		return true;
	}

	/**
	 * Get the next character in the source string.
	 * 
	 * @return The next character, or 0 if past the end of the source string.
	 */
	public char next() throws JSONException {
		int c;
		if (usePrevious) {
			usePrevious = false;
			c = previous;
		} else {
			try {
				c = reader.read();
			} catch (IOException exception) {
				throw new JSONException(exception);
			}

			if (c <= 0) { // End of stream
				eof = true;
				c = 0;
			}
		}
		index += 1;
		if (previous == '\r') {
			line += 1;
			character = c == '\n' ? 0 : 1;
		} else if (c == '\n') {
			line += 1;
			character = 0;
		} else {
			character += 1;
		}
		previous = (char) c;
		return previous;
	}

	/**
	 * Consume the next character, and check that it matches a specified
	 * character.
	 * 
	 * @param c
	 *            The character to match.
	 * @return The character.
	 * @throws JSONException
	 *             if the character does not match.
	 */
	public char next(char c) throws JSONException {
		char n = this.next();
		if (n != c) {
			throw syntaxError("Expected '" + c + "' and instead saw '" + n + "'");
		}
		return n;
	}

	/**
	 * Get the next n characters.
	 * 
	 * @param n
	 *            The number of characters to take.
	 * @return A string of n characters.
	 * @throws JSONException
	 *             Substring bounds error if there are not n characters
	 *             remaining in the source string.
	 */
	public String next(int n) throws JSONException {
		if (n == 0) {
			return "";
		}

		char[] chars = new char[n];
		int pos = 0;

		while (pos < n) {
			chars[pos] = this.next();
			if (end()) {
				throw syntaxError("Substring bounds error");
			}
			pos += 1;
		}
		return new String(chars);
	}

	/**
	 * Get the next char in the string, skipping whitespace.
	 * 
	 * @throws JSONException
	 * @return A character, or 0 if there are no more characters.
	 */
	public char nextClean() throws JSONException {
		for (;;) {
			char c = this.next();
			if (c == 0 || c > ' ') {
				return c;
			}
		}
	}

	/**
	 * Return the characters up to the next close quote character. Backslash
	 * processing is done. The formal JSON format does not allow strings in
	 * single quotes, but an implementation is allowed to accept them.
	 * 
	 * @param quote
	 *            The quoting character, either <code>"</code>
	 *            &nbsp;<small>(double quote)</small> or <code>'</code>
	 *            &nbsp;<small>(single quote)</small>.
	 * @return A String.
	 * @throws JSONException
	 *             Unterminated string.
	 */
	public String nextString(char quote) throws JSONException {
		char c;
		StringBuilder sb = new StringBuilder();
		for (;;) {
			c = this.next();
			switch (c) {
			case 0:
			case '\n':
			case '\r':
				throw syntaxError("Unterminated string");
			case '\\':
				c = this.next();
				switch (c) {
				case 'b':
					sb.append('\b');
					break;
				case 't':
					sb.append('\t');
					break;
				case 'n':
					sb.append('\n');
					break;
				case 'f':
					sb.append('\f');
					break;
				case 'r':
					sb.append('\r');
					break;
				case 'u':
					sb.append((char) Integer.parseInt(this.next(4), 16));
					break;
				case '"':
				case '\'':
				case '\\':
				case '/':
					sb.append(c);
					break;
				default:
					throw syntaxError("Illegal escape.");
				}
				break;
			default:
				if (c == quote) {
					return sb.toString();
				}
				sb.append(c);
			}
		}
	}

	/**
	 * Get the text up but not including the specified character or the end of
	 * line, whichever comes first.
	 * 
	 * @param delimiter
	 *            A delimiter character.
	 * @return A string.
	 */
	public String nextTo(char delimiter) throws JSONException {
		StringBuilder sb = new StringBuilder();
		for (;;) {
			char c = this.next();
			if (c == delimiter || c == 0 || c == '\n' || c == '\r') {
				if (c != 0) {
					back();
				}
				return sb.toString().trim();
			}
			sb.append(c);
		}
	}

	/**
	 * Get the text up but not including one of the specified delimiter
	 * characters or the end of line, whichever comes first.
	 * 
	 * @param delimiters
	 *            A set of delimiter characters.
	 * @return A string, trimmed.
	 */
	public String nextTo(String delimiters) throws JSONException {
		char c;
		StringBuilder sb = new StringBuilder();
		for (;;) {
			c = this.next();
			if (delimiters.indexOf(c) >= 0 || c == 0 || c == '\n' || c == '\r') {
				if (c != 0) {
					back();
				}
				return sb.toString().trim();
			}
			sb.append(c);
		}
	}

	private static final String INDEX_OF_STRING1 = "'\"{}";

	/**
	 * 获取下一个花括号之间的值，单双引号之间不算，支持层级花括号
	 * 
	 * @return
	 * @throws JSONException
	 */
	public void nextBrace(StringBuilder sb) throws JSONException {
		int subBraces = 0;
		while (true) {
			char c = this.next();
			switch (INDEX_OF_STRING1.indexOf(c)) {
			case 0:
				sb.append('\'');
				sb.append(nextString('\''));
				sb.append('\'');
				break;
			case 1:
				sb.append('\"');
				sb.append(nextString('\"'));
				sb.append('\"');
				break;
			case 2:
				subBraces++;
				sb.append(c);
				break;
			case 3:
				sb.append(c);
				if (subBraces > 0) {
					subBraces--;
				} else {
                    return;
                }
				break;
			default:
				sb.append(c);
			}
		}
	}

	/**
	 * Get the next value. The value can be a Boolean, Double, Integer,
	 * JSONArray, JSONObject, Long, or String, or the JSONObject.NULL object.
	 * 
	 * @throws JSONException
	 *             If syntax error.
	 * 
	 * @return An object.
	 */
	public Object nextValue() throws JSONException {
		char c = nextClean();
		String string;

		switch (c) {
		case '"':
		case '\'':
			return nextString(c);
		case '{':
			back();
			return new JSONObject(this);
		case '[':
			back();
			return new JSONArray(this);
		}

		/*
		 * Handle unquoted text. This could be the values true, false, or null,
		 * or it can be a number. An implementation (such as this one) is
		 * allowed to also accept non-standard forms.
		 * 
		 * Accumulate characters until we reach the end of the text or a
		 * formatting character.
		 */

		StringBuilder sb = new StringBuilder();
		while (c >= ' ' && INDEX_OF_STRING.indexOf(c) < 0) {
			sb.append(c);
			c = this.next();
		}
		// 处理javascript函数
		if (c == '{') {
			String v = sb.toString().trim();
			if (v.startsWith("function") && v.endsWith(")")) {
				sb.append("{");
				nextBrace(sb);
				return new NoQuoteString(sb.toString());
			}
		} else {
            back();
        }

		string = sb.toString().trim();
		if ("".equals(string)) {
			throw syntaxError("Missing value");
		}
		return JSONObject.stringToValue(string);
	}

	private static final String INDEX_OF_STRING = ",:]}/\\\"[{;=#";

	/**
	 * Skip characters until the next character is the requested character. If
	 * the requested character is not found, no characters are skipped.
	 * 
	 * @param to
	 *            A character to skip to.
	 * @return The requested character, or zero if the requested character is
	 *         not found.
	 */
	public char skipTo(char to) throws JSONException {
		char c;
		try {
			long startIndex = index;
			long startCharacter = character;
			long startLine = line;
			reader.mark(1000000);
			do {
				c = this.next();
				if (c == 0) {
					reader.reset();
					index = startIndex;
					character = startCharacter;
					line = startLine;
					return c;
				}
			} while (c != to);
		} catch (IOException exception) {
			throw new JSONException(exception);
		}
		back();
		return c;
	}

	/**
	 * Make a JSONException to signal a syntax error.
	 * 
	 * @param message
	 *            The error message.
	 * @return A JSONException object, suitable for throwing
	 */
	public JSONException syntaxError(String message) {
		return new JSONException(message + toString());
	}

	/**
	 * Make a printable string of this JSONTokener.
	 * 
	 * @return " at {index} [character {character} line {line}]"
	 */
	@Override
	public String toString() {
		return " at " + index + " [character " + character + " line " + line + "]";
	}
}