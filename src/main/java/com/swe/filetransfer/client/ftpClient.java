/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swe.filetransfer.client;

import java.net.Socket;

/**
 *
 * @author foysalmac
 */
public class ftpClient {
    
     public static void main(String args[]) throws Exception
    {
        Socket soc=new Socket("127.0.0.1",5217);
        transferFileClient t=new transferFileClient(soc,"");
       // t.displayMenu();

    }
    
}
