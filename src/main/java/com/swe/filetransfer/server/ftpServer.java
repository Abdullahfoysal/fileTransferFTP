/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swe.filetransfer.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author foysalmac
 */
public class FtpServer {
    
    public String serverDirectory="/Users/foysalmac/Desktop/server";
    int port;
   // public static  ServerSocket soc;
   // public static  TranferFileServerThread transferFileServer;
      
    public FtpServer(int port,String serverDirectory) throws IOException{
          this.port= port;
          this.serverDirectory= serverDirectory;
         // soc=new ServerSocket(port);
          
          System.out.println("FTP Server Started on Port Number :"+port);
          
//           while(true)
//        {
//            System.out.println("Waiting for Connection ...\n");
//            //TransferFileServerThread t = new TransferFileServerThread(soc.accept());
//             transferFileServer=new TranferFileServerThread(soc.accept(),serverDirectory);
//
//        }
      }
    
    
     public static void main(String args[]) throws Exception
    {
      
//         soc=new ServerSocket(5217);
//        System.out.println("FTP Server Started on Port Number 5217\n");
//        while(true)
//        {
//            System.out.println("Waiting for Connection ...\n");
//            //TransferFileServerThread t = new TransferFileServerThread(soc.accept());
//             t=new tranferFileServerThread(soc.accept(),serverDirectory);
//
//        }
    } 
    
}
