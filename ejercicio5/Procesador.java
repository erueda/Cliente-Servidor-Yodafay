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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente! 
//
public class Procesador extends Thread{
	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private Socket socketServicio;
	// stream de lectura (por aquí se recibe lo que envía el cliente)
	private InputStream inputStream;
	// stream de escritura (por aquí se envía los datos al cliente)
	private OutputStream outputStream;
	
	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private Random random;
	
        
	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public   Procesador (Socket socketServicio)  {
		this.socketServicio=socketServicio;
		random=new Random();
	}

	
	
        
	// Aquí es donde se realiza el procesamiento realmente:
        @Override
	 public void run(){
		
		// Como máximo leeremos un bloque de 1024 bytes. Esto se puede modificar.
		String datosRecibidos ;
                int numeroCliente;
		int NumeroSecretoServidor=random.nextInt(10);
		// Array de bytes para enviar la respuesta. Podemos reservar memoria cuando vayamos a enviarla:
		String datosEnviar;
		int respuestaServidor=0;
                
                System.out.println("Numero Secreto Servidor: "+NumeroSecretoServidor);

                int NumAdivinar=random.nextInt(10);
                
		try {
                    
                  int respuesta;
                  do
                  {
                      
                      //------------ Para Cliente -------------------
                      //procesamos la pregunta del cliente
                        System.out.println("----- Turno Cliente -----");
			BufferedReader inReader =new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
                        datosRecibidos=inReader.readLine();
                        

			numeroCliente=Integer.valueOf(datosRecibidos);
			respuesta=CalculoNum(numeroCliente,NumeroSecretoServidor);
                        
                        datosEnviar=String.valueOf(respuesta);
			
			// Enviamos la traducción de Yoda:
			////////////////////////////////////////////////////////
			System.out.println("Servidor envia respuesta a cliente"+datosEnviar);
                        PrintWriter outPrinter;
                        outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
			outPrinter.println(datosEnviar);
                        outPrinter.flush();
                      
                        
                   if(respuesta!=-1 && respuestaServidor!=-1 && numeroCliente!=-1){
                        
                        //--------------- Para Servidor ------------------------
                        // el servidor pregunta al cliente si su numero esta bien
                        
                        String buferEnvio=String.valueOf(NumAdivinar);
                        System.out.println("Servidor envia "+buferEnvio);
                           Thread.sleep(500);
                        PrintWriter outPrinter2 = new PrintWriter(socketServicio.getOutputStream(),true);
                        outPrinter2.println(buferEnvio);
                        outPrinter2.flush();

                        //respuesta
                        BufferedReader inReader2 = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));                   
                        String buferRecepcion=inReader2.readLine();  
                        respuestaServidor=Integer.valueOf(buferRecepcion);
                            
                            
                        System.out.println("Servidor recibe "+respuestaServidor);
                             
                        if(respuestaServidor==3)
                               NumAdivinar=random.nextInt(10);
                        if(respuestaServidor==2)
                               NumAdivinar=random.nextInt(NumAdivinar);
                        if (respuestaServidor==-1)
                               System.out.println("El Servidor a Ganado!! El numero es: "+NumAdivinar);
                      
                   }
                  }while(respuesta!=-1 && respuestaServidor!=-1 && numeroCliente!=-1);
                  
                  socketServicio.close();

		} catch (IOException e) {
			System.err.println("Error al obtener los flujos de entrada/salida.");
		} catch (InterruptedException ex) {
                Logger.getLogger(Procesador.class.getName()).log(Level.SEVERE, null, ex);
            }

	}

	// Yoda interpreta una frase y la devuelve en su "dialecto":
	private  int CalculoNum(int numeroCliente,int NumeroSecretoServidor) {
		
               int respuesta;
               if(NumeroSecretoServidor==numeroCliente){
                  System.out.println("El cliente a Ganado!! el numero es:" + NumeroSecretoServidor);
                  respuesta= -1;
               }
               else if(NumeroSecretoServidor<numeroCliente){
                  System.out.println("El numero es menor");
                  respuesta= 2;
               }
               else{  
                  System.out.println("El numero es mayor");
                  respuesta= 3;
               }
               return respuesta;
	}

    private PrintWriter newPrintWriter(OutputStream outputStream, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
