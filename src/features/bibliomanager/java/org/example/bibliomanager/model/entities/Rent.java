package org.example.bibliomanager.model.entities;

import java.sql.Date;
import java.time.LocalDate;

public class Rent {
    private int id;
    private final Book book;
    private final User user;
    private final Date pickUpDate;
    private final Date returnDate;

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
