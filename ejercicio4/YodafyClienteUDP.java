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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class YodafyClienteUDP {

	public static void main(String[] args) throws SocketException, IOException {
		
            
                DatagramSocket socket;
                socket = new DatagramSocket();
                int puerto=8889;
                
                

		
		try {
			// Creamos un socket que se conecte a "hist" y "port":
			//////////////////////////////////////////////////////
			
                        InetAddress direccion;
                        DatagramPacket paquete;
                        byte[] buferEnvio = new byte[1024];
                        direccion = InetAddress.getByName("localhost");

                       
                             
                      
			
			// Si queremos enviar una cadena de caracteres por un OutputStream, hay que pasarla primero
			// a un array de bytes:
			buferEnvio="Al monte del volcan debes ir sin demora".getBytes();
			
			// Enviamos el array por el outputStream;
			//////////////////////////////////////////////////////
                     
                        
                         paquete = new DatagramPacket(buferEnvio, buferEnvio.length, direccion,puerto);
                         socket.send(paquete);
                        
			// Aunque le indiquemos a TCP que queremos enviar varios arrays de bytes, sólo
			// los enviará efectivamente cuando considere que tiene suficientes datos que enviar...
			// Podemos usar "flush()" para obligar a TCP a que no espere para hacer el envío:
			//////////////////////////////////////////////////////

                        
			// Leemos la respuesta del servidor. Para ello le pasamos un array de bytes, que intentará
			// rellenar. El método "read(...)" devolverá el número de bytes leídos.
			//////////////////////////////////////////////////////

			byte[] buferLeido = new byte[1024];
                        paquete = new DatagramPacket(buferLeido, buferLeido.length);
                        socket.receive(paquete);
                        paquete.getData();
                        paquete.getAddress();
                        paquete.getPort();
                        
                       
			// MOstremos la cadena de caracteres recibidos:
			System.out.println("Recibido: ");
                        
                        for(int i=0;i<buferLeido.length;i++)
                        {
				System.out.print((char)buferLeido[i]);
			}
		
			
			// Una vez terminado el servicio, cerramos el socket (automáticamente se cierran
			// el inpuStream  y el outputStream)
			//////////////////////////////////////////////////////

			socket.close();
                        
			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}
