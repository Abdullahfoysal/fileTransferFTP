/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swe.filetransfer.server;

import java.net.ServerSocket;

/**
 *
 * @author foysalmac
 */
public class ftpServer {
    
    
     public static void main(String args[]) throws Exception
    {
        String serverDirectory="/Users/foysalmac/Desktop/server";
        ServerSocket soc=new ServerSocket(5217);
        System.out.println("FTP Server Started on Port Number 5217\n");
        while(true)
        {
            System.out.println("Waiting for Connection ...\n");
            //TransferFileServerThread t = new TransferFileServerThread(soc.accept());
            tranferFileServerThread t=new tranferFileServerThread(soc.accept(),serverDirectory);

        }
    }
    
}
