package com.hiroshisprojects.lox;

public enum TokenType {
    // Single-character tokens
    SEMICOLON, LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, COMMA, DOT,
    PLUS, MINUS, SLASH, STAR,

    // One or two character tokens
    BANG, BANG_EQUAL,
    EQUAL, EQUAL_EQUAL,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,

    // Literals
    IDENTIFIER, STRING, NUMBER,

    // Keywords
    AND, OR, CLASS, IF, ELSE, TRUE, FALSE, FUN, FOR, WHILE, VAR, NIL, PRINT,
    RETURN, SUPER, THIS,

    EOF
}
