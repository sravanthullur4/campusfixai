package com.campusfixai.service;

public interface AIProvider {
  String analyze(String prompt);
  String name();
}
