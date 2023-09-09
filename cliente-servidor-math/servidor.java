// Servidor
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServidorUDP {
  public static void main(String[] args) {
    System.out.println("Servidor UDP on");
    DatagramSocket socket = null;
    
    try {
      socket = new DatagramSocket(7890);
      byte[] buffer = new byte[1000];
      while (true) {
        DatagramPacket requisicao = new DatagramPacket(buffer, buffer.length);
        socket.receive(requisicao);
        System.out.println("Recebi requisicao: " + requisicao.getAddress().toString());

        String operacao = new String(requisicao.getData()).trim();
        System.out.println("Operação recebida: " + operacao);
        
        double result = calcula(operacao);
        System.out.println("Resultado calculado: " + result); // Exibindo o resultado no servidor.
        
        byte[] respByte = String.valueOf(result).getBytes();
        
        DatagramPacket resposta = new DatagramPacket(
                respByte,
                respByte.length,
                requisicao.getAddress(),
                requisicao.getPort());
        socket.send(resposta);
      }
    } 
    catch (SocketException e) {
      System.out.println("Socket: " + e.getMessage());
    } 
    catch (IOException e) {
      System.out.println("IO " + e.getMessage());
    }
  }

  private static double calcula(String request) {
    String[] tokens = request.split(":");

    double valor1 = 0;
    double valor2 = 0;

    try {
      valor1 = Double.parseDouble(tokens[1]);
      valor2 = Double.parseDouble(tokens[2]);
    } 
    catch (NumberFormatException e) {
      throw new IllegalArgumentException("Os valores fornecidos não são números válidos.");
    }
    switch (tokens[0]) {
      case "soma":
        return valor1 + valor2;
      case "subtrai":
        return valor1 - valor2;
      case "multiplica":
        return valor1 * valor2;
      case "expoente":
        return Math.pow(valor1, valor2);
      case "porcentagem":
        return valor1/100*valor2;
      case "divide":
        if (valor2 != 0) {
          return valor1 / valor2;
        } 
        else {
          throw new ArithmeticException("Divisão por zero.");
        }
      default:
        throw new IllegalArgumentException("Operação inválida.");
    }
  }
}
