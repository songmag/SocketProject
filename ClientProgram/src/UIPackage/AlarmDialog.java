package UIPackage;

import DataPackage.AlarmDTO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalTime;
import java.util.Vector;

/**
 * Custom Dialog 로써 해당 데이터를 변경하는데 있어서 컨트롤러가 따로 필요하지 않다.
 * 반환값을 가져오는 Method = getValue();
 * 정상 종료 되었는지 판단하는 Method status 반환 getStatus;
 * @author songm
 */

public class AlarmDialog extends JDialog{
	JButton btn1;
	JTextField name;
	JComboBox<String> type;
	Vector<JComboBox<Integer>> date;
	AlarmDTO dto = null;
	boolean status;
	String alarmType,path;

	public AlarmDialog(JFrame frame)
	{
		super(frame,"Alarm Add",true);
		JPanel panel;
		name = new JTextField(10);
		date = new Vector<JComboBox<Integer>>();
		btn1 = new JButton("저장");
		JComboBox<Integer> hour,minute,sec;
		hour = new JComboBox<Integer>();
		minute = new JComboBox<Integer>();
		sec = new JComboBox<Integer>();
		for(int i = 0 ; i < 60;i++) {
			if(i<24)
				hour.addItem(i);
			minute.addItem(i);
			sec.addItem(i);
		}
		date.add(hour);
		date.add(minute);
		date.add(sec);
		panel = new JPanel();
		panel.add(new JLabel("Name"));
		panel.add(name);
		panel.add(new JLabel("Date"));
		panel.add(hour);
		panel.add(minute);
		panel.add(sec);
		panel.add(new JLabel("Type"));
		type = new JComboBox<String>();
		type.addItem("ShutDown System");
		type.addItem("Music Alarm");
		type.addActionListener(new ActionListener() {
			JComboBox box;
			@Override
			public void actionPerformed(ActionEvent e) {
				box = (JComboBox) e.getSource();
				if(box.getSelectedItem().equals("Music Alarm"))
				{
					alarmType= AlarmDTO.SOUND_TYPE;
					System.out.println("MusicBox");
				}
				else
				{
					alarmType= AlarmDTO.SYSTEM_TYPE;
					System.out.println("SystemType");
				}
			}
		});
		panel.add(type);
		panel.add(btn1);
		add(panel);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(dto == null) {
					status = false;
					System.out.println("Error System Fail");
				}

			}
		});
		DialogEvent action = new DialogEvent();
		btn1.addActionListener(action);
	}
	public AlarmDialog(JFrame frame, AlarmDTO dto)
	{
		this(frame);
		name.setText(dto.getName());
		date.get(0).setSelectedItem(dto.getDateTime().getHour());
		date.get(1).setSelectedItem(dto.getDateTime().getMinute());
		date.get(2).setSelectedItem(dto.getDateTime().getSecond());
		type.setSelectedItem(dto.getType());
	}
	public void showDialog()
	{
		pack();
		setVisible(true);
	}
	public void addListener(ActionListener action)
	{
		type.addActionListener(action);
		btn1.addActionListener(action);
	}
	public void buttonClickEvent()
	{
		dto = new AlarmDTO();
		dto.setName(this.name.getText());
		Integer hour,minute,sec;
		hour = (Integer) this.date.get(0).getSelectedItem();
		minute = (Integer) this.date.get(1).getSelectedItem();
		sec = (Integer) this.date.get(2).getSelectedItem();
		LocalTime time = LocalTime.of(hour, minute, sec);
		dto.setDate(time);
		if(this.alarmType != null)
		{
			dto.setType(this.alarmType);
		}
		if(this.path != null)
		{
			dto.setPath(path);
		}
		System.out.println(type.getSelectedItem().toString());
		dto.setType(type.getSelectedItem().toString());
		status=true;
		this.setVisible(false);
		this.dispose();
	}
	/**
	 * 
	 * @return AlarmDTO
	 */
	public boolean getStatus()
	{
		return status;
	}
	public AlarmDTO getValue()
	{
		return dto;
	}
	class DialogEvent implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			buttonClickEvent();
		}
	}
}