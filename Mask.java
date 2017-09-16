/* character importing and creation 
 * for use in Johnny
 * Program by Megan "Milk" Charity
 * Made for the RamHacks 2017 Hack-a-thon
*/


import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;

public class Mask{

	////// INSTANTIATION FUNCTIONS //////

	private String character;
	private String script_name;
	private String color;
	private ArrayList<String> script;
	public  ArrayList<String> char_lines;
	public  HashMap<String, String> dialogue;

	public Mask(){
		setCharacter("Default");
		setScriptName("");
		setColor("\u001B[37m");
	}

	public Mask(String c, String sc_name, String col){
		setCharacter(c);
		setScriptName(sc_name);
		setColor(col);
	}

	//////     SET AND GET        //////
	public void setCharacter(String name){
		character = name;
	}
	public String getCharacter(){
		return character;
	}
	public void setScriptName(String name){
		script_name = name;
		cleanScript();
	}
	public String getScriptName(){
		return script_name;
	}
	public void setColor(String col){
		color = col;
	}
	public String getColor(){
		return color;
	}

	///////    GET NECESSARY INFORMATION   /////

	public void createCharacter(){
		script = new ArrayList<String>();
		char_lines = new ArrayList<String>();
		dialogue = new HashMap<String, String>();

		readScript();
		readDialogue();
		readLines();
	}


	//////   READ THE SCRIPT AND KNOW YOUR LINES   //////

	//import multiple episodes for one character
	public void getShow(String show){
		return;
	}

	//imports the script based on the file name
	private void readScript(){
		File scriptFile = new File("format_text/" + getScriptName());
		if(!scriptFile.exists())
			throw new IllegalArgumentException("ERROR: No such script - " + getScriptName() + "!");

		try{
			Scanner in = new Scanner(scriptFile);				//read the text file
			while(in.hasNextLine()){
				String w = in.nextLine();
				script.add(w);
			}
			in.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	//remove any exposition from the script
	private void cleanScript(){
		String perl_cmd = "perl script_writer.pl < \"text/" + getScriptName() + "\"";
		try{
			Runtime.getRuntime().exec(perl_cmd);
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
	
	//read the individual character lines
	private void readLines(){
		String c = getCharacter();
		for(int l=1;l<script.size();l++){
			String line = script.get(l);

			//check if character is speaking this line
			String[] words = line.split(":");
			if(words.length > 1 && words[0].equals(c)){
				char_lines.add(words[1].trim());
			}
		}
	}
	public void showLines(){
		for(String l : char_lines){
			System.out.println(l);
		}
	}
	
	//read back and forth dialogue with the character and pair it up
	private void readDialogue(){
		String c = getCharacter();
		for(int l=1;l<script.size();l++){
			String line = script.get(l);

			//check if character is speaking this line
			String[] words = line.split(":");
			if(words[0].equals(c)){
				//get line before it
				String prev_line = script.get(l-1);
				String[] words2 = prev_line.split(":");

				//match up answer-response dialogue
				if(words2.length > 1 && words.length > 1)
					dialogue.put(words2[1].trim(), words[1].trim());
				else
					System.out.println("\u001B[31m" + prev_line + " " + line + "\u001B[0m");
			}
		}
	}



	///////       PUT ON A SHOW      //////////

	public String react(String input){
		String[] input_words = input.toLowerCase().split(" ");
		int maxScore = input_words.length;

		//find the best match(es) from the given words
		double best_acc = 100;
		ArrayList<String> resp = new ArrayList<String>();
		for(String q : dialogue.keySet()){
			String[] quest_words = q.toLowerCase().split(" ");
			double acc = dialogue_accuracy(input_words, quest_words);

			if(Math.abs(100-acc) < best_acc){
				resp = new ArrayList<String>();
				resp.add((String)dialogue.get(q));
				best_acc = Math.round(acc);
			}else if(Math.abs(100-acc) == best_acc){
				resp.add((String)dialogue.get(q));
			}
		}

		//return a random response
		if(resp.size() > 1){
			int rand = (int)(Math.random() * resp.size());
			return resp.get(rand);
		}else{
			return resp.get(0);
		}
	}

	//check how well the input matches the dialogue response
	private double dialogue_accuracy(String[] tinyArr, String[] bigArr){
		int matches = 0;
		for(String w : tinyArr){
			if(Arrays.asList(bigArr).contains(w))
				matches++;
		}

		return matches / bigArr.length;
	}
}