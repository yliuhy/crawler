package POJO;

public class Book {
    private String bookName;
    private String author;
    private String publisher;
    private String publicationYear;
    private String pageCount;
    private String price;
    private String isbn;
    private String rating;

    public Book(String bookName, String author, String publisher, String publicationYear, String pageCount, String price, String isbn, String rating) {
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.pageCount = pageCount;
        this.price = price;
        this.isbn = isbn;
        this.rating = rating;
    }

    // Getters for each field
    public String getBookName() { return bookName; }
    public String getAuthor() { return author; }
    public String getPublisher() { return publisher; }
    public String getPublicationYear() { return publicationYear; }
    public String getPageCount() { return pageCount; }
    public String getPrice() { return price; }
    public String getIsbn() { return isbn; }
    public String getRating() { return rating; }
}