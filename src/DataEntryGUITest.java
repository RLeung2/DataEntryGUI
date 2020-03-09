import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextField;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

public class DataEntryGUITest {
    JFXPanel panel = new JFXPanel();

    TextField testName1 = new TextField();
    TextField testName2 = new TextField();
    TextField testName3 = new TextField();
    TextField testPhone1 = new TextField();
    TextField testPhone2 = new TextField();
    TextField testPhone3 = new TextField();

    @Before
    public void setUp(){
        panel = new JFXPanel();

        testName1 = new TextField();
        testName2 = new TextField();
        testName3 = new TextField();
        testPhone1 = new TextField();
        testPhone2 = new TextField();
        testPhone3 = new TextField();
    }

    @After
    public void tearDown(){
        panel = null;

        testName1 = null;
        testName2 = null;
        testName3 = null;
        testPhone1 = null;
        testPhone2 = null;
        testPhone3 = null;
    }

    @org.junit.Test
    public void testMakePrompt() {
        // Test name prompt
        DataEntryGUI.makePrompt(testName1, "Name");
        assertEquals("Name", testName1.getPromptText());

        // Test phone prompt
        DataEntryGUI.makePrompt(testPhone1, "(###) ###-####");
        assertEquals("(###) ###-####", testPhone1.getPromptText());
    }

    @org.junit.Test
    public void testValidateName() {
        testName1.setText("jon snow");  // not capitalized first letters
        boolean name1Validity = DataEntryGUI.validateName(testName1);
        assertFalse(name1Validity);

        testName2.setText("Kobe Bryanttttttttttttttttttttttt");  // name greater than 20 characters
        boolean name2Validity = DataEntryGUI.validateName(testName2);
        assertFalse(name2Validity);

        testName3.setText("Kobe Bryant");
        boolean name3Validity = DataEntryGUI.validateName(testName3);
        assertTrue(name3Validity);

        TextField testName4 = new TextField();
        testName4.setText("Jon Snow");
        boolean name4Validity = DataEntryGUI.validateName(testName4);
        assertTrue(name4Validity);
    }

    @org.junit.Test
    public void testValidatePhone() {
        testPhone1.setText("(123)456-7890"); // no space after parenthesis
        boolean phone1Validity = DataEntryGUI.validatePhone(testPhone1);
        assertFalse(phone1Validity);

        testPhone2.setText("(123) 456-78901"); // extra number
        boolean phone2Validity = DataEntryGUI.validatePhone(testPhone2);
        assertFalse(phone2Validity);

        testPhone3.setText("(123) 456-7890");
        boolean phone3Validity = DataEntryGUI.validatePhone(testPhone3);
        assertTrue(phone3Validity);

        TextField testPhone4 = new TextField();
        testPhone4.setText("(212) 200-1999");
        boolean phone4Validity = DataEntryGUI.validatePhone(testPhone4);
        assertTrue(phone4Validity);
    }

    @org.junit.Test
    public void testColorText() {
        // Test coloring invalid name
        testName1.setText("jon snow");  // not capitalized first letters
        DataEntryGUI.colorText(testName1, false);  // should color red if invalid (false)
        assertEquals("-fx-text-fill: red;", testName1.getStyle());

        // Test coloring valid name
        testName2.setText("Jon Snow");
        DataEntryGUI.colorText(testName2, true);  // should color black if valid (true)
        assertEquals("-fx-text-fill: black;", testName2.getStyle());

        // Test coloring invalid Phone
        testPhone1.setText("(123)456-7890"); // no space after parenthesis
        DataEntryGUI.colorText(testPhone1, false);  // should color red
        assertEquals("-fx-text-fill: red;", testPhone1.getStyle());

        // Test coloring valid Phone
        testPhone2.setText("(123) 456-7890"); // no space after parenthesis
        DataEntryGUI.colorText(testPhone2, true);  // should color black
        assertEquals("-fx-text-fill: black;", testPhone2.getStyle());
    }

    // Test "Create Profiles" button disability
    @org.junit.Test
    public void testIsDisabled(){
        testName1.setText("");  // empty text field

        testName2.setText("Kobe Bryant");

        testName3.setText("Bob Lee");

        testPhone1.setText("(123) 456-7890");

        testPhone2.setText("(646) 101-9999");

        testPhone3.setText("(212) 444-3000");

        boolean disabled = DataEntryGUI.isDisabled(testName1, testName2, testName3, testPhone1, testPhone2, testPhone3).get();
        assertTrue(disabled);  // should return true because testName1 has no text, so button should be disabled

        testName1.setText("Jon Snow");  // fill testName1 with text
        disabled = DataEntryGUI.isDisabled(testName1, testName2, testName3, testPhone1, testPhone2, testPhone3).get();
        assertFalse(disabled);  // should return false as the button should be enabled now

    }

    @org.junit.Test
    public void testValidateAll() {
        // Valid text fields: returns true and results in valid pop up window
        testName1.setText("Jon Snow");

        testName2.setText("Kobe Bryant");

        testName3.setText("Bob Lee");

        testPhone1.setText("(123) 456-7890");

        testPhone2.setText("(646) 101-9999");

        testPhone3.setText("(212) 444-3000");

        testName1.setStyle("-fx-text-fill: black;");
        testName2.setStyle("-fx-text-fill: black;");
        testName3.setStyle("-fx-text-fill: black;");
        testPhone1.setStyle("-fx-text-fill: black;");
        testPhone2.setStyle("-fx-text-fill: black;");
        testPhone3.setStyle("-fx-text-fill: black;");
        assertTrue(DataEntryGUI.validateAll(testName1, testName2, testName3, testPhone1, testPhone2, testPhone3));

        // Invalid text field in testName1: returns false and results in invalid pop up window
        testName1.setText("Jon Snow");

        testName1.setStyle("-fx-text-fill: red;");  // invalid color
        assertFalse(DataEntryGUI.validateAll(testName1, testName2, testName3, testPhone1, testPhone2, testPhone3));
    }

    @org.junit.Test
    public void testLockTextFields() {
        testName1.setText("Jon Snow");

        testName2.setText("Kobe Bryant");

        testName3.setText("Bob Lee");

        testPhone1.setText("(123) 456-7890");

        testPhone2.setText("(646) 101-9999");

        testPhone3.setText("(212) 444-3000");

        DataEntryGUI.lockTextFields(testName1, testName2, testName3, testPhone1, testPhone2, testPhone3);
        assertFalse(testName1.isEditable());
        assertFalse(testName2.isEditable());
        assertFalse(testName3.isEditable());
        assertFalse(testPhone1.isEditable());
        assertFalse(testPhone2.isEditable());
        assertFalse(testPhone3.isEditable());
    }

}