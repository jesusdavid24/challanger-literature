package com.alura.literatura.dto;

import com.alura.literatura.model.Authors;

import java.util.List;

public record BookDTO(
  Long id,
  String title,
  List<Authors> authors,
  List<String> languages,
  int downloadCount
) {}
