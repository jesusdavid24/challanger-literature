package com.alura.literatura.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiConnector {
  public String fetchData( String url){
    HttpClient client = HttpClient.newBuilder()
      .followRedirects(HttpClient.Redirect.NORMAL)
      .build();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(url))
      .build();

    try {
      HttpResponse<String> response = client
        .send(request, HttpResponse.BodyHandlers.ofString());
      String json = response.body();
      return json;
    } catch (Exception e) {
      throw new RuntimeException("I didn't find that book");
    }
  }
}
