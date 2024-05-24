package com.alura.literatura.model;

public enum Language {
  EN("en"),
  ES("es"),
  FR("fr"),
  DE("de");

  private String code;

  Language(String  code) {
    this.code = code;
  }

  public static Language fromCode(String code) {
    for (Language language : values()) {
      if (language.code.equals(code)) {
        return language;
      }
    }
    throw new IllegalArgumentException("Unknown language code: " + code);
  }
}
