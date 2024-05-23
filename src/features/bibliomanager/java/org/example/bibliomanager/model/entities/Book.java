package org.example.bibliomanager.model.entities;

import java.security.Timestamp;

public class Book {
    private final int id;
    private final String title;
    private final String synopsis;
    private final String author;
    private final String category;
    private final String isbn;
    private final String date;
    private final float rating;
    private final int quantity;
    private final String img;

    public String getTitle() {
        return title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getDate() {
        return date;
    }

    public float getRating() {
        return rating;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImg() {
        return img;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", isbn='" + isbn + '\'' +
                ", date='" + date + '\'' +
                ", rating=" + rating +
                ", quantity=" + quantity +
                ", img='" + img + '\'' +
                '}';
    }

    public Book(int id, String title, String synopsis, String author, String category, String isbn, String date, float rating, int quantity, String img) {
        this.id = id;
        this.title = title;
        this.synopsis = synopsis;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.date = date;
        this.rating = rating;
        this.quantity = quantity;
        this.img = img;
    }
}
