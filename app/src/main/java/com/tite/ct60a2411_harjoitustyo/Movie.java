package com.tite.ct60a2411_harjoitustyo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Movie {
    private int ID;
    private Date startTime;
    private Date endTime;
    private String title;
    private String originalTitle;
    private int year;
    private int length;
    private String rating;
    private int theatreId;
    private String theatreName;
    private String auditorium;

    public Movie(int ID, Date startTime, Date endTime, String title, String originalTitle, int year, int length, String rating, String theatreName, String auditorium) {
        this.ID = ID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.originalTitle = originalTitle;
        this.year = year;
        this.length = length;
        this.rating = rating;
        this.theatreName = theatreName;
        this.auditorium = auditorium;
    }

    public Movie(String[] data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        try {
            this.ID = Integer.parseInt(data[0]);
            this.startTime = dateFormat.parse(data[1]);
            this.endTime = dateFormat.parse(data[2]);
            this.title = data[3];
            this.originalTitle = data[4];
            this.year = Integer.parseInt(data[5]);
            this.length = Integer.parseInt(data[6]);
            this.rating = data[7];
            this.theatreId = Integer.parseInt(data[8]);
            this.theatreName = data[9];
            this.auditorium = data[10];
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Prints info about movie
    public void printMovieInfo() {
        System.out.println("Movie id:" + this.ID);
    }

    public int getID() {
        return ID;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public int getYear() {
        return year;
    }

    public int getLength() {
        return length;
    }

    public String getRating() {
        return rating;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public String getAuditorium() {
        return auditorium;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "ID=" + ID +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", tittle='" + title + '\'' +
                ", originalTittle='" + originalTitle + '\'' +
                ", year=" + year +
                ", length=" + length +
                ", rating='" + rating + '\'' +
                ", theatreName='" + theatreName + '\'' +
                ", auditorium='" + auditorium + '\'' +
                '}';
    }
}
