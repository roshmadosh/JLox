package com.hiroshisprojects.lox;

/**
 *  A class for identifying one or more characters in a source file. The string representation
 *  of each "token" is called a lexeme. It also tracks the line number of the token
 *  in its source file so we can provide more descriptive error messages.
 */
public class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    public Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    public String toString() {
        return type + " " + lexeme + " " + literal;
    }

}
