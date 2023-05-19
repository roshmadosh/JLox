package com.hiroshisprojects.lox;

import java.util.ArrayList;
import java.util.List;

import static com.hiroshisprojects.lox.TokenType.*;

class Scanner {
    private final String source;
    private final List<Token> tokens;
    private int start = 0;
    private int current = 0;
    private int line = 1;

    Scanner(String source) {
        this.source = source;
        this.tokens = new ArrayList<>();
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            // single lexemes
            case '{' -> addToken(LEFT_BRACE);
            case '}' -> addToken(RIGHT_BRACE);
            case ';' -> addToken(SEMICOLON);
            case '(' -> addToken(LEFT_PAREN);
            case ')' -> addToken(RIGHT_PAREN);
            case '+' -> addToken(PLUS);
            case '-' -> addToken(MINUS);
            case '*' -> addToken(STAR);
            case ',' -> addToken(COMMA);
            case '.' -> addToken(DOT);

            case '/' -> {
                // for single-line comments, no token added
                if (match('/')) {
                    // BEFORE the newline char
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else {
                    // for division
                    addToken(SLASH);
                }
            }

            // empty space
            case ' ', '\r', '\t' -> { /* do nothing */ };

            // newline
            case '\n' -> line++;

            // strings
            case '"' -> string();

            // operators
            case '!' -> addToken(match('=') ? BANG_EQUAL: BANG);
            case '>' -> addToken(match('=') ? GREATER_EQUAL: GREATER);
            case '<' -> addToken(match('=') ? LESS_EQUAL: LESS);
            case '=' -> addToken(match('=') ? EQUAL_EQUAL: EQUAL);
            default -> {
                if (isDigit(c)) {
                    number();
                } else {
                    Lox.error(line, "Unexpected character '" + c + "'");
                }
            }
        }
    }

    private void number() {
        while (isDigit(peek())) advance();

        if (peek() == '.' && isDigit(peekNext())) {
            // consume '.'
            advance();
            while (isDigit(peek())) advance();
        }

        addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    // For characters whose meaning may depend on the following character
    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }
    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        tokens.add(new Token(type, source.substring(start, current), literal, line));
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }


    private char advance() {
        return source.charAt(current++);
    }

    private boolean isAtEnd() {
        return current > source.length();
    }

    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }

        if (isAtEnd()) {
            Lox.error(line, "Unterminated string.");
            return;
        }

        // consume closing double-quote
        advance();

        String comment = source.substring(start + 1,  current -1);
        addToken(STRING, comment);
    }

}
