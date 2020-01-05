import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.JPasswordField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.CardLayout;
import java.awt.Color;

public class FrameMain{
	JFrame frame;
	public JMenuItem openAlarm;
	public JMenuItem addAlarm;
	public JMenuItem updateAlarm;
	public JMenuItem deleteAlarm;
	public JMenuItem exit;
	public JButton button;
	public JButton btnLogout;
	
	private JList nList,tList,dList;
	private JPanel loginPanel;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lblNewLabel;
	private String[] cardLayoutName = {"LoginPanel","LogOutPanel"};
	private CardLayout layout;
	public FrameMain(){
		frame = new JFrame("AlarmProgram");
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu mnMenu = new JMenu("menu");
		menuBar.add(mnMenu);
		openAlarm = new JMenuItem("OpenAlarm");
		mnMenu.add(openAlarm);
		addAlarm = new JMenuItem("AddAlarm");
		mnMenu.add(addAlarm);
		updateAlarm = new JMenuItem("UpdateAlarm");
		mnMenu.add(updateAlarm);
		deleteAlarm = new JMenuItem("DeleteAlarm");
		mnMenu.add(deleteAlarm);
		exit = new JMenuItem("ExitAlarm");
		mnMenu.add(exit);
		nList = new JList();
		tList = new JList();
		dList = new JList();
		frame.getContentPane().add(nList, BorderLayout.WEST);
		frame.getContentPane().add(tList, BorderLayout.CENTER);
		frame.getContentPane().add(dList, BorderLayout.EAST);
		loginPanel = new JPanel();
		frame.getContentPane().add(loginPanel, BorderLayout.NORTH);
		layout = new CardLayout(0,0);
		loginPanel.setLayout(layout);
		JLabel lblId = new JLabel("ID");
		textField = new JTextField();
		textField.setColumns(10);
		JLabel lblPw = new JLabel("PW");
		passwordField = new JPasswordField();
		passwordField.setColumns(18);
		button = new JButton("\uB85C\uADF8\uC778");
		new JButton("\uB85C\uADF8\uC544\uC6C3");
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.add(lblId);
		panel.add(textField);
		panel.add(lblPw);
		panel.add(passwordField);
		panel.add(button);
		loginPanel.add(panel, "LoginPanel");
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		loginPanel.add(panel_1, "LogOutPanel");
		JLabel lblId_1 = new JLabel("ID");
		panel_1.add(lblId_1);
		lblNewLabel = new JLabel("Plz Set Text");
		panel_1.add(lblNewLabel);
		btnLogout = new JButton("logout");
		panel_1.add(btnLogout);
		layout.show(loginPanel,"LoginPanel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
	}
	public void repack()
	{
		frame.pack();
	}
	public void addListModel(DefaultListModel<String> n,DefaultListModel<String> d,DefaultListModel<String> t)
	{
		nList.setModel(n);
		dList.setModel(d);
		tList.setModel(t);
	}
	public String getAlarm()
	{
		
		return null;
	}
	public void setCardLayoutNext()
	{
		layout.next(loginPanel);
		repack();
	}
	public String fileChoose()
	{
		JFileChooser chooser = new JFileChooser();
		int choose = chooser.showOpenDialog(frame);
		String rs = null;
		if(choose == JFileChooser.CANCEL_OPTION)
		{
			System.out.println("FileChooser Select Cancle");
		}
		else if(choose == JFileChooser.APPROVE_OPTION)
		{
			System.out.println(chooser.getSelectedFile());
			rs = chooser.getSelectedFile().getPath();
		}
		else
		{
			System.out.println("FileChooser Select Cancle");
		}
		return rs;
	}
	public AlarmDTO addAlarmView()
	{
		AlarmDialog dialog = new AlarmDialog(frame);
		dialog.showDialog();
		if(dialog.getStatus()) {
			AlarmDTO dto = dialog.getValue();
			System.out.println(dto.name);
			System.out.println(dto.date);
			System.out.println(dto.type);
			
			return dto;
		}
		return null;
	}
	public int exitView()
	{
		return JOptionPane.showConfirmDialog(loginPanel, "프로그램을 종료하겠습니까?","Exit",JOptionPane.YES_NO_OPTION);
	}
	public void addListController(MouseListener action)
	{
		nList.addMouseListener(action);
		dList.addMouseListener(action);
		tList.addMouseListener(action);
	}
	public void addMenuController(ActionListener action)
	{
		openAlarm.addActionListener(action);
		addAlarm.addActionListener(action);
		updateAlarm.addActionListener(action);
		deleteAlarm.addActionListener(action);
		exit.addActionListener(action);
	}
	public void addButtonController(ActionListener action)
	{
		button.addActionListener(action);
		btnLogout.addActionListener(action);
	}
	public static void main(String[] args) {
		MainController main = new MainController(new FrameMain());
	}
}