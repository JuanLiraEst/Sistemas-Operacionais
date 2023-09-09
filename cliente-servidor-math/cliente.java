// Cliente
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class ClienteUDP {
  
    public static void main(String[] args) {
        System.out.println("Cliente UDP");
        DatagramSocket socket = null;
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("Digite a operação no formato 'operação:valor1:valor2' (ex: soma:4:3):");
            String mensagem = scanner.nextLine();

            byte[] m = mensagem.getBytes();
            InetAddress endereco = InetAddress.getByName("localhost");
            int porta = 7890;

            DatagramPacket pacoteMensagem = new DatagramPacket(m, m.length, endereco, porta);
            socket = new DatagramSocket();
            socket.send(pacoteMensagem);

            byte[] buffer = new byte[1000];
            DatagramPacket resposta = new DatagramPacket(buffer, buffer.length);
            socket.receive(resposta);
            System.out.println("Resultado: " + new String(resposta.getData()).trim());

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO " + e.getMessage());
        } finally {
            if (socket != null) {
                socket.close();
            }
            scanner.close();
        }
    }
}
