import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;

public class Colors{

	public static HashMap<String, String> colors = new HashMap<String, String>(){{
		put(("reset"), 	("\u001B[0m"));
		put(("black"), 	("\u001B[30m"));
		put(("red"), 	("\u001B[31m"));
		put(("green"), 	("\u001B[32m"));
		put(("yellow"),	("\u001B[33m"));
		put(("blue"), 	("\u001B[34m"));
		put(("purple"),	("\u001B[35m"));
		put(("cyan"),	("\u001B[36m"));
		put(("white"),	("\u001B[37m"));
	}};

	//returns the color
	public static String getColor(String colorTxt){
		Set s = colors.entrySet();
		Iterator i = s.iterator();

		//get the matching ansi color value
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			String key = (String)(me.getKey());
			if(key.equals(colorTxt))
				return (String)(me.getValue());
		}

		//return the reset color otherwise
		return (String)(colors.get("reset"));
	}

}
