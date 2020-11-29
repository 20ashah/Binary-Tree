
/*
 Arjun Shah
 B Block
 2/26/2020
 Program Description: 
 This program takes in integers and inserts them
 into a binary tree. This program handles deletions
 from the binary tree as well. 
*/

import javax.swing.*;
import BreezySwing.*;

public class Main extends GBFrame {

	// GUI components
	private JLabel numberLabel;
	private IntegerField numberField;
	private JTextArea outputArea;
	private JButton insertButton;
	private JButton deleteButton;

	// new tree object
	private Tree tree = new Tree();

	// constructor for setting GUI components
	public Main() {
		numberLabel = addLabel("Number", 1, 1, 1, 1);
		numberField = addIntegerField(0, 1, 2, 1, 1);
		insertButton = addButton("Insert", 2, 1, 1, 1);
		deleteButton = addButton("Delete", 2, 2, 1, 1);
		outputArea = addTextArea("", 3, 1, 10, 10);
		numberField.setText("");

	}

	// performing actions when buttons are clicked
	public void buttonClicked(JButton button) {
		if (button == insertButton) { // if user clicks the insert button
			tree.insert(numberField.getNumber());
		} else if (button == deleteButton) { // if user clicks the delete button
			tree.deleteVal(numberField.getNumber());
		}

		// clear fields
		numberField.setText("");
		outputArea.setText(tree + "");
	}

	// main method
	public static void main(String[] args) {
		Main theGUI = new Main();
		theGUI.setSize(600, 400);
		theGUI.setVisible(true);
	}

}
