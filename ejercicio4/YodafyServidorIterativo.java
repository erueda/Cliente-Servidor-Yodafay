package fr2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class YodafyServidorIterativo {
    

	public static void main(String[] args) throws SocketException {
            
            
            int puerto=8889;
            
            // array de bytes auxiliar para recibir o enviar datos.
            byte [] buffer=new byte[1024];
            // Número de bytes leídos

             

            try {
                        // Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
                        DatagramSocket socketServidor;
                        socketServidor = new DatagramSocket(puerto);
			// Mientras ... siempre!
			do {
				
				// Aceptamos una nueva conexión con accept()
				/////////////////////////////////////////////////

				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                                socketServidor.receive(packet);
                                
                               
				// Creamos un objeto de la clase ProcesadorYodafy, pasándole como 
				// argumento el nuevo socketDatagramSocket socketServidor;, para que realice el procesamiento
				// Este esquema permite que se puedan usar hebras más fácilmente.
				ProcesadorYodafy procesador=new ProcesadorYodafy(packet);
				procesador.procesa();
				
			} while (true);
			
                  } catch (IOException e) {
                         System.out.println("Error: no se pudo atender en elpuerto "+puerto);
                  }

	}

}
