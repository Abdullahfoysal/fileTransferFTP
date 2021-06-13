/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swe.filetransfer.client;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.*;
import java.util.*;

/**
 *
 * @author foysalmac
 */
public class transferFileClient {
    
    Socket ClientSoc;

    DataInputStream din;
    DataOutputStream dout;
    BufferedReader br;
    PrintWriter pw;
    String clientDirectory = "/Users/foysalmac/Desktop/client1";
    transferFileClient(Socket soc,String clientDirectory)
    {
        this.clientDirectory=clientDirectory;
        
        try
        {
            ClientSoc=soc;
            din=new DataInputStream(ClientSoc.getInputStream());
            dout=new DataOutputStream(ClientSoc.getOutputStream());
            br=new BufferedReader(new InputStreamReader(System.in));
            //pw = new PrintWriter(ClientSoc.getOutputStream(), true);
        }
        catch(Exception ex){
            System.out.println(ex);
        }        
    }
//      void SendFile() throws Exception
//    {        
//
//        String filename;
//        System.out.print("Enter File Name :\n");
//        filename=br.readLine();
//       
//
//        File f=new File(clientDirectory+filename);
//        if(!f.exists())
//        {
//            System.out.println("File not Exists...\n");
//            dout.writeUTF("File not found\n");
//            return;
//        }
//
//        dout.writeUTF(filename);
//        pw.println(filename);
//
//        String msgFromServer=din.readUTF();
//        
//         System.out.println("Message from server: "+msgFromServer);
//         
//        if(msgFromServer.compareTo("File Already Exists")==0)
//        {
//            String Option;
//            System.out.println("File Already Exists. Want to OverWrite (Y/N) ?\n");
//            Option=br.readLine();            
//            if(Option=="Y")    
//            {
//                dout.writeUTF("Y");
//            }
//            else
//            {
//                dout.writeUTF("N");
//                return;
//            }
//        }
//
//        System.out.println("Sending File ...\n");
//        FileInputStream fin= new FileInputStream(f);
//        int ch;
//        do
//        {
//            ch=fin.read();
//            dout.writeUTF(String.valueOf(ch));
//        }
//        while(ch!=-1);
//        fin.close();
//        System.out.println(din.readUTF());
//
//    }
//
//    void ReceiveFile() throws Exception
//    {
//        String fileName;
//        System.out.print("Enter File Name :\n");
//        fileName=br.readLine();
//        dout.writeUTF(fileName);
//        String msgFromServer=din.readUTF();
//
//        if(msgFromServer.compareTo("File Not Found")==0)
//        {
//            System.out.println("File not found on Server ...\n");
//            return;
//        }
//        else if(msgFromServer.compareTo("READY")==0)
//        {
//            System.out.println("Receiving File ...\n");
//            File f=new File(clientDirectory+fileName);
//            if(f.exists())
//            {
//                String Option;
//                System.out.println("File Already Exists. Want to OverWrite (Y/N) ?\n");
//                Option=br.readLine();            
//                if(Option=="N")    
//                {
//                    dout.flush();
//                    return;    
//                }                
//            }
//            FileOutputStream fout=new FileOutputStream(f);
//            int ch;
//            String temp;
//            do
//            {
//                temp=din.readUTF();
//                ch=Integer.parseInt(temp);
//                if(ch!=-1)
//                {
//                    fout.write(ch);                    
//                }
//            }while(ch!=-1);
//            fout.close();
//            System.out.println(din.readUTF());
//
//        }
//
//
//    }
    
    String getSplitedFileName(String fileDir){
        String[] namesList = fileDir.split("/");
        String fileName = namesList[namesList.length-1];
        return fileName;
    }

    String uploadFile(String fileDir) throws IOException{
        String fileName = getSplitedFileName(fileDir);
         dout.writeUTF("upload");
         String msgFromServer=din.readUTF();
         if(msgFromServer.compareTo("okUpload")==0){
             dout.writeUTF(fileName);
             
        msgFromServer=din.readUTF();
        System.out.println("Message from server on client..."+msgFromServer);
             
        System.out.println("Uploading File start...\n");
        File f=new File(fileDir);
        FileInputStream fin= new FileInputStream(f);
        int ch;
        do
        {
            ch=fin.read();
            dout.writeUTF(String.valueOf(ch));
        }
        while(ch!=-1);
        fin.close();
        
        System.out.println("Uploading File End on client...\n");
        msgFromServer=din.readUTF();
        System.out.println(msgFromServer);
        return msgFromServer;
       }
         return "Something went wrong";
              
         
         
    }

    String downloadFile(String fileName) throws IOException{
         dout.writeUTF("download");
         String msgFromServer=din.readUTF();
         if(msgFromServer.compareTo("okDownload")==0){
             dout.writeUTF(fileName);
             
          
        System.out.println("Download File start...\n");
        File f=new File(clientDirectory+"/"+fileName);
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
                
        System.out.println("Downloading File End on client...\n");
        dout.writeUTF("File Download Successfully on Client"); 
      
      msgFromServer=din.readUTF();
        System.out.println("File Download Successfully on Client"+msgFromServer);
        return msgFromServer;
       }
         return "Something went wrong";
              
         
         
    }
    
    String deleteFile(String fileName) throws IOException{
         dout.writeUTF("delete");
         String msgFromServer=din.readUTF();
         if(msgFromServer.compareTo("okDelete")==0){
             dout.writeUTF(fileName);
             
 
         msgFromServer=din.readUTF();
        System.out.println("File Download Successfully on Client"+msgFromServer);
        return msgFromServer;
       }
         return "Something went wrong";
              
         
         
    }
    
    String getServerFile() throws IOException{
         dout.writeUTF("serverFile");
         String msgFromServer=din.readUTF();
         
        // System.out.println("on client"+msgFromServer);
         return msgFromServer;
           
              
    }
    
    

    void SendFile(String fileDir,String fileName) throws Exception
    {        

       // String filename;
      //  System.out.print("Enter File Name :\n");
        //filename=br.readLine();
       

        File f=new File(fileDir);
        System.out.println("sending file is "+f.exists());
//        if(!f.exists())
//        {
//            System.out.println("File not Exists...\n");
//            dout.writeUTF("File not found");
//            return;
//        }

        dout.writeUTF(fileName);
        //pw.println(fileDir);
        
        dout.writeUTF(fileName);
        

       
         // System.out.println("Message from server: "+msgFromServer);
//        if(msgFromServer.compareTo("File Already Exists")==0)
//        {
//            String Option;
//            System.out.println("File Already Exists. Want to OverWrite (Y/N) ?\n");
//            Option=br.readLine();            
//            if(Option=="Y")    
//            {
//                dout.writeUTF("Y");
//            }
//            else
//            {
//                dout.writeUTF("N");
//                return;
//            }
//        }

 String msgFromServer=din.readUTF();
 
    System.out.println("message on client from server if file exist:"+ msgFromServer);

       if(msgFromServer.compareTo("READY")==0){
           System.out.println("Sending File ...\n");
        FileInputStream fin= new FileInputStream(f);
        int ch;
        do
        {
            ch=fin.read();
            dout.writeUTF(String.valueOf(ch));
        }
        while(ch!=-1);
        fin.close();
       }
        System.out.println(din.readUTF());

    }

    void ReceiveFile(String fileDir) throws Exception
    {
       // String fileName;
        System.out.print("Enter File Name :\n");
      //  fileName=br.readLine();
        dout.writeUTF(fileDir);
        String msgFromServer=din.readUTF();
          System.out.print("reply from server: "+msgFromServer);

        if(msgFromServer.compareTo("File Not Found")==0)
        {
            System.out.println("File not found on Server ...\n");
            return;
        }
        else if(msgFromServer.compareTo("READY")==0)
        {
            System.out.println("Receiving File ...\n");
            File f=new File(fileDir);
            if(f.exists())
            {
                String Option;
                System.out.println("File Already Exists. Want to OverWrite (Y/N) ?\n");
                Option=br.readLine();            
                if(Option=="N")    
                {
                    dout.flush();
                    return;    
                }                
            }
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
            System.out.println(din.readUTF());

        }


    }


}
