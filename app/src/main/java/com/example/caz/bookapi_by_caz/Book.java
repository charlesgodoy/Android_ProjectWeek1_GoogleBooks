package com.example.caz.bookapi_by_caz;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Book implements Serializable {
    private String isbn;
    private String title;
    private String description;
    private String publisher;
    private String year;
    private String selfLink;	// to store the link to the full details of the book
    private String imageLink;
    private String review;
    private boolean read;
    private boolean toRead;




    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isToRead() {
        return toRead;
    }

    public void setToRead(boolean toRead) {
        this.toRead = toRead;
    }


// load book from json


    public Book(JSONObject bookJson){
        // Parse book json
        try{
            this.selfLink = bookJson.getString("selfLink"); // selflink is not inside volumeinfo
        }catch (JSONException e){
            e.printStackTrace();
        }
        JSONObject volumeInfo = null;
        try{
            volumeInfo = bookJson.getJSONObject("volumeInfo");
        }catch (JSONException e){
            e.printStackTrace();
        }
        try{
            this.title = volumeInfo.getString("title");//title is inside volumeinfo
        }catch (JSONException e){
            this.title = "N/A";
            e.printStackTrace();
        }
        try{
            this.publisher = volumeInfo.getString("publisher");
        }catch (JSONException e){
            this.publisher = "N/A";
            e.printStackTrace();
        }
        try{
            this.year = volumeInfo.getString("publishedDate");
        }catch (JSONException e){
            this.year = "N/A";
            e.printStackTrace();
        }
        try{
            this.description = volumeInfo.getString("description");
        }catch (JSONException e){
            this.description = "N/A";
            e.printStackTrace();
        }


        // get the image

        JSONObject imageLinks = null;
        try{
            imageLinks = volumeInfo.getJSONObject("imageLinks");
        }catch (JSONException e){
            e.printStackTrace();
        }
        try{
            if(imageLinks!=null){
                this.imageLink = imageLinks.getString("thumbnail");
            }else{
                this.imageLink = "N/A";
            }

        }catch (JSONException e){
            this.imageLink = "N/A";
            e.printStackTrace();
        }

        // ISBN is inside industryidentifiers
        JSONArray industryIdentifiers = null;
        try{
            industryIdentifiers = volumeInfo.getJSONArray("industryIdentifiers"); //its an array
        }catch (JSONException e){
            e.printStackTrace();
        }
        try{

            this.isbn = industryIdentifiers.getJSONObject(0).getString("identifier");
        }catch (JSONException e){
            e.printStackTrace();
        }


        // fields with default values
        this.review = "";
        this.read = false;

    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", publisher='" + publisher + '\'' +
                ", year='" + year + '\'' +
                ", selfLink='" + selfLink + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", review='" + review + '\'' +
                ", read=" + read +
                ", toRead=" + toRead +
                '}';
    }

    public Book(){

    }
}