import javax.swing.*;

import com.drew.imaging.ImageProcessingException;

import java.awt.event.*;
import java.awt.*;
import java.io.IOException;

public class photostat_gui extends JFrame
implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton folder_button;
	private JButton analyze_button;
	private JLabel folder_label;
	private JPanel panel;
	private JFileChooser chooser;
	private String choosertitle;

	public photostat_gui() {
		super("Photo analyzer v0.1");
		setLocation(300,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(5,5));

		//Labels erzeugen
		folder_label = new JLabel("/Users/kargm/Desktop/DCIM");
		//Label zentrieren
		folder_label.setHorizontalAlignment(JLabel.CENTER); 
		panel = new JPanel(new GridLayout(3,1));

		analyze_button = new JButton("Analyze photos");
		folder_button = new JButton("Select folder");
		folder_button.addActionListener(this); 
		analyze_button.addActionListener(this);

		panel.add(folder_label);
		panel.add(folder_button);
		panel.add(analyze_button);

		getContentPane().add(panel);
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		//int result;

		if (e.getSource() == folder_button) {
			chooser = new JFileChooser(); 
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle(choosertitle);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);
			//    
			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
				System.out.println("getCurrentDirectory(): " 
						+  chooser.getCurrentDirectory());
				System.out.println("getSelectedFile() : " 
						+  chooser.getSelectedFile());
				folder_label.setText(chooser.getSelectedFile().toString());
			}
			else {
				System.out.println("No Selection ");
			}
		}
		else{
			analyzer a = new analyzer();
			try {
				a.analyze(folder_label.getText());
			} catch (ImageProcessingException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				System.out.println("Error: Folder not found!");
			}
		}	
	}


	public Dimension getPreferredSize(){
		return new Dimension(400, 200);
	}

	public static void main(String s[]) {
		JFrame frame = new JFrame("");
		photostat_gui panel = new photostat_gui();
		frame.addWindowListener(
				new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				}
				);
	}
}