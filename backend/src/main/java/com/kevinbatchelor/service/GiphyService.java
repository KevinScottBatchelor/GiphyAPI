package com.kevinbatchelor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevinbatchelor.model.Gif;
import com.kevinbatchelor.model.GifDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GiphyService {

    @Value("${api.url}")
    private String apiURL;
    @Value("${api.key}")
    private String apiKey;
    @Value("${api.search.prefix}")
    private String searchPrefix;
    @Value("${api.limit}")
    private String apiLimit;

    public List<Gif>  getSearchResults(String searchString){

        String url = apiURL + apiKey + searchPrefix + searchString + apiLimit;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>("");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;

        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        List<Gif> gifList = new ArrayList<>();

        try {
            jsonNode = objectMapper.readTree(response.getBody());

            JsonNode root = jsonNode.path("data");
            for (int i = 0; i < root.size(); i++){

                String id = root.path(i).path("id").asText();
                String title = root.path(i).path("title").asText();
                String giphyUrl = "https://media.giphy.com/media/" +id + "/giphy.gif";
                Gif gif = new Gif(id, giphyUrl, title);
                gifList.add(gif);
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return gifList;
    }

    public GifDetail getGiphyDetails(String id){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>("");
        ObjectMapper objectMapper = new ObjectMapper();
        String apiUrl = "https://api.giphy.com/v1/gifs/" + id + "?api_key=" + apiKey;
        ResponseEntity<String> response =
                restTemplate.exchange(apiUrl, HttpMethod.GET, httpEntity, String.class);
        JsonNode jsonNode;

        try {
            jsonNode = objectMapper.readTree(response.getBody());
            JsonNode root = jsonNode.path("data");
            String gifId = root.path("id").asText();
            String giphyUrl = "https://media.giphy.com/media/" +id + "/giphy.gif";
            String rating = root.path("rating").asText();
            String description = root.path("user").path("description").asText();
            String title = root.path("title").asText();
            String userName = root.path("username").asText();
            int height = root.path("images").path("preview").path("height").asInt();
            int width = root.path("images").path("preview").path("width").asInt();

            return new GifDetail(giphyUrl, gifId, rating, description, title, userName, height, width);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
