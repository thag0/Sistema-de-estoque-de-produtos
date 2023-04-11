package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import core.Estoque;

/**
 * Classe responsavel pelo gerenciamento de leitura e gravação do arquivo gerado pelo programa
 */
public class GerenciadorDados{

   String caminho = "./resource/";
   String caminhoBackup = "./resource/backup/";

   public GerenciadorDados(){
   }

   /**
    * Faz a gravação Estoque e cria um arquivo .dat 
    * @param estoque Objeto do Estoque
    * @param nomeArquivo nome do arquivo, com extensão, para gravação
    * @return Retorna verdadeiro caso consiga gravar o arquivo, retorna falso caso contrário
    */
   public boolean gravarEstoque(Estoque estoque, String nomeArquivo){
      Calendar calendario = Calendar.getInstance();

      String diaAtual = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY));
      diaAtual += "-" + String.valueOf(calendario.get(Calendar.MINUTE));
      diaAtual += " " + String.valueOf(calendario.get(Calendar.DAY_OF_MONTH));
      diaAtual += "-" + String.valueOf(calendario.get(Calendar.MONTH));
      diaAtual += "-" + String.valueOf(calendario.get(Calendar.YEAR));

      String nomeArquivoBackup = "backup " + diaAtual + " - " + nomeArquivo;

      try{
         ObjectOutputStream objetoGravacao = new ObjectOutputStream(new FileOutputStream(caminho + nomeArquivo));
         ObjectOutputStream objetoGravacaoBackup = new ObjectOutputStream(new FileOutputStream(caminhoBackup + nomeArquivoBackup));

         objetoGravacao.writeObject(estoque);
         objetoGravacao.close();

         objetoGravacaoBackup.writeObject(estoque);
         objetoGravacaoBackup.close();

         return true;
      }catch(Exception e){
         System.out.println("\n" + e);
      }
      return false;
   }


   /**
    * Faz a leitura do arquivo salvo a partir do objeto Estoque
    * @param nomeArquivo nome do arquivo, com extensão, para leitura
    * @return retorna um objeto Estoque 
    */
   public Estoque lerEstoque(String nomeArquivo){
      Estoque estoque = new Estoque();

      if(verificarArquivoExistente(caminho + nomeArquivo)){
         try{
            FileInputStream arqLeitura = new FileInputStream(caminho + nomeArquivo);
            ObjectInputStream objLeitura = new ObjectInputStream(arqLeitura);

            estoque = (Estoque) objLeitura.readObject();
            arqLeitura.close();

            return estoque;
         }catch(Exception e){}
      }

      return estoque;
   }
   

   /**
    * Verifica se o arquivo especificado existe
    * @param nomeArquivo nome do arquivo para busca
    * @return  retorna verdadeiro se encontrar o arquivo, retorna falso caso contrário
    */
   public boolean verificarArquivoExistente(String caminhoArquivo){
      File file = new File(caminhoArquivo);
      if(file.exists()){
         return true;
      }
      return false;
   }


   /**
    * @return retorna o caminho relativo onde os arquivos são salvos
    */
   public String getCaminho(){
      return this.caminho;
   }

}
