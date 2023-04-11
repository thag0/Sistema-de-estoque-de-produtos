package structure;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import core.Estoque;
import core.Produto;
import data.GerenciadorDados;
import ui.PainelCadastro;
import ui.PainelEdicaoProduto;
import ui.PainelInformacoes;
import ui.PainelLerDados;
import ui.PainelLista;
import ui.PainelPrincipal;
import ui.PainelRegistrarVenda;
import ui.PainelRemocao;
import ui.PainelSalvarDados;


public class Logica{

   PainelPrincipal pp;
   LeitorTeclado leitorTeclado;

   Estoque estoque = new Estoque();
   GerenciadorDados gd = new GerenciadorDados();
   String nomeArquivo = "produtos.dat";

   public boolean produtoEncontrado = false; 
   boolean produtoRemovido = false, produtoAdicionadoRecentemente = false;

   //variavel de busca
   int indiceProduto;

   boolean arquivoGravado, arquivoSobrescritoRecusado;
   boolean arquivoLido = false, arquivoInexistente = false;
   boolean recarregamentoArquivoCancelado = false;
   boolean sobrescritaCancelada = false, sobrescritaConfirmada = false;//painel edição

   public Logica(PainelPrincipal pp){
      this.pp = pp;
      this.leitorTeclado = pp.getLeitorTeclado();
   }


   //funções lógicas dos painéis

   /**
    * Administra as teclas pressionadas do painel de Menu (PainelPrincipal)
    */
   public void telaMenu(){

      if(pp.estadoAtual == pp.estadoMenu){
         if(leitorTeclado.getSetaCima()){
            if(pp.indiceMenu-1 < 1){
               pp.indiceMenu = pp.maxOpcoesMenuPrincipal;
            
            }else{
               pp.indiceMenu--;
            }       
            leitorTeclado.setSetaCima(false);
         
         }else if(leitorTeclado.getSetaBaixo()){
            if(pp.indiceMenu+1 > pp.maxOpcoesMenuPrincipal){
               pp.indiceMenu = 1;
            
            }else{
               pp.indiceMenu++;
            }
            leitorTeclado.setSetaBaixo(false);
         
         } 
      
      }
      if(leitorTeclado.getEnter()){
         switch(pp.indiceMenu){
            case 1:
               pp.desativarPainel();
               pp.pc.ativarPainel();

               pp.pc.limparCamposTexto();
               pp.estadoAtual = pp.estadoCadastroProduto;
            break;

            case 2:
               pp.desativarPainel();
               pp.pr.ativarPainel();

               pp.pr.limparCamposTexto();

               pp.estadoAtual = pp.estadoRemocaoProduto;
            break;

            case 3:
               pp.desativarPainel();
               pp.pl.ativarPainel();

               pp.estadoAtual = pp.estadoListaProduto;
            break;

            case 4:
               pp.desativarPainel();
               pp.psd.ativarPainel();
               
               pp.estadoAtual = pp.estadoSalvarDados;
            break;

            case 5:
               pp.desativarPainel();
               pp.pld.ativarPainel();

               pp.estadoAtual = pp.estadoLerDados;
            break;

            case 6:
               pp.desativarPainel();
               pp.prv.ativarPainel();

               pp.prv.limparCamposTexto();

               pp.estadoAtual = pp.estadoRegistrarVenda;
            break;

            case 7:
               pp.desativarPainel();
               pp.pi.ativarPainel();

               pp.estadoAtual = pp.estadoInformacoes;
            break;

            case 8:
               pp.desativarPainel();
               pp.pep.ativarPainel();

               pp.estadoAtual = pp.estadoEditarProduto;
            break;
         }     
         leitorTeclado.setEnter(false);
      }
       
   }


   /**
    * Administra as teclas pressionadas do painel de cadastro além do foco 
    * dos objetos e suas cores corrrespondentes e as informações de um label
    * interativo
    * @param painel PainelCadastro necessário
    */
   public void telaCadastroProduto(PainelCadastro painel){
      //administrar os focos correspondentes do painel
      verificarFoco(painel.caixaTextoCodigoProduto, painel.focarCodigoProduto, painel.corCaixaTextoSelecionado, painel.corCaixaTextoNaoSelecionado);
      verificarFoco(painel.caixaTextoNomeProduto, painel.focarNomeProduto, painel.corCaixaTextoSelecionado, painel.corCaixaTextoNaoSelecionado);
      verificarFoco(painel.caixaTextoValorProduto, painel.focarValorProduto, painel.corCaixaTextoSelecionado, painel.corCaixaTextoNaoSelecionado);
      verificarFoco(painel.caixaTextoUnidadesProduto, painel.focarUnidadesProduto, painel.corCaixaTextoSelecionado, painel.corCaixaTextoNaoSelecionado);

      if(!painel.focarCodigoProduto && !painel.focarNomeProduto && !painel.focarValorProduto && !painel.focarUnidadesProduto) painel.requestFocus();

      //lógicas dos textos interativos
      if(painel.caixaTextoCodigoProduto.getText().isEmpty()){
         painel.label.setText("Digite o código do produto");
      
      }else if(painel.caixaTextoNomeProduto.getText().isEmpty()){
         painel.label.setText("Digite o nome do produto");
      
      }else if(painel.caixaTextoValorProduto.getText().isEmpty()){
         painel.label.setText("Digite o valor do produto");
      
      }else if(painel.caixaTextoUnidadesProduto.getText().isEmpty()){
         painel.label.setText("Digite as unidades do produto");      
      }
      
      if((procurarProdutoPorCodigo(estoque, painel.caixaTextoCodigoProduto.getText()) != -1) && !produtoAdicionadoRecentemente){
         painel.label.setText("Código já registrado anteriormente");

         painel.botao.setEnabled(false);
         painel.botao.setText("");
      }else{
         painel.botao.setEnabled(true);
         painel.botao.setText(painel.textoPadraoBotao);
      }

      //lógica das teclas
      if(leitorTeclado.getEnter()){
         adicionarProduto(painel);
         leitorTeclado.setEnter(false);

      }else if(leitorTeclado.getEsc()){
         produtoAdicionadoRecentemente = false;

         pp.ativarPainel();
         painel.desativarPainel();

         pp.estadoAtual = pp.estadoMenu;
         leitorTeclado.setEsc(false);
      }

   }


   /**
    * Administra as teclas pressionadas do painel de remoção além do foco 
    * dos objetos e suas cores corrrespondentese as informações de um label
    * interativo
    * @param painel PainelRemocao necessário
    */
   public void telaRemocaoProduto(PainelRemocao painel){
      verificarFoco(painel.caixaTextoCodigoProduto, painel.focarCodigoProduto, painel.corCaixaTextoSelecionado, painel.corCaixaTextoNaoSelecionado);
      if(!painel.focarCodigoProduto) painel.requestFocus();

      if(painel.caixaTextoCodigoProduto.getText().isEmpty()){
         produtoEncontrado = false;
         painel.label.setText("Digite um código de produto");
      
      }else if(!painel.caixaTextoCodigoProduto.getText().isEmpty()){

         indiceProduto = procurarProdutoPorCodigo(estoque, painel.caixaTextoCodigoProduto.getText());
         
         if(indiceProduto != -1){
            produtoEncontrado = true;
            painel.label.setText(getInformacoesProduto(estoque, indiceProduto));
         
         }else if(produtoRemovido){
            painel.label.setText("Produto removido");
         
         }else{
            painel.label.setText("Produto não encontrado");
         }
      }

      if(leitorTeclado.getEsc()){
         produtoRemovido = false;

         pp.ativarPainel();
         painel.desativarPainel();

         pp.estadoAtual = pp.estadoMenu;
         leitorTeclado.setEsc(false);
      }

   }


   /**
    * Administra as teclas pressionadas do painel de lista,
    * as informações de um label interativo e da caixa de texto 
    * com as informações dos produtos
    * @param painel PainelLista necessário
    */
   public void telaListaProduto(PainelLista painel){

      painel.requestFocus();

      String texto; 

      if(estoque == null || estoque.getTamanho() == 0){
         painel.telaRolagem.setVisible(false);

         texto = "Estoque vazio";
         painel.label.setVisible(true);
      
      }else{
         painel.label.setVisible(false);

         texto = getInformacoesProdutos();
         painel.areaTexto.setText(texto);
         painel.telaRolagem.setVisible(true);
      }
      

      if(leitorTeclado.getEsc()){
         pp.ativarPainel();
         painel.desativarPainel();

         pp.estadoAtual = pp.estadoMenu;
         leitorTeclado.setEsc(false);
      }
   }


   /**
    * Administra as teclas pressionadas do painel salvar dados e
    * as informações de um label interativo
    * @param painel PainelSalvarDados necessário
    */
   public void telaSalvarDados(PainelSalvarDados painel){

      painel.requestFocus();

      if(gd.verificarArquivoExistente(nomeArquivo)){
         painel.label.setText("Arquivo encontrado");
      
      }else{
         painel.label.setText("Confirmar para salvar");         
      }

      if(arquivoSobrescritoRecusado) painel.label.setText("Gravação recusada");

      if(arquivoGravado || (arquivoGravado && arquivoSobrescritoRecusado)) painel.label.setText("Arquivo gravado");


      if(leitorTeclado.getEnter()){// evitar usar 100% o mouse
         salvarDados();
         leitorTeclado.setEnter(false);

      }else if(leitorTeclado.getEsc()){
         pp.ativarPainel();
         painel.desativarPainel();

         arquivoGravado = false;
         arquivoSobrescritoRecusado = false;

         pp.estadoAtual = pp.estadoMenu;
         leitorTeclado.setEsc(false);
      }
   }


   /**
    * Administra as teclas pressionadas do painel ler dados e
    * as informações de um label interativo
    * @param painel PainelLerDados necessário
    */
   public void telaLerDados(PainelLerDados painel){
      painel.requestFocus();

      if(!arquivoGravado && !arquivoInexistente) painel.label.setText("Nenhum dado carregado");
      if(arquivoInexistente) painel.label.setText("Arquivo \"" + nomeArquivo + "\" não encontrado");
      
      if(arquivoLido) painel.label.setText("Arquivo lido");
      if(recarregamentoArquivoCancelado) painel.label.setText("Recarregamento cancelado");

      if(leitorTeclado.getEnter()){// evitar usar 100% o mouse
         lerDados();
         leitorTeclado.setEnter(false);

      }else if(leitorTeclado.getEsc()){
         pp.ativarPainel();
         painel.desativarPainel();

         recarregamentoArquivoCancelado = false;

         pp.estadoAtual = pp.estadoMenu;
         leitorTeclado.setEsc(false);
      }
   }


   /**
    * Administra as teclas pressionadas do painel de registrar venda além de
    * do foco dos objetos correspondentes e as informações de um label interativo
    * @param painel PainelRegistrarVenda necessário
    */
   public void telaRegistrarVenda(PainelRegistrarVenda painel){

      verificarFoco(painel.caixaTextoCodigo, painel.focarCaixaTextoCodigo, painel.corCaixaTextoSelecionado, painel.corCaixaTextoNaoSelecionado);
      verificarFoco(painel.caixaTextoUnidades, painel.focarCaixaTextoUnidades, painel.corCaixaTextoSelecionado, painel.corCaixaTextoNaoSelecionado);

      if(!painel.focarCaixaTextoCodigo && !painel.focarCaixaTextoUnidades) painel.requestFocus();

      // logica de texto
      if(pp.prv.focarCaixaTextoUnidades) painel.caixaTextoUnidades.requestFocus();

      if(!painel.caixaTextoCodigo.getText().isEmpty()){
         indiceProduto = procurarProdutoPorCodigo(estoque, painel.caixaTextoCodigo.getText());

         if(indiceProduto != -1){
            painel.label.setText(estoque.getProdutos().get(indiceProduto).getNome() + " R$" + 
            estoque.getProdutos().get(indiceProduto).getValor() + " " + estoque.getProdutos().get(indiceProduto).getUnidades() + " un");
         
         }else{
            painel.label.setText("Produto não encontrado");
         }
      
      }else{
         painel.label.setText("Digite um código para busca");
      }

      if(painel.caixaTextoCodigo.getText().isEmpty() && painel.caixaTextoUnidades.getText().isEmpty()){
         painel.labelFeedback.setText("Preencha os campos");
      
      }else if(painel.caixaTextoCodigo.getText().isEmpty()){
         painel.labelFeedback.setText("Digite o código do produto");
      
      }else if(painel.caixaTextoUnidades.getText().isEmpty()){
         painel.labelFeedback.setText("Digite as unidades vendidas");
      }

      if(leitorTeclado.getEnter()){
         registrarVenda(painel);
         leitorTeclado.setEnter(false);

      }else if(leitorTeclado.getEsc()){
         pp.ativarPainel();
         painel.desativarPainel();

         pp.estadoAtual = pp.estadoMenu;
         leitorTeclado.setEsc(false);
      }
   }


   /**
    * Administra as teclas pressionadas do painel de informações do estoque
    * @param painel PainelInformacoes necessário
    */
   public void telaInformacoes(PainelInformacoes painel){
      painel.requestFocus(true);

      if(leitorTeclado.getEsc()){
         pp.ativarPainel();
         painel.desativarPainel();

         pp.estadoAtual = pp.estadoMenu;
         leitorTeclado.setEsc(false);
      }
   }


   /**
    * Administra as teclas pressionadas do painel de informações de edição de produto
    * @param painel PainelEdicaoProduto necessário
    */
   public void telaEditarProduto(PainelEdicaoProduto painel){

      verificarFoco(painel.caixaTextoCodigo, painel.focarCaixaCodigo, painel.corCaixaTextoSelecionado, painel.corCaixaTextoNaoSelecionado);
      verificarFoco(painel.caixaTextoNome, painel.focarCaixaNome, painel.corCaixaTextoSelecionado, painel.corCaixaTextoNaoSelecionado);
      verificarFoco(painel.caixaTextoValor, painel.focarCaixaValor, painel.corCaixaTextoSelecionado, painel.corCaixaTextoNaoSelecionado);
      verificarFoco(painel.caixaTextoUnidades, painel.focarCaixaUnidades, painel.corCaixaTextoSelecionado, painel.corCaixaTextoNaoSelecionado);

      if(!painel.focarCaixaCodigo && !painel.focarCaixaNome && !painel.focarCaixaValor && !painel.focarCaixaUnidades){
         painel.requestFocus();
      }

      // parte logica
      if(procurarProdutoPorCodigo(estoque, painel.caixaTextoCodigo.getText()) == -1){
         painel.caixaTextoNome.setEnabled(false);
         painel.caixaTextoValor.setEnabled(false);
         painel.caixaTextoUnidades.setEnabled(false);

         if(!painel.caixaTextoCodigo.getText().isEmpty()) painel.label.setText("Produto não encontrado");
      
      }else{
         painel.caixaTextoNome.setEnabled(true);
         painel.caixaTextoValor.setEnabled(true);
         painel.caixaTextoUnidades.setEnabled(true);

         if(!sobrescritaConfirmada){
            indiceProduto = procurarProdutoPorCodigo(estoque, painel.caixaTextoCodigo.getText());
            painel.label.setText(getInformacoesProduto(estoque, indiceProduto));        
         }
      }

      //texto interativo
      if(sobrescritaCancelada && !sobrescritaConfirmada) painel.label.setText("Sobrescrita cancelada");
      if(sobrescritaConfirmada || (sobrescritaCancelada && sobrescritaConfirmada)) painel.label.setText("Dados alterados");

      //lidando com teclado
      if(leitorTeclado.getEsc()){
         pp.ativarPainel();
         pp.pep.desativarPainel();
         painel.limparCamposTexto();

         sobrescritaCancelada = false; 
         sobrescritaConfirmada = false;

         pp.estadoAtual = pp.estadoMenu;
      }
   }


   /**
    * Busca as informações de um produto específico
    * @param estoque Estoque para busca
    * @param indice  indice do estoque para captura de informações
    * @return  retorna uma string com as informações recuperadas
    */
   private String getInformacoesProduto(Estoque estoque, int indice){
      String texto = "";
      try{
         texto = estoque.getProdutos().get(indiceProduto).getNome() + " R$" + 
         estoque.getProdutos().get(indiceProduto).getValor() + " " + estoque.getProdutos().get(indiceProduto).getUnidades() + " un";
         return texto;
      }catch(Exception e){

      }
      return texto;
   }


   /**
    * Configura o foco da caixa de texto com base na sua variavel de verificação correspondente
    * @param caixaTexto caixa de texto que será verificada
    * @param focar variável correspondente ao foco da caixa de texto
    * @param corSelecionado cor usada caso a caixa de texto esteja em foco
    * @param corNaoSelecionado cor usada caso a caixa de texto não esteja em foco
    */
   private void verificarFoco(JTextField caixaTexto, boolean focar, Color corSelecionado, Color corNaoSelecionado){
      if(focar){
         caixaTexto.requestFocus();
         caixaTexto.setBackground(corSelecionado);
      }else{
         caixaTexto.setBackground(corNaoSelecionado);
      }
   }


   //funções logicas

   /**
    * Adiciona um produto ao Estoque, usando as informações registradas
    * no objeto de caixa de texto
    * @param painel PainelCadastro necessário para acesso aos componentes
    */
   public void adicionarProduto(PainelCadastro painel){

      String codigo = painel.caixaTextoCodigoProduto.getText();
      String nome = painel.caixaTextoNomeProduto.getText();
      String valorCaixaTexto = painel.caixaTextoValorProduto.getText();
      String unidadesCaixaTexto = painel.caixaTextoUnidadesProduto.getText();

      try{
         if(valorCaixaTexto.contains(",")){
            valorCaixaTexto = valorCaixaTexto.replace(",", ".");
         }

         double valor = Double.parseDouble(valorCaixaTexto);
         int unidades = Integer.parseInt(unidadesCaixaTexto);

         if((!painel.caixaTextoCodigoProduto.getText().isEmpty()) && (!painel.caixaTextoNomeProduto.getText().isEmpty()) && 
         (!painel.caixaTextoValorProduto.getText().isEmpty()) && (!painel.caixaTextoUnidadesProduto.getText().isEmpty())){
            
            estoque.adicionarProduto(codigo, nome, valor, unidades);
            produtoAdicionadoRecentemente = true;
            painel.label.setText("Produto adicionado");
            return;
         }                         
      }catch(Exception e){
      }
      painel.label.setText("Dados inválidos");
   }


   /**
    * Excluito um produto do Estoque, usando o código de produto único
    * de cada produto com base na informação registrada na caixa de texto
    * @param painel PainelRemocao necessário para acesso aos componentes
    */
   public void excluirProduto(PainelRemocao painel){
      String codigoRemocao = painel.caixaTextoCodigoProduto.getText();
      int indiceBusca = procurarProdutoPorCodigo(estoque, codigoRemocao);

      if(indiceBusca != -1){
         estoque.getProdutos().remove(indiceBusca);
         produtoRemovido = true;
      }
   }


   /**
    * Salva os dados num arquivo separado na pasta "resource"
    */
   public void salvarDados(){
      int opcao = -99;//apenas pra não conflitar entre os retornos 
      
      if(gd.verificarArquivoExistente(gd.getCaminho() + nomeArquivo)){
         String[] botoes = {"Sim", "Não"};
         String mensagem = "Foi detectado um arquivo existente de nome \"" + nomeArquivo + "\"  \ndeseja sobrescrever o arquivo ?";
         String tituloJanela = "Conflito entre arquivos";
         opcao = JOptionPane.showOptionDialog(null, mensagem, tituloJanela, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, botoes, 0); 

         if(opcao == 1 || opcao == -1){
            arquivoSobrescritoRecusado = true;// opção não
         }        
      }
      
      if((!gd.verificarArquivoExistente(nomeArquivo) || opcao == 0)){
         arquivoGravado =  gd.gravarEstoque(estoque, nomeArquivo);
         arquivoGravado = true;
      }
   }


   /**
    * Lê o arquivo de dados na pasta "resource"
    */
   public void lerDados(){
      int opcao = -99;

      if(estoque != null){
         if(estoque.getTamanho() != 0){
            String[] botoes = {"Sim", "Não"};
            String tituloJanela = "Arquivo já aberto";
            String mensagem = "O arquivo de produtos já foi carregado.\nCaso carregue novamente perderá todas\nas alterações feitas nessa sessão.\nConfirmar escolha?";
            opcao = JOptionPane.showOptionDialog(null, mensagem, tituloJanela, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, botoes, 0);
            recarregamentoArquivoCancelado = true;
         }
      }

      if(estoque.getTamanho() == 0 || opcao == 0){
         estoque = gd.lerEstoque(nomeArquivo);

         if(estoque instanceof Estoque && (estoque.getTamanho() != 0)){
            recarregamentoArquivoCancelado = false;
            arquivoLido = true;
         
         }else arquivoInexistente = true;
      }
   }


   /**
    * Calcula o valor de caixa gerado com base no valor do produto indicado
    * e as quantidades vendidas
    * @param painel PainelRegistrarVenda necessário para acesso aos componentes
    */
   public void registrarVenda(PainelRegistrarVenda painel){
      String codigoProduto = painel.caixaTextoCodigo.getText();
      String unidadesProduto = painel.caixaTextoUnidades.getText();

      try{
         int indiceBusca = procurarProdutoPorCodigo(estoque, codigoProduto);
         int unidadesVendidas = Integer.parseInt(unidadesProduto);

         if(unidadesVendidas != 0){
            if(unidadesVendidas <= estoque.getProdutos().get(indiceBusca).getUnidades()){
               estoque.adicionarCaixa((unidadesVendidas * estoque.getProdutos().get(indiceBusca).getValor()));
               estoque.getProdutos().get(indiceBusca).reduzirUnidades(unidadesVendidas);
               painel.labelFeedback.setText("Venda registrada");
            
            }else{
               painel.labelFeedback.setText("As unidades solicitadas excedem o valor registrado");
            }
         }

      }catch(Exception e){

      }
   }


   /**
    * Edita as propriedades de um produto
    * @param painel PainelEdicaoProduto necessário
    */
   public void editarProduto(PainelEdicaoProduto painel){
      if(!painel.caixaTextoCodigo.getText().isEmpty() && !painel.caixaTextoNome.getText().isEmpty() 
      && !painel.caixaTextoValor.getText().isEmpty() && !painel.caixaTextoUnidades.getText().isEmpty()){

         String codigo = painel.caixaTextoCodigo.getText();
         String nomeNovo = painel.caixaTextoNome.getText();
         String valorNovoS = painel.caixaTextoValor.getText();
         String unidadesNovasS = painel.caixaTextoUnidades.getText();

         try{
            valorNovoS = valorNovoS.replace(",", ".");
            double valorNovo = Double.parseDouble(valorNovoS);
            int unidadesNovas = Integer.parseInt(unidadesNovasS);
            int indice = procurarProdutoPorCodigo(estoque, codigo);

            String[] botoes = {"Sim", "Não"};
            String tituloJanela = "Confirmar escolha";
            String mensagem = "Deseja mesmo alterar os dados do produto ?";
            int opcao = JOptionPane.showOptionDialog(null, mensagem, tituloJanela, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, botoes, 0);

            if(opcao == -1 || opcao == 1) sobrescritaCancelada = true;            
            else {
               estoque.sobrescreverProduto(indice, codigo, nomeNovo, valorNovo, unidadesNovas);
               sobrescritaConfirmada = true;
            }

         }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Verifique os campos");
         }
         
      }

   }


   /**
    * Faz uma varredura nos produtos do Estoque e retorna o índice correspondente
    * na lista pelo código informado
    * @param estoque Estoque para leitura
    * @param codigo Código do produto para busca
    * @return retorna o indice na lista de produtos, retorna -1 caso não encontre 
    */
   public static int procurarProdutoPorCodigo(Estoque estoque, String codigo){
      for(int i = 0; i < estoque.getTamanho(); i++){
         if(codigo.equals(estoque.getProdutos().get(i).getCodigo())){
            return i;
         }
      }
      return -1;
   }


   /**
    * Obtém os dados de todos os produtos registrados
    * @return retorna um texto de acordo com os dados lidos do estoque
    */
   private String getInformacoesProdutos(){
      String texto = "";
      if(estoque.getTamanho() != 0){
         for(Produto produto : estoque.getProdutos()){
            texto += "Cód: " + produto.getCodigo() + "  -  " + produto.getNome() + "  -  R$: " + produto.getValor() + "  -  " +
             produto.getUnidades() + " unidades\n\n";
         }
      }
      return texto;
   }


   /**
    * @return retorna o nome do arquivo gerado pelo programa
    */
   public String getNomeArquivo(){
      return this.nomeArquivo;
   }


   /**
    * @return retorna o objeto Estoque 
    */
   public Estoque getEstoque(){
      return this.estoque;
   }
}
