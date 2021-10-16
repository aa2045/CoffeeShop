package f21as.coursework.coffeshop.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import f21as.coursework.coffeshop.exceptions.FrequencyException;

// class deals with reading, writing and over writing to the file

public class FileManager {
	
		
	// write to a file when the filename and content is passed as an arguement
	public static void writeFile(String fileName, String content) throws FrequencyException  
    {
        
		try
		{	
		    FileWriter fw = new FileWriter(fileName,true); 
		    //the data will be appended
		    fw.write(content);
		    fw.close(); // close the file writer
		}
		//catch the IOexception
		catch(IOException e)
		{
			e.printStackTrace();
            String message = "Problem WRITING the file " + fileName;
            //log the message when there's a problem in writing to the file
			LogManager.getLogger().severe(message);
			//Exception is thrown
			throw new FrequencyException(message);
		}
    } 
	
	// method to over write the content onto to the file, useful for appending order details to the final report
	public static void overWriteFile(String fileName, String content) throws FrequencyException  
    {
        
		File f=new File(fileName);
		//delete the file
		f.delete();
		//write the content onto the newly created file
		File fnew=new File(fileName);
		
		FileManager.writeFile(fileName, content);
    }
	
	//method to read the file passed in the arguement and appends the data to a string
	public static String readFile(String fileName) throws FrequencyException  {
		
		
		String output = "";
		 
        try
        {
            output = new String ( Files.readAllBytes( Paths.get(fileName) ) );
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            String message = "Problem READING the file " + fileName;
            //calls the getLogger() when there's a problem in reading the file
			LogManager.getLogger().severe(message);
			//Exception is thrown
			throw new FrequencyException(message);
        }
 
        return output;		
	}
	
	//method deletes the file passed in the arguement
	public static boolean removeFile(String filename)
	{
		File f= new File(filename);
		return f.delete(); 
	}
	
//	public static void main(String[] args)
//	{
//		
//		
//		try {
//			FileManager.writeFile("files/orderfile.csv", "test,test,test,test,test");
//		} catch (FrequencyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		try 
//		{
//			String content = FileManager.readFile("files/orderfile.csv");
//			
//			System.out.println(content);
//		} catch (FrequencyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
	
   
} 
