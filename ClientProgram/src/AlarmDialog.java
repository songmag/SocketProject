import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.time.LocalTime;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Custom Dialog �ν� �ش� �����͸� �����ϴµ� �־ ��Ʈ�ѷ��� ���� �ʿ����� �ʴ�.
 * ��ȯ���� �������� Method = getValue();
 * 
 * @author songm
 *
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
		super(frame,"CustomDialog",true);
		JPanel panel;
		name = new JTextField(10);
		date = new Vector<JComboBox<Integer>>();
		btn1 = new JButton("����");
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
		type.addItem("�ý��� ����");
		type.addItem("���� ����");
		type.addActionListener(new ActionListener() {
			JComboBox box;
			@Override
			public void actionPerformed(ActionEvent e) {
				box = (JComboBox) e.getSource();
				if(box.getSelectedItem().equals("���� ����"))
				{
					alarmType=AlarmDTO.SOUND_TYPE;
					System.out.println("MusicBox");
				}
				else
				{
					alarmType=AlarmDTO.SYSTEM_TYPE;
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
		DialogEvent action = new DialogEvent(this);
		btn1.addActionListener(action);
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
		dto.name = this.name.getText();
		Integer hour,minute,sec;
		hour = (Integer) this.date.get(0).getSelectedItem();
		minute = (Integer) this.date.get(1).getSelectedItem();
		sec = (Integer) this.date.get(2).getSelectedItem();
		LocalTime time = LocalTime.of(hour, minute, sec);
		dto.date = time;
		if(this.alarmType != null)
		{
			dto.type = this.alarmType;
		}
		if(this.path != null)
		{
			dto.path = path;
		}
		dto.type=AlarmDTO.SYSTEM_TYPE;
		dto.path="";
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
		AlarmDialog dialog;
		public DialogEvent(AlarmDialog dialog)
		{
			this.dialog = dialog;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			dialog.buttonClickEvent();
		}
	}
}