package org.helmo.gbeditor.domains;

/**
 * Class Choice of a page
 * From page number X to Number Y with a text
 */
public class Choice {

    private final int toPage;
    private final int fromPage;

    private final String text;

    /***
     * Choice Construtor
     * @param toPage integer number of the page to go to
     * @param text string text of the choice
     */
    public Choice(int fromPage,int toPage, String text) {
        this.fromPage=fromPage;
        this.toPage = toPage;
        this.text = text;
    }

    public int getFromPage() {
        return fromPage;
    }

    public int getToPage() {
        return this.toPage;
    }

    public String getText() {
        return this.text;
    }

    /**
     * Put in form the choice text to explain to the user to go to a Page
     *
     * @return String formatted String of the Choices
     */
    public String toChoiceOption() {
        return text + "\n--> Vers la page N°:" + toPage;
    }
}
