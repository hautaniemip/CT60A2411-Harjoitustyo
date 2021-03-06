package com.tite.ct60a2411_harjoitustyo;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Movie implements Serializable {
    private int ID;
    private Date startTime;
    private Date endTime;
    private int eventId;
    private String title;
    private String originalTitle;
    private int year;
    private int length;
    private String rating;
    private int theatreId;
    private String theatreName;
    private String auditorium;
    private String largeImageUrl;

    private float userRating;

    public Movie(int ID, Date startTime, Date endTime, int eventId, String title, String originalTitle, int year, int length, String rating, int theatreId, String theatreName, String auditorium, String largeImageUrl) {
        this.ID = ID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventId = eventId;
        this.title = title;
        this.originalTitle = originalTitle;
        this.year = year;
        this.length = length;
        this.rating = rating;
        this.theatreId = theatreId;
        this.theatreName = theatreName;
        this.auditorium = auditorium;
        this.largeImageUrl = largeImageUrl;
    }

    public Movie(String[] data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        try {
            this.ID = Integer.parseInt(data[0]);
            this.startTime = dateFormat.parse(data[1]);
            this.endTime = dateFormat.parse(data[2]);
            this.eventId = Integer.parseInt(data[3]);
            this.title = data[4];
            this.originalTitle = data[5];
            this.year = Integer.parseInt(data[6]);
            this.length = Integer.parseInt(data[7]);
            this.rating = data[8];
            this.theatreId = Integer.parseInt(data[9]);
            this.theatreName = data[10];
            this.auditorium = data[11];
            this.largeImageUrl = data[12];
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Prints info about movie
    public void printMovieInfo() {
        System.out.println(this);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(int theatreId) {
        this.theatreId = theatreId;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public String getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(String auditorium) {
        this.auditorium = auditorium;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    @NonNull
    @Override
    public String toString() {
        return "Movie{" +
                "ID=" + ID +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", eventId=" + eventId +
                ", tittle='" + title + '\'' +
                ", originalTittle='" + originalTitle + '\'' +
                ", year=" + year +
                ", length=" + length +
                ", rating='" + rating + '\'' +
                ", theatreId=" + theatreId +
                ", theatreName='" + theatreName + '\'' +
                ", auditorium='" + auditorium + '\'' +
                ", largeImageUrl='" + largeImageUrl + '\'' +
                '}';
    }
}
