
/**

file server will receive file name from client and search the file in current directory.
if file does not exit, server will inform client.
if file does exist, server will send the full file.
*/
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class fileServer{

   static PrintStream ps = null;     // to send message
   static Scanner sis = null;        // to receive message
   static String data = null;
   static File file = null;
   
	/**
	 *	try: creates server socket, inputstream and outputstream
	 *	try: runs receive, then send, then closes skt 
	 */
   public static void main(String args[]){
        ServerSocket svr_skt = null;
        Socket skt = null;
        try {
            svr_skt = new ServerSocket(1357); 
            skt = svr_skt.accept();
	        sis = new Scanner(skt.getInputStream());
	        ps = new PrintStream(skt.getOutputStream());
        }
        catch (IOException e){
           System.out.println(" Error connecting: " + e); 
        }
        try {
            receive(); 
            send();
            skt.close();
            svr_skt.close();
        }
        catch (IOException e){
            System.out.println(" Error sending:" + e);
        }
        System.out.println("Communication ended successfully at the Server!");
   }
	/**
	 *	waits for client to send name of file
	 *	searches CWD for file name
	 * 	if name sent from client matches name in cwd, data is set to file name, else DNE
	 */
   public static void receive() throws IOException{
		System.out.println("ready to receive");
        String fileName = sis.next();
        System.out.println("I (Server) received: " + fileName);
        File dir = new File(".");
        File[] filesList = dir.listFiles();
        for(File temp : filesList){
		    if(temp.getName().equals(fileName)){
                data = fileName;
                file = temp;
            }
            else{
                data = "DNE";
            }
        }
   }
	/**
	 *	sends value of data to server
	 *	if file exists reads from file line by line and sends to client	
	 */
   public static void send() throws IOException{
		System.out.println("sending....");
        ps.println(data); // send message
        if(file != null){
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                ps.println(reader.nextLine());
            }
        }
        ps.flush(); // flush the message
       
   }

}


