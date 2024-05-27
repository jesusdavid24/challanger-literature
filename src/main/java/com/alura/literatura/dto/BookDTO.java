package com.alura.literatura.dto;

import com.alura.literatura.model.Authors;
import com.alura.literatura.model.Language;

import java.util.List;
import java.util.Set;

public record BookDTO(
  String title,
  Set<Authors> authors,
  List<Language> languages,
  int downloadCount
) {}
