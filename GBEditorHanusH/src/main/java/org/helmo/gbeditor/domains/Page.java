package org.helmo.gbeditor.domains;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Page Of a GameBook
 */
public class Page {
    private transient String textPage;
    private transient int numero;
    private transient List<Choice> choices = new ArrayList<>();

    /**
     * Constructor of a Page
     *
     * @param textPage String text of the page
     * @param numero   Integer number of the Page in the GameBook
     * @param choice   List of Object Choice
     */
    public Page(String textPage, int numero, List<Choice> choice) {
        this.textPage = textPage;
        this.numero = numero;
        this.choices = choice;
    }

    public String getTextPage() {
        return textPage;
    }

    public int getNumero() {
        return numero;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    /**
     * Get the Choice using his text
     *
     * @param text String texte of the Choice
     * @return Object Choice in the Page with the same text or return null
     */
    public Choice getChoiceByText(String text) {
        for (Choice choice : choices) {
            if (choice.getText().equals(text)) {
                return choice;
            }
        }
        return null;
    }

    /**
     * Format the Page to a String for view
     *
     * @return String formatted of the page
     */
    public String toDetail() {
        return "N°: " + numero + " | Texte :" + textPage;
    }

    /**
     * Setter of the Class Page
     *
     * @param textPage String text of the Page
     * @param numero   Integer numero of the Page
     * @param choices  List of Object Choices
     */
    public void editPage(String textPage, int numero, List<Choice> choices) {
        this.textPage = textPage;
        this.numero = numero;
        this.choices = choices;
    }

    /**
     * Add Object Choice in the List of Choice of the Page
     *
     * @param choice Object Choice to add to the List
     */
    public void addChoice(Choice choice) {
        choices.add(choice);
    }

    /**
     * Clear the List of Choice of the Page
     */
    public void clear() {
        choices.clear();
    }

    /**
     * Check if the list of Choices is empty
     *
     * @return Boolean true if the list is empty false if not
     */
    public boolean isEmpty() {
        return choices.isEmpty();
    }
}
