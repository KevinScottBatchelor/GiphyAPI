package com.kevinbatchelor.controller;

import com.kevinbatchelor.model.Gif;
import com.kevinbatchelor.model.GifDetail;
import com.kevinbatchelor.service.GiphyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class GiphyController {

    @Autowired
    GiphyService giphyService;

    @GetMapping("/giphy")
    public List<Gif> returnAllGiphys(@RequestParam String query){
        return giphyService.getSearchResults(query);
    }

    @GetMapping("/detail/{id}")
    public GifDetail getDetails(@PathVariable String id){
        return giphyService.getGiphyDetails(id);
    }
}
