import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class AlarmDTO {
	static final String SOUND_TYPE="MUSIC_TYPE";
	static final String SYSTEM_TYPE="SYSTEM_TYPE";
	public String name;
	public LocalTime date;
	public String type;
	public String path;
	
	public String getDate()
	{
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:SS");
		return format.format(date);
	}
}
