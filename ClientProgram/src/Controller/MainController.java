package Controller;

import DataPackage.AlarmDTO;
import UIPackage.FrameMain;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Vector;

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
		this.view.addListController(action3);
		this.view.repack();
		Thread t = new Thread(new AlarmThread());
		t.setDaemon(true);
		t.start();
	}
	public void addList(String a,LocalTime b,String c) {
		nameList.addElement(a);
		dateList.addElement(b);
		typeList.addElement(c);
		view.repack();
	}
	public void updateSelectItem(AlarmDTO dto) {
		AlarmDTO temp = view.viewUpdateItem(dto);
		dto.setDate(temp.getDateTime());
		dto.setType(temp.getType());
		dto.setName(temp.getName());
		dto.setPath(temp.getPath());
		reModelSetting();
	}
	public void reModelSetting() {
		nameList.clear();
		dateList.clear();
		typeList.clear();
		for(AlarmDTO to : dtos)
		{
			nameList.addElement(to.getName());
			dateList.addElement(to.getDateTime());
			typeList.addElement(to.getType());
		}
	}
	public void deleteSelected(AlarmDTO dto)
	{
		dtos.remove(dto);
		reModelSetting();
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
							if (dto.getType().equals(AlarmDTO.SYSTEM_TYPE))
							{
								Runtime object = Runtime.getRuntime();
								try {
									object.exec("shutdown -s");
								} catch (IOException e) {
								e.printStackTrace();
								}
								System.out.println("SYSTEM_TEST");
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
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
			if(view.rightMenu.isVisible()) view.showPopupMenu(false);
			JMenuItem item = (JMenuItem)e.getSource();
			if(item == view.exit || item == view.popExit)
			{
				if(view.exitView() == 0) {
					System.exit(0);
					System.out.println("Exit");
				}
				System.out.println("Check Option");
			}
			else if(item == view.addAlarm || item == view.popAddAlarm)
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
			else if(item == view.popDeleteAlarm)
			{
				if(view.nList.getSelectedIndex() != -1) {
					deleteSelected(dtos.get(view.nList.getSelectedIndex()));
				}
			}
			else if(item == view.openAlarm || item == view.popOpenAlarm)
			{
				String rs=view.fileChoose();
				if(rs != null)
				{
					System.out.println(rs);
				}
			}
			else if(item == view.save || item == view.popSave)
			{
				System.out.println("Save Alarm");
			}
			else if(item == view.popUpdateAlarm)
			{
				if(view.nList.getSelectedIndex() != -1)
				{
					updateSelectItem(dtos.get(view.nList.getSelectedIndex()));
				}
			}
			else if(item == view.introduce)
			{
				view.showIntroduce();
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
	class ListSelectionAction extends MouseAdapter{
		int clickCounter = 0;
		int selectIndex=-1;

		@Override
		public void mousePressed(MouseEvent e) {
			if(view.rightMenu.isVisible())
			{
				view.showPopupMenu(false);
			}
			if(e.getButton() == MouseEvent.BUTTON3)
			{
				System.out.println("Right");
				view.showMousePopup(e.getLocationOnScreen());
				return;
			}
			JList list = (JList) e.getSource();
			if(list.getSelectedIndex() != selectIndex) {
				selectIndex = list.getSelectedIndex();
				clickCounter=0;
			}
			else if(clickCounter < 2)
			{
				clickCounter +=1;
				return;
			}
			else
			{
				if(selectIndex != -1) {
					updateSelectItem(dtos.get(selectIndex));
					selectIndex = -1;
					clickCounter = 0;
				}
			}
			if(list == view.nList)
			{
				view.dList.setSelectedIndex(selectIndex);
				view.tList.setSelectedIndex(selectIndex);
			}
			else if(list == view.dList)
			{
				view.nList.setSelectedIndex(selectIndex);
				view.tList.setSelectedIndex(selectIndex);
			}
			else
			{
				view.nList.setSelectedIndex(selectIndex);
				view.dList.setSelectedIndex(selectIndex);
			}
		}
	}
}

