import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MainController {
	private FrameMain view;
	private DefaultListModel<String> nameList;
	private DefaultListModel<LocalTime> dateList;
	private DefaultListModel<String> typeList;
	private Vector<AlarmDTO> dtos;
	/**
	 * View�� �� Model List Model 
	 * nameList, dateList, typeList
	 * @param view
	 */
	public MainController(FrameMain view)
	{
		this.view = view;
		dtos = new Vector<AlarmDTO>();
		MenuAction action = new MenuAction();
		this.view.addMenuController(action);
		LoginAction action2 = new LoginAction();
		this.view.addButtonController(action2);
		nameList = new DefaultListModel<String>();
		dateList = new DefaultListModel<LocalTime>();
		typeList = new DefaultListModel<String>();
		/**
		 *  Model Setting
		 */
		this.view.addListModel(nameList, dateList, typeList);
		ListSelectionAction action3 = new ListSelectionAction();
		this.view.repack();
		this.view.addListController(action3);
		Thread t = new Thread(new AlarmThread());
		t.setDaemon(true);
		t.start();
	}
	public void deleteSelectItem(String item)
	{

	}
	public void addList(String a,LocalTime b,String c) {
		nameList.addElement(a);
		dateList.addElement(b);
		typeList.addElement(c);
		view.repack();
	}
	//demonThread�� ���۽��Ѽ� �ڵ� ����ǵ��� ó��
	class AlarmThread implements Runnable{
		@Override
		public void run() {
			while(true)
			{
				LocalTime time = LocalTime.now();
				synchronized (dtos) {
					for (AlarmDTO dto : dtos) {
						//after�� false, Before�̸� true
						//after�� true, Before�̸� false
						//������ �ΰ��� false
						//�ΰ��� �ϳ��� true��� �ش� ������ false�� �ǵ��� or �������� ó���Ѵ�.
						if ((dto.getDateTime().until(LocalTime.now(), ChronoUnit.HOURS) | dto.getDateTime().until(LocalTime.now(), ChronoUnit.MINUTES)
						| dto.getDateTime().until(LocalTime.now(), ChronoUnit.SECONDS))==0)
						{
							if (dto.getType().equals(AlarmDTO.SYSTEM_TYPE)) {
							/*
							Runtime object = Runtime.getRuntime();
							try {
								object.exec("shutdown -s");
							} catch (IOException e) {
								e.printStackTrace();
							}
							*/
								System.out.println("SYSTEM_TEST");
							} else {
								System.out.println(dto.getPath());
							}
						}
					}
				}
			}
		}
	}
	//Alarm�� �ڵ����� �︱ �� �ִ� Thread �� ������ ������ �Ǵ°�?
	//�ش� Alarm �� ������� �����⸦ �ؾ��ϴ°�?
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
				AlarmDTO dto = view.addAlarmView();
				if(dto != null)
				{
					try {
						System.out.println(dto.clone());
						dtos.add((AlarmDTO) dto.clone());
						addList(dto.getName(),dto.getDateTime(),dto.getType());
					} catch (CloneNotSupportedException ex) {
						ex.printStackTrace();
					}
				}
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
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {
			JList list = (JList) e.getSource();
			if(list == view.nList)
			{
				System.out.println(list.getSelectedValue());
				view.dList.setSelectedIndex(list.getSelectedIndex());
				view.tList.setSelectedIndex(list.getSelectedIndex());
			}
			else if(list == view.dList)
			{
				System.out.println(list.getSelectedValue());
				view.nList.setSelectedIndex(list.getSelectedIndex());
				view.tList.setSelectedIndex(list.getSelectedIndex());
			}
			else
			{
				System.out.println(list.getSelectedValue());
				view.nList.setSelectedIndex(list.getSelectedIndex());
				view.dList.setSelectedIndex(list.getSelectedIndex());
			}


		}
		@Override
		public void mouseReleased(MouseEvent e) {}
	}
}

