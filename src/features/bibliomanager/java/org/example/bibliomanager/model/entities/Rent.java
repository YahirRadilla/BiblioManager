package org.example.bibliomanager.model.entities;

import java.sql.Date;
import java.time.LocalDate;

public class Rent {
    private int id;
    private Book book;
    private User user;
    private String bookName;
    private String userEmail;
    private Date rentDate;
    private final Date pickUpDate;
    private final Date returnDate;
    private boolean returned;

    public Date getRentDate() {
        return rentDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public String getBookName() {
        return bookName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public Rent(int id, Book book, User user, Date pickUpDate, Date returnDate ) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.pickUpDate = pickUpDate;
        this.returnDate = returnDate;
        this.returned = false;
    }

    public Rent(int id, Date pickUpDate, Date rentDate ,Date returnDate, boolean returned, String bookName, String userEmail ) {
        this.id = id;
        this.pickUpDate = pickUpDate;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.returned = returned;
        this.bookName = bookName;
        this.userEmail = userEmail;
    }

    public Rent(Book book, User user, Date pickUpDate, Date returnDate ) {
        this.book = book;
        this.user = user;
        this.pickUpDate = pickUpDate;
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "id=" + id +
                ", book=" + book +
                ", user=" + user +
                ", pickUpDate='" + pickUpDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                '}';
    }
}
