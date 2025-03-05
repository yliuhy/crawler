package POJO;

public class Movie {
    private String title;
    private String year;
    private String category;
    private String stars;

    public Movie(String title, String year, String category, String stars) {
        this.title = title;
        this.year = year;
        this.category = category;
        this.stars = stars;
    }

    // Getters for each field
    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getCategory() {
        return category;
    }

    public String getStars() {
        return stars;
    }
}