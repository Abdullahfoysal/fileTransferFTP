/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swe.filetransfer.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import javax.swing.DefaultListModel;

/**
 *
 * @author foysalmac
 */
public class TranferFileServerThread extends Thread{
    
    Socket ClientSoc;
    DataInputStream din;
    DataOutputStream dout;
    String serverDirectory="/Users/foysalmac/Desktop/server";
    DefaultListModel<String> jListServerModel;
    javax.swing.JLabel hostAddressTxt;

    TranferFileServerThread(Socket soc,String serverDirector)
    {
        this.serverDirectory=serverDirectory;
        this.jListServerModel=jListServerModel;
        try
        {
            ClientSoc=soc;                        
            din=new DataInputStream(ClientSoc.getInputStream());
            dout=new DataOutputStream(ClientSoc.getOutputStream());
             System.out.println("FTP Server HostAddress:"+ ClientSoc.getInetAddress().getHostName());
        System.out.println("FTP Server Started on Port Number :"+ClientSoc.getPort());
            System.out.println("FTP Client Connected ...\n");
            start();

        }
        catch(Exception ex){
            
        }        
    }
    
   String getSocketHostAddress(){
       System.out.println("calling get socket Host address");
       return ClientSoc.getInetAddress().getHostName();
   }
    void setServerFileListToServer(DefaultListModel<String> jListServerModel,javax.swing.JLabel hostAddressTxt){
        this.jListServerModel=jListServerModel;
        this.hostAddressTxt= hostAddressTxt;
        this.hostAddressTxt.setText(getSocketHostAddress()+"kdfaksdka");
    }
    void SendFile() throws Exception
    {        
         System.out.println("asce sendFIle on server ********");
        String filename=din.readUTF();
        System.out.println("Response from send file server : "+filename);
        
        System.out.println("asce sendFIle on server aaaa");
        
        File f=new File(serverDirectory+filename);
        
            dout.writeUTF("READY");
            FileOutputStream fout=new FileOutputStream(f);
                int ch;
                String temp;
                do
                {
                    temp=din.readUTF();
                    ch=Integer.parseInt(temp);
                    if(ch!=-1)
                    {
                        fout.write(ch);                    
                    }
                }while(ch!=-1);
                fout.close();
                //dout.writeUTF("File Send Successfully");
            dout.writeUTF("File Receive Successfully");                            
        
    }

    void ReceiveFile() throws Exception
    {
        String filename=din.readUTF();
        if(filename.compareTo("File not found")==0)
        {
            return;
        }
        File f=new File(serverDirectory+filename);
        String option;

        if(f.exists())
        {
            dout.writeUTF("File Already Exists");
            option=din.readUTF();
        }
        else
        {
            dout.writeUTF("SendFile");
            option="Y";
        }

            if(option.compareTo("Y")==0)
            {
                FileOutputStream fout=new FileOutputStream(f);
                int ch;
                String temp;
                do
                {
                    temp=din.readUTF();
                    ch=Integer.parseInt(temp);
                    if(ch!=-1)
                    {
                        fout.write(ch);                    
                    }
                }while(ch!=-1);
                fout.close();
                dout.writeUTF("File Send Successfully");
            }
            else
            {
                return;
            }

    }
    
    void SaveFileFromClient()throws Exception{
         String filename=din.readUTF();
          dout.writeUTF("startUpload");
          File f=new File(serverDirectory+"/"+filename);
         FileOutputStream fout=new FileOutputStream(f);
                int ch;
                String temp;
                do
                {
                    temp=din.readUTF();
                    ch=Integer.parseInt(temp);
                    if(ch!=-1)
                    {
                        fout.write(ch);                    
                    }
                }while(ch!=-1);
                fout.close();
               
            dout.writeUTF("File Upload Successfully on server"); 
            
            listFilesonServer(new File(serverDirectory));
         
         
         
    }
    
    void SendFileFromServer()throws Exception{
         String filename=din.readUTF();
        
          System.out.println(" File start download from server...\n");
        File f=new File(serverDirectory+"/"+filename);
        FileInputStream fin= new FileInputStream(f);
        int ch;
        do
        {
            ch=fin.read();
            dout.writeUTF(String.valueOf(ch));
        }
        while(ch!=-1);
        fin.close();
        filename=din.readUTF();
       System.out.println(" File End download from server...\n"+filename);
               
       dout.writeUTF("Thanks for successfully download from server"); 
         
         listFilesonServer(new File(serverDirectory));
         
    }
    
     String  listFilesForFolder(final File folder) {
           
	String temp_names="";
		
	   for (final File fileEntry : folder.listFiles()) {
		   if (!fileEntry.isDirectory()) {
			   temp_names =temp_names+"/"+fileEntry.getName();
                       
                           
		   } 
		  
	   }
          
            
            return temp_names;
	   
			
	}
     
      void  listFilesonServer(final File folder) {
           
	jListServerModel.clear();
		
	   for (final File fileEntry : folder.listFiles()) {
		   if (!fileEntry.isDirectory()) {
			   
                           jListServerModel.addElement(fileEntry.getName());
		   } 
		  
	   }
          
            
           
	   
			
	}
    void deleteFile()throws Exception{
         String filename=din.readUTF();
          File f=new File(serverDirectory+"/"+filename);
           boolean response= f.delete();
               
         dout.writeUTF("File Delete Successfully on server: "+response); 
         
          listFilesonServer(new File(serverDirectory));
         
    }
    
    
      @Override
    public void run()
    {
        while(true)
        {
            try
            {
           // System.out.println("Waiting for Command ...\n");
            String Command=din.readUTF();
            System.out.println("Server geeting this message form client :"+Command);
            
            if(Command.compareTo("upload")==0){
                dout.writeUTF("okUpload");
               
                 SaveFileFromClient();
            }
            else if(Command.compareTo("download")==0){
                dout.writeUTF("okDownload");
               
                 SendFileFromServer();
            }
            else if(Command.compareTo("serverFile")==0){
                
            String temp_names= listFilesForFolder(new File(serverDirectory));
                
                dout.writeUTF(temp_names);
                
            }
            else if(Command.compareTo("delete")==0){
                 dout.writeUTF("okDelete");
                 deleteFile();
                 
            }
            
//            if(Command.compareTo("GET")==0)
//            {
//                System.out.println("\tGET Command Received ...\n");
//                SendFile();
//                continue;
//            }
//            else if(Command.compareTo("SEND")==0)
//            {
//                System.out.println("\tSEND Command Receiced ...\n");  
//                ReceiveFile();
//                continue;
//            }
            else if(Command.compareTo("DISCONNECT")==0)
            {
                System.out.println("\t Disconnect Command Received ...\n");
                System.exit(1);
            }
            }
            catch(Exception ex)
            {
            }
        }
    }
    

//    @Override
//    public void run()
//    {
//        while(true)
//        {
//            try
//            {
//           // System.out.println("Waiting for Command ...\n");
//            String Command=din.readUTF();
//            System.out.println("Server geeting this message form client :"+Command);
//            if(Command.compareTo("GET")==0)
//            {
//                System.out.println("\tGET Command Received ...\n");
//                SendFile();
//                continue;
//            }
//            else if(Command.compareTo("SEND")==0)
//            {
//                System.out.println("\tSEND Command Receiced ...\n");  
//                ReceiveFile();
//                continue;
//            }
//            else if(Command.compareTo("DISCONNECT")==0)
//            {
//                System.out.println("\t Disconnect Command Received ...\n");
//                System.exit(1);
//            }
//            }
//            catch(Exception ex)
//            {
//            }
//        }
//    }
//    
}
