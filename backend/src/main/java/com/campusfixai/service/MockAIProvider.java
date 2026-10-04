package com.campusfixai.service;
import org.springframework.stereotype.Component;
@Component("mockAI") public class MockAIProvider implements AIProvider { public String analyze(String prompt){ return "MOCK: deterministic fallback used. The agent pipeline completed using local campus rules."; } public String name(){return "mock";} }
