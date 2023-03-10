package com.javarush.task.task32.task3209;

import com.javarush.task.task32.task3209.listeners.FrameListener;
import com.javarush.task.task32.task3209.listeners.TabbedPaneChangeListener;
import com.javarush.task.task32.task3209.listeners.UndoListener;

import javax.swing.*;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public class View extends JFrame implements ActionListener {
	private Controller controller;
	private JTabbedPane tabbedPane = new JTabbedPane();
	private JTextPane htmlTextPane = new JTextPane();
	private JEditorPane plainTextPane = new JEditorPane();
	private UndoManager undoManager = new UndoManager();
	private UndoListener undoListener = new UndoListener(undoManager);

	public Controller getController() {
		return controller;
	}

	public View(){
		try	{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException e){
			ExceptionHandler.log(e);
		}
		catch (InstantiationException e){
			ExceptionHandler.log(e);
		}
		catch (IllegalAccessException e){
			ExceptionHandler.log(e);
		}
		catch (UnsupportedLookAndFeelException e){
			ExceptionHandler.log(e);
		}
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()){
			case "Новый":
				controller.createNewDocument();
				break;
			case "Открыть":
				controller.openDocument();
				break;
			case "Сохранить":
				controller.saveDocument();
				break;
			case "Сохранить как...":
				controller.saveDocumentAs();
				break;
			case "Выход":
				controller.exit();
				break;
			case "О программе":
				showAbout();
				break;
		}
	}
	public void init(){
		initGui();
		FrameListener frameListener = new FrameListener(this);
		addWindowListener(frameListener);
		setVisible(true);
	}
	public void exit(){
		controller.exit();
	}
	public void initMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		//Файл, Редактировать, Стиль, Выравнивание, Цвет, Шрифт и Помощь.
		MenuHelper.initFileMenu(this, menuBar);
		MenuHelper.initEditMenu(this, menuBar);
		MenuHelper.initStyleMenu(this, menuBar);
		MenuHelper.initAlignMenu(this, menuBar);
		MenuHelper.initColorMenu(this, menuBar);
		MenuHelper.initFontMenu(this, menuBar);
		MenuHelper.initHelpMenu(this, menuBar);
		getContentPane().add(menuBar, BorderLayout.NORTH);
	}
	public void initEditor(){
		htmlTextPane.setContentType("text/html");
		tabbedPane.add("HTML", new JScrollPane(htmlTextPane));
		tabbedPane.add("Текст", new JScrollPane(plainTextPane));
		tabbedPane.setPreferredSize(new Dimension(800, 800));
		tabbedPane.addChangeListener(new TabbedPaneChangeListener(this));

		getContentPane().add(tabbedPane, BorderLayout.CENTER);
	}
	public void initGui(){
		initMenuBar();
		initEditor();
		pack();
	}
	public void selectedTabChanged(){
		switch (tabbedPane.getSelectedIndex()){
			case 0:
				controller.setPlainText(plainTextPane.getText());
				break;
			case 1:
				plainTextPane.setText(controller.getPlainText());
				break;
		}
		resetUndo();
	}
	public boolean canUndo(){
		return undoManager.canUndo();
	}
	public boolean canRedo(){
		return undoManager.canRedo();
	}
	public void undo(){
		try {
			undoManager.undo();
		}catch (CannotUndoException e){
			ExceptionHandler.log(e);
		}
	}
	public void redo(){
		try {
			undoManager.redo();
		}catch (CannotRedoException e){
			ExceptionHandler.log(e);
		}
	}
	public void resetUndo(){
		undoManager.discardAllEdits();
	}

	public UndoListener getUndoListener() {
		return undoListener;
	}
	public boolean isHtmlTabSelected(){
		return tabbedPane.getSelectedIndex() == 0;
	}
	public void selectHtmlTab(){
		tabbedPane.setSelectedIndex(0);
		resetUndo();
	}
	public void update(){
		htmlTextPane.setDocument(controller.getDocument());
	}
	public void showAbout(){
		JOptionPane.showMessageDialog(this,"HTML Editor", "About", JOptionPane.INFORMATION_MESSAGE);
	}
}
