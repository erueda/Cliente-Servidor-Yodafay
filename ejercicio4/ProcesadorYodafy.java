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
import java.util.Random;


//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente! 
//
public class ProcesadorYodafy {
	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private DatagramPacket packet;
	
	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private Random random;
	
	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase


        ProcesadorYodafy(DatagramPacket packet) {
                    this.packet=packet;
                    random=new Random();
        }
	
	
	// Aquí es donde se realiza el procesamiento realmente:
	void procesa() throws IOException{
		
		// Como máximo leeremos un bloque de 1024 bytes. Esto se puede modificar.
		byte [] datosRecibidos=null;

		
		// Array de bytes para enviar la respuesta. Podemos reservar memoria cuando vayamos a enviarla:
		byte [] datosEnviar;
                datosRecibidos=packet.getData();
                
         
                String peticion=new String(datosRecibidos,0,packet.getLength());
               
                String respuesta=yodaDo(peticion);
                
                //********** Enviar datos ***************
    
                datosEnviar=respuesta.getBytes();
                
                
                packet = new DatagramPacket(datosEnviar, datosEnviar.length, packet.getAddress(),packet.getPort());
                        
                DatagramSocket socket = new DatagramSocket();
                        
                socket.send(packet);

	}

	// Yoda interpreta una frase y la devuelve en su "dialecto":
	private String yodaDo(String peticion) {
		// Desordenamos las palabras:
		String[] s = peticion.split(" ");
		String resultado="";
		
		for(int i=0;i<s.length;i++){
			int j=random.nextInt(s.length);
			int k=random.nextInt(s.length);
			String tmp=s[j];
			
			s[j]=s[k];
			s[k]=tmp;
		}
		
		resultado=s[0];
		for(int i=1;i<s.length;i++){
		  resultado+=" "+s[i];
		}
		
		return resultado;
	}

    private PrintWriter newPrintWriter(OutputStream outputStream, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
