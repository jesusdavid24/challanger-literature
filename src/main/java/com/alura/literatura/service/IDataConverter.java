package com.alura.literatura.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface IDataConverter {
  <T> T getData(String json, Class<T> tClass);
}
