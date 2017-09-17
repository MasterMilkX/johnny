/*
 * A chatbot that takes on the personality 
 * of TV or movie characters.
 * Program by Megan "Milk" Charity
 * Project for RamHacks 2017 Hack-a-Thon
*/


import java.util.Scanner;
public class Johnny{

	static Mask curMask;
	static Mask finn = new Mask("Finn", "advent_time", "\u001B[36m");
	static Mask deadpool = new Mask("DEADPOOL", "deadpool", "\u001B[31m");


	public Johnny(){}
	public static void main(String[] args){
		finn.createCharacter();
		deadpool.createCharacter();
		//finn.showDialogue();

		curMask = deadpool;

		String anonOut = curMask.getCharacter();
	   	for(int a = 0; a < (8 - curMask.getCharacter().length()); a++){
	   		anonOut += " ";
	   	}
	   	anonOut += " > ";

		//setup the convo
		/*
		if(args.length > 0)
			startUp(args[0]);
		else
			startUp(null);
		*/
		Scanner in = new Scanner(System.in);
		System.out.println("\u001B[31m" + "Say hi to " + curMask.getCharacter() +" [type Bye to finish]" + "\u001B[0m");
		//exportConvo("-------------" + curMask.getCharacter() + "-------------");

		//first line
		System.out.print("User     > ");
		String user = in.nextLine();
		//exportConvo("User     > " + user);

		//while conversation is still going
		while(!user.toUpperCase().equals("BYE")){
			String maskResp = curMask.react(user);

			//print it out
			System.out.println(curMask.getColor() + anonOut + maskResp + "\u001B[0m");
			//exportConvo(anonOut + maskResp);

			//get user's input
			System.out.print("User     > ");
			user = in.nextLine();
			//exportConvo("User     > " + user);
		}
		//try{bw.close();}catch(IOException e){e.printStackTrace();}
	}

	/*
	//make the output file
	public static void startUp(String character){
		try{
			//access the log files
			logtxt = new File("log.txt");
			if(!logtxt.exists())
				logtxt.createNewFile();
			fw = new FileWriter(logtxt, true);
	   		bw = new BufferedWriter(fw);

	   		//get the anon user
	   		actor = ((character != null) ? character : "Default");
	   		importProfile(actor);

	   		//make the output string
	   		anonOut += actor;
	   		for(int a = 0; a < (8 - actor.length()); a++){
	   			anonOut += " ";
	   		}
	   		anonOut += " > ";

		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	*/
}	