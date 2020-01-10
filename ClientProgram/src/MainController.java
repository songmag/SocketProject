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
	 * View에 들어갈 Model List Model 
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
	//demonThread로 동작시켜서 자동 종료되도록 처리
	class AlarmThread implements Runnable{
		@Override
		public void run() {
			while(true)
			{
				LocalTime time = LocalTime.now();
				synchronized (dtos) {
					for (AlarmDTO dto : dtos) {
						//after면 false, Before이면 true
						//after면 true, Before이면 false
						//같으면 두개다 false
						//두개중 하나라도 true라면 해당 문제는 false가 되도록 or 연산으로 처리한다.
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

