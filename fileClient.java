/**

file client will send a file name to the server.
if file does not exist, client will print message to the users.
	"the file<name of file> is not available".
if file does exist, client will receive file from server. client will save as.
	<original_name>+"_clt.<original_extensiion>
*/

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class fileClient{

   static PrintStream ps = null;     // to send message
   static Scanner sis = null;        // to receive message
   static String fileName = null;

	/**
	 *	try: creates new socket, printstream, inputstream
	 *  try: runs send, receive, closes socket
	 */
   public static void main(String args[]){
		Socket clt_skt = null;
        try {
		    clt_skt = new Socket("127.0.0.1", 1357); // "127.0.0.1" is "localhost"; so you can also try "localhost"
		    ps = new PrintStream(clt_skt.getOutputStream());
            sis = new Scanner(clt_skt.getInputStream());
   		}
       	catch (IOException e){
            System.out.println(" Error connecting: " + e); 
        }
        try {
            send(); 
		    receive();
    	    clt_skt.close();
        }
        catch (IOException e){
            System.out.println(" Error sending:" + e);
		}
      System.out.println("Communication ended successfully at the Client!");
	}
   	/**
	 *	gets input from user for file name and sends to server
	 *	
	 */
   public static void send() throws IOException{
		System.out.println("Enter a filename");
		Scanner input = new Scanner(System.in);
		fileName = input.next();
        ps.println(fileName);
		System.out.println("sending....<"+fileName+">");        
		ps.flush(); // flush the message
   }
	/**
	 *	receives data from server
	 *	if dne is returned, does nothing
	 *	if data equals name of file requested, creates new file and writes data read from socke to new file.
	 */
   public static void receive() throws IOException{ 
		String temp = sis.next();
        if(temp.equals("DNE")){
            System.out.println("the file <"+fileName+"> is not available");
        }
        else{
			fileName = temp +"_clt.txt";
			File file = new File(fileName);
            ps = new PrintStream(file);
            while(sis.hasNextLine()){
                ps.println(sis.nextLine());
            }    
        }
   }



}


