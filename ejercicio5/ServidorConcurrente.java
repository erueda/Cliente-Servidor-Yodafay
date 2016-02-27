package fr2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Math.random;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class ServidorConcurrente {

	public static void main(String[] args) {
            Random random;

            // Puerto de escucha
            int puerto=8889;
            random = new Random();
            ServerSocket socketServidor ;   
            

            try {
                       
                        socketServidor = new ServerSocket(puerto);		
			do {

				Socket socketConexion  = socketServidor.accept();
				Procesador procesa=new Procesador(socketConexion);
				procesa.start();
				
			} while (true);
			
                  } catch (IOException e) {
                         System.out.println("Error: no se pudo atender en elpuerto "+puerto);
                  }

	}

}
