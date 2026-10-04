package com.campusfixai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.net.URI; import java.net.http.*; import java.time.Duration;

@Component
public class HttpAIProviders {
  private final HttpClient client=HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(15)).build();
  @Value("${campusfix.ai.openai.api-key:}") String openAiKey; @Value("${campusfix.ai.openai.model:gpt-4.1-mini}") String openAiModel;
  @Value("${campusfix.ai.gemini.api-key:}") String geminiKey; @Value("${campusfix.ai.gemini.model:gemini-2.5-flash}") String geminiModel;
  public String openai(String prompt){ if(openAiKey==null||openAiKey.isBlank()) return ""; String body="{\"model\":\""+esc(openAiModel)+"\",\"messages\":[{\"role\":\"system\",\"content\":\"You are the CampusFix AI orchestrator. Return concise operational reasoning.\"},{\"role\":\"user\",\"content\":\""+esc(prompt)+"\"}]}"; return post("https://api.openai.com/v1/chat/completions",body,"Bearer "+openAiKey); }
  public String gemini(String prompt){ if(geminiKey==null||geminiKey.isBlank()) return ""; String body="{\"contents\":[{\"parts\":[{\"text\":\""+esc(prompt)+"\"}]}]}"; return post("https://generativelanguage.googleapis.com/v1beta/models/"+geminiModel+":generateContent?key="+geminiKey,body,null); }
  private String post(String url,String body,String auth){try{var b=HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(30)).header("Content-Type","application/json"); if(auth!=null)b.header("Authorization",auth); var r=client.send(b.POST(HttpRequest.BodyPublishers.ofString(body)).build(),HttpResponse.BodyHandlers.ofString()); return r.statusCode()<300?r.body():"";}catch(Exception e){return "";}}
  private static String esc(String s){return s.replace("\\","\\\\").replace("\"","\\\"").replace("\n","\\n").replace("\r","\\r");}
}
