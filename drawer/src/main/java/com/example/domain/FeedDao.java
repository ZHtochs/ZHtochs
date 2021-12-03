package com.example.domain;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-12-03 23:35
 **/
@Dao
public interface FeedDao {
    @Insert
    void insertFeed(FeedEntry ... feedEntries);

    @Query("DELETE FROM entry")
    void deleteAllFeeds();

    @Query("SELECT * FROM entry ORDER BY _id")
    List<FeedEntry> getAllFeeds();

}