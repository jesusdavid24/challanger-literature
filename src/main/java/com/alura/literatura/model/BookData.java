package com.alura.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(
  String title,
  List<AuthorsData> authors,
  List<String> languages,
  @JsonAlias("download_count") int downloadCount
) {}
