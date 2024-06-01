package org.example.bibliomanager.model.entities;

public class Rent {
    private final int id;
    private final Book book;
    private final User user;
    private final String pickUpDate;
    private final String returnDate;

    public int getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public String getPickUpDate() {
        return pickUpDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public Rent(int id, Book book, User user, String pickUpDate, String returnDate ) {
        this.id = id;
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
