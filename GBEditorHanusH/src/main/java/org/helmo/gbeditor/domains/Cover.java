package org.helmo.gbeditor.domains;


import java.util.regex.Pattern;

/**
 * Class Cover : Cover of the GameBook (Title, ISBN, Resume)
 */
public class Cover {


    private final String title;
    private final String isbn;
    private final String resume;
    private static final String ISBN_PATTERN = "([\\d]{1}-[\\d]{6}-[\\d]{2}-[\\d|xX])";
    private static final char X_VALUE = 'X';


    /***
     * Cover Constructor
     * @param title Title of the GameBook
     * @param isbn Isbn of the GameBook
     * @param resume Resume of the GameBook
     */
    public Cover(String title, String isbn, String resume) {
        this.title = title;
        this.isbn = isbn;
        this.resume = resume;
    }


    public String getIsbn() {
        return isbn;
    }

    public String getResume() {
        return resume;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Format the Cover of the Book
     *
     * @param isPublish boolean if the gamebook is published or not
     * @return String format of the Cover
     */
    public String formatCover(boolean isPublish) {
        return isPublish ? "Publié-" + toDetail() : toDetail();

    }

    private String toDetail() {
        return this.isbn + " || " + this.title;
    }

    /**
     * CheckIsbn Check if the ISBN have a correct pattern
     *
     * @return boolean If correct return true else false
     */
    public boolean checkISBN() {
        if (Pattern.matches(ISBN_PATTERN, isbn)) {
            return verifyISBN(isbn);
        }
        return false;
    }

    private boolean verifyISBN(String ISBN) {
        String temp = ISBN.replace("-", "");
        int length = temp.length();
        int[] isbnArray = createArrayISBNNumber(temp, length);
        return isbnArray[length - 1] == getNumberSecurity(length, isbnArray);
    }

    private static int[] createArrayISBNNumber(String temp, int length) {
        int[] isbnArray = new int[length];
        for (int i = 0; i < isbnArray.length; i++) {
            if ((temp.charAt(i)) == X_VALUE) {
                isbnArray[i] = 10;
            } else {
                isbnArray[i] = Integer.parseInt(temp.substring(i, i + 1));
            }
        }
        return isbnArray;
    }

    private static int getNumberSecurity(int length, int[] isbnArray) {
        int somme = 0;
        for (int j = 0; j < isbnArray.length - 1; j++) {

            somme += isbnArray[j] * (length - j);
        }
        return (11 - (somme % 11)) == 11 ? 0 : (11 - (somme % 11));
    }
}
