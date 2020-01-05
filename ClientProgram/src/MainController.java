import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MainController {
	private FrameMain view;
	private DefaultListModel<String> nameList;
	private DefaultListModel<String> dateList;
	private DefaultListModel<String> typeList;
	private List<AlarmDTO> dtos;
	/**
	 * View에 들어갈 Model List Model 
	 * nameList, dateList, typeList
	 * @param view
	 */
	public MainController(FrameMain view)
	{
		this.view = view;
		MenuAction action = new MenuAction();
		this.view.addMenuController(action);
		LoginAction action2 = new LoginAction();
		this.view.addButtonController(action2);
		nameList = new DefaultListModel<String>();
		dateList = new DefaultListModel<String>();
		typeList = new DefaultListModel<String>();
		/**
		 *  Model Setting
		 */
		this.view.addListModel(nameList, dateList, typeList);
		ListSelectionAction action3 = new ListSelectionAction();
		this.view.repack();
		this.view.addListController(action3);
	}
	public void addList(String a,String b,String c) {
		nameList.addElement(a);
		dateList.addElement(b);
		typeList.addElement(c);
	}
	//Alarm이 자동으로 울릴 수 있는 Thread 를 가지고 있으면 되는가?
	//해당 Alarm 은 계속적인 수행대기를 해야하는가?
	class MenuAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JMenuItem item = (JMenuItem)e.getSource();
			if(item == view.exit)
			{
				if(view.exitView() == 0) {
					System.exit(0);
					System.out.println("Exit");
				}
				System.out.println("Check Option");
			}
			else if(item == view.addAlarm)
			{
				view.addAlarmView();
			}
			else if(item == view.deleteAlarm)
			{
				
			}
			else if(item == view.openAlarm)
			{
				String rs=view.fileChoose();
				if(rs != null)
				{
					System.out.println(rs);
				}
			}
		}
	}
	class LoginAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton)e.getSource();
			if(button == view.button)
			{
				System.out.println("LoginButton");
				view.setCardLayoutNext();
			}
			else if(button == view.btnLogout)
			{
				System.out.println("LogoutButton");
				view.setCardLayoutNext();
			}
		}	
	}
	class ListSelectionAction implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			
		}
		@Override
		public void mouseExited(MouseEvent e) {
			
		}
		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println(e);
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			
		}
	}
}

