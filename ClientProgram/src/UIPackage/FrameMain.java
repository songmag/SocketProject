package UIPackage;

import Controller.MainController;
import DataPackage.AlarmDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.time.LocalTime;

public class FrameMain{
	JFrame frame;
	public JMenuItem openAlarm;
	public JMenuItem addAlarm;
	public JMenuItem save;
	public JMenuItem exit;

	public JMenuItem popOpenAlarm;
	public JMenuItem popAddAlarm;
	public JMenuItem popUpdateAlarm;
	public JMenuItem popDeleteAlarm;
	public JMenuItem popSave;
	public JMenuItem popExit;
	public JMenuItem introduce;
	public JButton button;
	public JButton btnLogout;
	public JList nList,tList,dList;
	private JPanel loginPanel;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lblNewLabel;
	private CardLayout layout;

	public JPopupMenu rightMenu;

	public FrameMain(){
		frame = new JFrame("AlarmProgram");
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu mnMenu2 = new JMenu("Introduce");
		introduce = new JMenuItem("Introduce");
		mnMenu2.add(introduce);

		JMenu mnMenu = new JMenu("menu");
		menuBar.add(mnMenu);
		menuBar.add(mnMenu2);
		openAlarm = new JMenuItem("Open Alarm");
		mnMenu.add(openAlarm);
		addAlarm = new JMenuItem("Add Alarm");
		mnMenu.add(addAlarm);
		save = new JMenuItem("Save Alarm");
		mnMenu.add(save);
		exit = new JMenuItem("Exit Alarm");
		mnMenu.add(exit);
		nList = new JList();
		tList = new JList();
		dList = new JList();
		frame.setIconImage(null);
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
		JPanel listPanel = new JPanel();
		listPanel.setPreferredSize(new Dimension(900,400));
		listPanel.add(nList,BorderLayout.WEST);
		listPanel.add(dList,BorderLayout.CENTER);
		listPanel.add(tList,BorderLayout.EAST);
		listPanel.setBackground(Color.white);
		frame.getContentPane().add(listPanel,BorderLayout.CENTER);
		frame.getContentPane().setBackground(Color.white);
		Dimension listSize = new Dimension(200,400);
		nList.setPreferredSize(listSize);
		dList.setPreferredSize(listSize);
		tList.setPreferredSize(listSize);
		nList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rightMenu = new JPopupMenu();

		popOpenAlarm = new JMenuItem("Open Alarm");
		popAddAlarm = new JMenuItem("Add Alarm");
		popUpdateAlarm = new JMenuItem("UpDate Alarm");
		popDeleteAlarm = new JMenuItem("Delete Alarm");
		popSave = new JMenuItem("Save Alarm");
		popExit = new JMenuItem("Exit");

		rightMenu.add(popOpenAlarm);
		rightMenu.add(popUpdateAlarm);
		rightMenu.add(popAddAlarm);
		rightMenu.add(popDeleteAlarm);
		rightMenu.add(popSave);
		rightMenu.add(popExit);
		rightMenu.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
	}
	public void repack()
	{
		frame.pack();
	}
	public void showMousePopup(Point p)
	{
		rightMenu.setLocation(p.x,p.y);
		rightMenu.setVisible(true);
	}
	public void showPopupMenu(boolean show)
	{
		rightMenu.setVisible(show);
	}
	public void addListModel(DefaultListModel<String> n, DefaultListModel<LocalTime> d, DefaultListModel<String> t)
	{
		nList.setModel(n);
		dList.setModel(d);
		tList.setModel(t);
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
			System.out.println("FileChooser Select Cancel");
		}
		else if(choose == JFileChooser.APPROVE_OPTION)
		{
			System.out.println(chooser.getSelectedFile());
			rs = chooser.getSelectedFile().getPath();
		}
		else
		{
			System.out.println("FileChooser Select Cancel");
		}
		return rs;
	}
	public AlarmDTO addAlarmView()
	{
		AlarmDialog dialog = new AlarmDialog(frame);
		dialog.showDialog();
		if(dialog.getStatus()) {
			AlarmDTO dto = dialog.getValue();
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
		save.addActionListener(action);
		exit.addActionListener(action);
		popOpenAlarm.addActionListener(action);
		popAddAlarm.addActionListener(action);
		popUpdateAlarm.addActionListener(action);
		popDeleteAlarm.addActionListener(action);
		popSave.addActionListener(action);
		popExit.addActionListener(action);
		introduce.addActionListener(action);
	}
	public void addButtonController(ActionListener action)
	{
		button.addActionListener(action);
		btnLogout.addActionListener(action);
	}
	public AlarmDTO viewUpdateItem(AlarmDTO dto){
		AlarmDialog dialog = new AlarmDialog(frame,dto);
		dialog.showDialog();
		if(dialog.getStatus()) {
			AlarmDTO temp = dialog.getValue();
			return temp;
		}
		return dto;
	}
	public static void main(String[] args) {
		MainController main = new MainController(new FrameMain());
	}
	public void showIntroduce() {
		JOptionPane.showMessageDialog(frame, "알람 서비스 by Songmag\n until 2020-01-01 ~ 2020-02-01","Introduce",JOptionPane.INFORMATION_MESSAGE);
	}
}