package testFontChooser;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import appLogger.AppLogger;
import fontChooser.FontChooser;

public class TestFontChooser {

	AppLogger log = AppLogger.getInstance();

	private JFrame frame;
	private JTextPane txtLog;
	private String title = "TestFontChooser     1.0";
	private JSplitPane splitPane;
	private JPanel panelLeft;
	private Component verticalStrut;
	private JButton btnFontChooser;
	private Component verticalStrut_1;
	private JButton btnFillWithText;
	private Component verticalStrut_2;
	private JButton btnNewButton;
	private JLabel lblCurrentFont;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFontChooser window = new TestFontChooser();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}// run
		});
	}// main
	
	private void displayCurrentFont(Font subjectFont) {
		
		
		String style;
		switch (subjectFont.getStyle()) {
		case Font.PLAIN:
			style = "Plain";
			break;
		case Font.BOLD:
			style = "Bold";
			break;
		case Font.ITALIC:
			style = "Italic";
			break;
		case Font.ITALIC | Font.BOLD:
			style = "Bold Italic";
			break;
		default:
			style = "Plain";
		}// switch
		lblCurrentFont.setText(String.format("%s - %d - %s",subjectFont.getFamily(),subjectFont.getSize(),style));
		
	}//displayCurrentFont

	/**
	 * Create the application.
	 */

	private Preferences getPreferences() {
		return Preferences.userNodeForPackage(TestFontChooser.class).node(this.getClass().getSimpleName());
	}// getPreferences

	private void appClose() {
		Preferences myPrefs = getPreferences();
		Dimension dim = frame.getSize();
		myPrefs.putInt("Height", dim.height);
		myPrefs.putInt("Width", dim.width);
		Point point = frame.getLocation();
		myPrefs.putInt("LocX", point.x);
		myPrefs.putInt("LocY", point.y);
		myPrefs.putInt("Divider", splitPane.getDividerLocation());
		myPrefs = null;
	}// appClose

	private void appInit() {
		frame.setBounds(100, 100, 736, 488);

		Preferences myPrefs = getPreferences();
		frame.setSize(myPrefs.getInt("Width", 761), myPrefs.getInt("Height", 693));
		frame.setLocation(myPrefs.getInt("LocX", 100), myPrefs.getInt("LocY", 100));
		splitPane.setDividerLocation(myPrefs.getInt("Divider", 250));
		myPrefs = null;

		log.setDoc(txtLog.getStyledDocument());
		log.addTimeStamp("Starting....");
		displayCurrentFont(txtLog.getFont());

	}// appInit

	public TestFontChooser() {
		initialize();
		appInit();
	}// Constructor

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				appClose();
			}
		});
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		splitPane = new JSplitPane();
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		frame.getContentPane().add(splitPane, gbc_splitPane);

		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);

		lblCurrentFont = new JLabel("Application Log");
		lblCurrentFont.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentFont.setForeground(Color.BLUE);
		lblCurrentFont.setFont(new Font("Times New Roman", Font.BOLD, 18));
		scrollPane.setColumnHeaderView(lblCurrentFont);

		txtLog = new JTextPane();
		txtLog.setFont(new Font("Courier New", Font.PLAIN, 15));
		scrollPane.setViewportView(txtLog);

		panelLeft = new JPanel();
		splitPane.setLeftComponent(panelLeft);
		GridBagLayout gbl_panelLeft = new GridBagLayout();
		gbl_panelLeft.columnWidths = new int[] { 0, 0 };
		gbl_panelLeft.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panelLeft.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panelLeft.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelLeft.setLayout(gbl_panelLeft);

		verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setMaximumSize(new Dimension(0, 0));
		verticalStrut.setPreferredSize(new Dimension(20, 20));
		verticalStrut.setMinimumSize(new Dimension(20, 20));
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 0;
		panelLeft.add(verticalStrut, gbc_verticalStrut);

		btnFontChooser = new JButton("Font Chooser Log's current Font");
		btnFontChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontChooser fc = new FontChooser(frame,txtLog.getFont());
				if(fc.showDialog()== JOptionPane.OK_OPTION) {
					txtLog.setFont(fc.selectedFont());
					displayCurrentFont(fc.selectedFont());
				}//if
				fc = null;
			}
		});
		GridBagConstraints gbc_btnFontChooser = new GridBagConstraints();
		gbc_btnFontChooser.insets = new Insets(0, 0, 5, 0);
		gbc_btnFontChooser.gridx = 0;
		gbc_btnFontChooser.gridy = 1;
		panelLeft.add(btnFontChooser, gbc_btnFontChooser);

		verticalStrut_1 = Box.createVerticalStrut(20);
		verticalStrut_1.setPreferredSize(new Dimension(20, 20));
		verticalStrut_1.setMinimumSize(new Dimension(20, 20));
		verticalStrut_1.setMaximumSize(new Dimension(0, 0));
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_1.gridx = 0;
		gbc_verticalStrut_1.gridy = 2;
		panelLeft.add(verticalStrut_1, gbc_verticalStrut_1);

		btnFillWithText = new JButton("Fill with Text");
		btnFillWithText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log.addNL();
				log.info("The listener interface for receiving action events."
						+ " The class that is interested in processing an action"
						+ " event implements this interface, and the object created"
						+ " with that class is registered with a component, using the" + ""
						+ " component's addActionListener method. When the action" + ""
						+ " event occurs, that object's actionPerformed method is invoked.");
				log.addNL();
				log.info("abcdefghijklmnopqrstuvwxyz");
				log.addNL();
				log.info("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
				log.addNL();
				log.info("1234567890   " + "~!@#$%^&*()_+{}|:\"<>? -=[]\\;\',./");
			}//actionPerformed
		});
		
		btnNewButton = new JButton("Font Chooser Tahoma 11 Plain");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontChooser fc = new FontChooser(frame,"Tahoma","Plain",11);
				if(fc.showDialog()== JOptionPane.OK_OPTION) {
					txtLog.setFont(fc.selectedFont());
					displayCurrentFont(fc.selectedFont());
				}//if
				fc = null;
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 3;
		panelLeft.add(btnNewButton, gbc_btnNewButton);
		
		verticalStrut_2 = Box.createVerticalStrut(20);
		verticalStrut_2.setPreferredSize(new Dimension(20, 20));
		verticalStrut_2.setMinimumSize(new Dimension(20, 20));
		verticalStrut_2.setMaximumSize(new Dimension(0, 0));
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_2.gridx = 0;
		gbc_verticalStrut_2.gridy = 4;
		panelLeft.add(verticalStrut_2, gbc_verticalStrut_2);
		GridBagConstraints gbc_btnFillWithText = new GridBagConstraints();
		gbc_btnFillWithText.gridx = 0;
		gbc_btnFillWithText.gridy = 5;
		panelLeft.add(btnFillWithText, gbc_btnFillWithText);
	}// initialize

}// class TestFontChooser
