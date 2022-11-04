package com.kevinbatchelor.dao;

import com.kevinbatchelor.model.GifDetail;

import java.util.List;

public interface GiphyDao {

    //CRUD
    GifDetail saveGiphy(GifDetail gif);  // CREATE

    List<GifDetail> getAllGiphys();  // READ

    GifDetail getGiphyById(int id); // READ one from db

}
