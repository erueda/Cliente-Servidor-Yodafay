package fr2;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class YodafyClienteTCP {

	public static void main(String[] args) {
		
		String buferEnvio;

		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8889;
		
		// Socket para la conexión TCP
		Socket socketServicio;
		
		try {
			// Creamos un socket que se conecte a "hist" y "port":
			//////////////////////////////////////////////////////
			
                        socketServicio=new Socket (host,port);
                        
          
			buferEnvio="Al monte del volcan debes ir sin demora";
			
                        
                        //
                        PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                        outPrinter.println(buferEnvio);
                        
                        
                        
			BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
                       
                        String buferRecepcion=inReader.readLine();
                        
			// MOstremos la cadena de caracteres recibidos:
			System.out.println("Recibido: ");
			System.out.print(buferRecepcion);
			
			
			// Una vez terminado el servicio, cerramos el socket (automáticamente se cierran
			// el inpuStream  y el outputStream)
			//////////////////////////////////////////////////////

			socketServicio.close();
                        
			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}
