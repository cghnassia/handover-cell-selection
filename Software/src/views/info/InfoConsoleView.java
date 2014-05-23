package views.info;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class InfoConsoleView extends JPanel {

	private JTextArea textArea;
	
	public InfoConsoleView() {
		this.textArea = new JTextArea();
		this.getTextArea().setEditable(false);
		
		this.setLayout(new BorderLayout());
		
		JScrollPane scrollPane = new JScrollPane(this.textArea);
		this.add(scrollPane, BorderLayout.CENTER);
		
		this.setOpaque(false);
		
		//Exemple
		this.getTextArea().setText(this.getTextArea().getText() + "eazeaezaeazeazeazeaz\n dazdadzadzadazdazd\n allo allo allo\n dazadazdazdazda");
		this.getTextArea().setText(this.getTextArea().getText() + "eazeaezaeazeazeazeaz\n dazdadzadzadazdazd\n allo allo allo\n dazadazdazdazda");
		this.getTextArea().setText(this.getTextArea().getText() + "eazeaezaeazeazeazeaz\n dazdadzadzadazdazd\n allo allo allo\n dazadazdazdazda");
		this.getTextArea().setText(this.getTextArea().getText() + "eazeaezaeazeazeazeaz\n dazdadzadzadazdazd\n allo allo allo\n dazadazdazdazda");
		this.getTextArea().setText(this.getTextArea().getText() + "eazeaezaeazeazeazeaz\n dazdadzadzadazdazd\n allo allo allo\n dazadazdazdazda");

	}
	
	public JTextArea getTextArea() {
		return this.textArea;
	}
}
