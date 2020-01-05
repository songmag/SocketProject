package Controller;

import com.google.gson.Gson;

public interface Controller {
	Gson gson = new Gson();
	String runSystem(String value);
}
