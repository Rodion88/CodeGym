package com.javarush.task.task32.task3209;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;

public class Controller {
	private View view;
	private HTMLDocument document;
	private File currentFile;

	public static void main(String[] args) {
		View view = new View();
		Controller controller = new Controller(view);
		view.setController(controller);
		view.init();
		controller.init();
	}

	public HTMLDocument getDocument() {
		return document;
	}

	public Controller(View view) {
		this.view = view;
	}
	public void init(){
		createNewDocument();
	}
	public void exit(){
		System.exit(0);
	}
	public void resetDocument(){
		if(document != null) {
			document.removeUndoableEditListener(view.getUndoListener());
		}
			document = (HTMLDocument) new HTMLEditorKit().createDefaultDocument();
			document.addUndoableEditListener(view.getUndoListener());
			view.update();
	}
	public void setPlainText(String text) {

		resetDocument();
		StringReader reader = new StringReader(text);
		try {
			new HTMLEditorKit().read(reader, document, 0);
		} catch (IOException e) {
			ExceptionHandler.log(e);
		} catch (BadLocationException badLocationException) {
			ExceptionHandler.log(badLocationException);
		}
	}
	public String getPlainText(){
		StringWriter writer = new StringWriter();
		try {
			new HTMLEditorKit().write(writer, document, 0, document.getLength());
		}catch (IOException e) {
			ExceptionHandler.log(e);
		} catch (BadLocationException badLocationException) {
			ExceptionHandler.log(badLocationException);
		}
		return writer.toString();
	}
	public void createNewDocument(){
		view.selectHtmlTab();
		resetDocument();
		view.setTitle("HTML редактор");
		currentFile = null;
	}
	public void openDocument(){
		view.selectHtmlTab();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new HTMLFileFilter());
		if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION){
			currentFile = fileChooser.getSelectedFile();
			resetDocument();view.setTitle(currentFile.getName());
			try(FileReader reader = new FileReader(currentFile)){
				new HTMLEditorKit().read(reader, document, 0);
				view.resetUndo();
			}catch (FileNotFoundException fileNotFoundException){
				ExceptionHandler.log(fileNotFoundException);
			}catch (IOException ioException){
				ExceptionHandler.log(ioException);
			}catch (BadLocationException badLocationException){
				ExceptionHandler.log(badLocationException);
			}
		}

	}
	public void saveDocument(){
		view.selectHtmlTab();
		if(currentFile != null){
			view.setTitle(currentFile.getName());
			try(FileWriter writer = new FileWriter(currentFile)){
				new HTMLEditorKit().write(writer, document, 0, document.getLength());
			}
			catch (IOException ioException){
				ExceptionHandler.log(ioException);
			}
			catch (BadLocationException badLocationException){
				ExceptionHandler.log(badLocationException);
			}
		}else {
			saveDocumentAs();
		}
	}
	public void saveDocumentAs(){
		view.selectHtmlTab();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new HTMLFileFilter());
		if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION){
			currentFile = fileChooser.getSelectedFile();
			view.setTitle(currentFile.getName());
			try(FileWriter writer = new FileWriter(currentFile)){
				new HTMLEditorKit().write(writer,document,0, document.getLength());
			}catch (IOException ioException){
				ExceptionHandler.log(ioException);
			}
			catch (BadLocationException badLocationException){
				ExceptionHandler.log(badLocationException);
			}
		}
	}
	
}
