package DataPackage;

import java.text.SimpleDateFormat;
import java.time.LocalTime;

public class AlarmDTO implements Cloneable{
	public static final String SOUND_TYPE="MUSIC_TYPE";
	public static final String SYSTEM_TYPE="SYSTEM_TYPE";
	private String name;
	private LocalTime date;
	private String type;
	private String path;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name.equals(""))
		{
			this.name = AlarmDTO.SYSTEM_TYPE;
			return;
		}
		this.name = name;
	}

	public void setDate(LocalTime date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		if(type.equals("ShutDown System") || type.equals(AlarmDTO.SYSTEM_TYPE))
			this.type = AlarmDTO.SYSTEM_TYPE;
		else
			this.type = AlarmDTO.SOUND_TYPE;
	}

	public String getPath() {

		return path;
	}

	public void setPath(String path) {
		if (path == null || path.equals(""))
		{
			this.path="";
			return;
		}
		this.path = path;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	public String getDate()
	{
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:SS");
		return format.format(date);
	}
	public LocalTime getDateTime()
	{
		return date;
	}
	@Override
	public String toString() {
		return "AlarmDTO{" +
				"name='" + name + '\'' +
				", date=" + date +
				", type='" + type + '\'' +
				", path='" + path + '\'' +
				'}';
	}
}
