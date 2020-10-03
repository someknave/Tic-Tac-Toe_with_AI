class Book {

    private String title;
    private int yearOfPublishing;
    private String[] authors;   
    
    public String getTitle() {
        return title;
    }
    public int getYearOfPublishing() {
        return yearOfPublishing;
    }
    public String[] getAuthors() {
        String[] authorsOut = new String[authors.length];
        System.arraycopy(authors, 0, authorsOut, 0, authors.length);
        return authorsOut;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setYearOfPublishing(int yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }
    public void setAuthors(String[] authors) {
        this.authors = new String[authors.length];
        System.arraycopy(authors, 0, this.authors, 0, authors.length);
    }
    
}
