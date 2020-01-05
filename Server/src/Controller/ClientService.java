package Controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import DataPackage.JsonDTO;
import DataPackage.ScheduleDTO;

public class ClientService implements Controller {
	private List<ScheduleDTO> dtos;
	public ClientService()
	{
		
	}
	@Override
	public String runSystem(String value) {
		String rs;
		JsonDTO json= gson.fromJson(value,JsonDTO.class);
		ScheduleDTO dto;
		if(json.type.equals("insert") || json.type.equals("delete") || json.type.equals("update"))
		{
			dto = gson.fromJson(json.jsonObject,ScheduleDTO.class);
			Method method;
			try {
				method = this.getClass().getDeclaredMethod(value,ScheduleDTO.class);
				method.invoke(this,dto);
			} 
			catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			dtos = new LinkedList<ScheduleDTO>();
			getAll();
			rs = gson.toJson(dtos);
			json.jsonObject = rs;
			json.type="ResultGetAll";
		}
		return gson.toJson(json);
	}
	private String delete(ScheduleDTO dto){
		
		return null;
	}
	private String update(ScheduleDTO dto) {
		
		return null;
	}
	private String insert(ScheduleDTO dto) {
		
		return null;
	}
	private void getAll() {
		
	}
}
