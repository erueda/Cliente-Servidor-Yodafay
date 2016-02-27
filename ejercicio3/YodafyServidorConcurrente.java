package fr2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class YodafyServidorConcurrente {
    private static Socket socketServicio;

	public static void main(String[] args) {
	
            // Puerto de escucha
            int puerto=8889;
 
            ServerSocket socketServidor ;   

            try {
                        // Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
                
                        socketServidor = new ServerSocket(puerto);

			// Mientras ... siempre!
			do {
				
				// Aceptamos una nueva conexión con accept()
				/////////////////////////////////////////////////

				Socket socketConexion  = socketServidor.accept();
                               
				// Creamos un objeto de la clase ProcesadorYodafy, pasándole como 
				// argumento el nuevo socket, para que realice el procesamiento
				// Este esquema permite que se puedan usar hebras más fácilmente.
				ProcesadorYodafy procesador=new ProcesadorYodafy(socketConexion);
				procesador.start();
				
			} while (true);
			
                  } catch (IOException e) {
                         System.out.println("Error: no se pudo atender en elpuerto "+puerto);
                  }

	}

}
