package com.example.booksapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Book {
    private String title;

    @SerializedName("author_name")
    private List<String> authorName;

    @SerializedName("first_publish_year")
    private Integer firstPublishYear;

    @SerializedName("key")
    private String key;

    public String getTitle() { return title != null ? title : "Без названия"; }
    public String getAuthors() {
        if (authorName != null && !authorName.isEmpty()) return String.join(", ", authorName);
        return "Автор неизвестен";
    }
    public String getYear() {
        return firstPublishYear != null ? String.valueOf(firstPublishYear) : "";
    }
    public String getKey() { return key != null ? key : title; }
}
