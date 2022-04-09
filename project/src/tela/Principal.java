package tela;

// Projeto Final

// Luiz Claudio dos Santos Cazarin Junior - UC19100111
// Deyziane de Queiroz Castelo Branco - UC19101193

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import tela.enumerador.SituacaoInicio;

public class Principal extends JFrame {
	private static final long serialVersionUID = 1L;
	JTabbedPane tpJogo;

	// *** Inicio *************************************************************
	private SituacaoInicio situacaoInicio;
	private JPanel pnJogador;
	private JTextField tfNome;
	private JPanel pnCivilizacao;
	private JComboBox<String> cbCivilizacoes;
	private JRadioButton rbCriarJogo;
	private JPanel pnServidor;
	private DefaultTableModel tmJogadores;
	private JTable tblJogadores;
	private JButton btnCriarJogo;
	private JButton btnDestruirJogo;
	private JButton btnIniciarJogo;
	private JButton btnEncerrarJogo;
	private JRadioButton rbConectarJogo;
	private JPanel pnCliente;
	private JLabel lblIPServidor;
	private JTextField tfIpServidor;
	private JButton btnConectar;
	private JButton btnDesconectar;
	private JPanel pnMensagens;
	private JTextArea taMensagens;
	private JLabel lblMensagem;
	private JTextField tfMensagem;
	private JButton btnEnviar;

	// *** Vila **************************************************************
	private JPanel pnTP_Vila;
	private JTable tblAldeoes;
	private DefaultTableModel tmAldeoes;
	private JComboBox<String> cbFazenda;
	private JComboBox<String> cbMinaOuro;
	private JTable tblFazendas;
	private DefaultTableModel tmFazendas;
	private JTable tblMinasOuro;
	private DefaultTableModel tmMinasOuro;
	private JLabel lblComida;
	private JLabel lblOuro;
	private JTextField tfPrefeitura;
	private JPanel pnTemplo;
	private JPanel pnOferenda;
	private JLabel lblOferenda;
	private JTextField tfTemplo;
	private JComboBox<String> cbTEmploEvolucoes;
	private JButton btnTemploEvoluir;
	private JComboBox<String> cbTemploLancamentos;
	private JComboBox<String> cbTemploInimigo;
	private JButton btnTemploLancar;
	private JPanel pnMaravilha;
	private JLabel lblMaravilha;
	private JProgressBar pbMaravilha;

	List<String> evolucoes = new ArrayList<String>();
	public Prefeitura prefeitura;
	public Templo templo;
	public Jogador jogador;
	public ClienteTCP cliente;
	public Subir_Servidor subirServidor;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Principal window = new Principal();
			window.setVisible(true);

		});

	}

	public Principal() {
		getContentPane().setBackground(Color.DARK_GRAY);

		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/tela/img/icone.png")));
		initialize();
		this.prefeitura = new Prefeitura(this);
		prefeitura.start();

	}

	@SuppressWarnings("serial")
	private void initialize() {
		this.setTitle("Jogo de Estratégia em Tempo Real");
		this.setResizable(false);
		this.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		this.setBounds(100, 100, 886, 720);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

		// *** Configuracoes **************************************************

		DefaultTableCellRenderer dtcrCentralizado = new DefaultTableCellRenderer();
		dtcrCentralizado.setHorizontalAlignment(SwingConstants.CENTER);

		DefaultTableCellRenderer dtcrAldeaoAcao = new DefaultTableCellRenderer() {
			public void setValue(Object valor) {
				String v = valor.toString();
				if (v.equals("parado"))
					setBackground(Color.WHITE);
				else if (v.equals("orando"))
					setBackground(new Color(135, 206, 235));
				else if (v.equals("sacrificado"))
					setBackground(Color.RED);
				else if (v.contains("cultivando"))
					setBackground(Color.GREEN);
				else if (v.contains("minerando"))
					setBackground(Color.YELLOW);
				else if (v.contains("construindo"))
					setBackground(Color.LIGHT_GRAY);
				else
					setBackground(Color.LIGHT_GRAY);
				super.setValue(valor);
			}
		};

		// *** Componentes ****************************************************

		this.tpJogo = new JTabbedPane(JTabbedPane.TOP);
		tpJogo.setBackground(Color.WHITE);
		this.tpJogo.setBounds(10, 10, 850, 665);
		this.getContentPane().add(this.tpJogo);

		JPanel pnTP_Inicio = new JPanel();
		pnTP_Inicio.setBackground(Color.DARK_GRAY);
		pnTP_Inicio.setLayout(null);
		this.tpJogo.addTab("Início", null, pnTP_Inicio, null);

		// Paineis

		this.pnJogador = new JPanel();
		pnJogador.setBackground(Color.WHITE);
		this.pnJogador.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Nome",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		this.pnJogador.setBounds(10, 10, 300, 51);
		this.pnJogador.setLayout(null);
		pnTP_Inicio.add(this.pnJogador);

		this.tfNome = new JTextField();
		this.tfNome.setBounds(10, 17, 280, 20);
		this.pnJogador.add(this.tfNome);

		this.pnCivilizacao = new JPanel();
		pnCivilizacao.setBackground(Color.WHITE);
		this.pnCivilizacao.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Civiliza\u00E7\u00E3o", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		this.pnCivilizacao.setBounds(320, 10, 300, 51);
		this.pnCivilizacao.setLayout(null);
		pnTP_Inicio.add(this.pnCivilizacao);

		this.cbCivilizacoes = new JComboBox<String>();
		this.cbCivilizacoes.setBounds(10, 17, 280, 21);
		this.cbCivilizacoes.addItem("Acádia");
		this.cbCivilizacoes.addItem("Babilônia");
		this.cbCivilizacoes.addItem("Helenística");
		this.cbCivilizacoes.addItem("Mesopotâmica");
		this.cbCivilizacoes.addItem("Persa");
		this.cbCivilizacoes.addItem("Suméria");
		this.pnCivilizacao.add(this.cbCivilizacoes);

		String[] colunasJogadores = { "Jogador", "Civilização", "IP", "Situação" };
		this.tmJogadores = (new DefaultTableModel(null, colunasJogadores) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		this.tblJogadores = new JTable(this.tmJogadores);
		this.tblJogadores.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.tblJogadores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.tblJogadores.getColumnModel().getColumn(0).setResizable(false);
		this.tblJogadores.getColumnModel().getColumn(0).setPreferredWidth(150);
		this.tblJogadores.getColumnModel().getColumn(1).setResizable(false);
		this.tblJogadores.getColumnModel().getColumn(1).setPreferredWidth(120);
		this.tblJogadores.getColumnModel().getColumn(2).setResizable(false);
		this.tblJogadores.getColumnModel().getColumn(2).setPreferredWidth(120);
		this.tblJogadores.getColumnModel().getColumn(3).setResizable(false);
		this.tblJogadores.getColumnModel().getColumn(3).setPreferredWidth(417);
		this.tblJogadores.setEnabled(false);
		JScrollPane spJogadores = new JScrollPane(this.tblJogadores);
		spJogadores.setBounds(10, 72, 825, 149);
		spJogadores.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnTP_Inicio.add(spJogadores);

		this.rbCriarJogo = new JRadioButton("Criar jogo");
		this.rbCriarJogo.setSelected(true);
		this.rbCriarJogo.setBounds(20, 227, 82, 21);
		pnTP_Inicio.add(this.rbCriarJogo);

		this.pnServidor = new JPanel();
		pnServidor.setBackground(Color.WHITE);
		this.pnServidor.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.pnServidor.setBounds(10, 237, 825, 47);
		this.pnServidor.setLayout(null);
		pnTP_Inicio.add(this.pnServidor);

		this.btnCriarJogo = new JButton("Criar novo jogo");
		this.btnCriarJogo.setBounds(10, 15, 130, 21);
		this.pnServidor.add(this.btnCriarJogo);

		this.btnDestruirJogo = new JButton("Destruir Jogo");
		this.btnDestruirJogo.setBounds(150, 15, 130, 21);
		this.pnServidor.add(this.btnDestruirJogo);

		this.btnIniciarJogo = new JButton("Iniciar Jogo");
		this.btnIniciarJogo.setBounds(290, 15, 130, 21);
		this.pnServidor.add(this.btnIniciarJogo);

		this.btnEncerrarJogo = new JButton("Encerrar jogo");
		this.btnEncerrarJogo.setBounds(430, 15, 130, 21);
		this.pnServidor.add(this.btnEncerrarJogo);

		this.rbConectarJogo = new JRadioButton("Conectar a um jogo");
		this.rbConectarJogo.setBounds(20, 290, 135, 21);
		pnTP_Inicio.add(this.rbConectarJogo);

		ButtonGroup bgCriarConectar = new ButtonGroup();
		bgCriarConectar.add(this.rbCriarJogo);
		bgCriarConectar.add(this.rbConectarJogo);

		this.pnCliente = new JPanel();
		pnCliente.setBackground(Color.WHITE);
		this.pnCliente.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.pnCliente.setBounds(10, 300, 825, 65);
		this.pnCliente.setLayout(null);
		pnTP_Inicio.add(this.pnCliente);

		this.lblIPServidor = new JLabel("IP do computador que criou o jogo");
		this.lblIPServidor.setBounds(10, 15, 249, 13);
		this.pnCliente.add(this.lblIPServidor);

		this.tfIpServidor = new JTextField();
		this.tfIpServidor.setBounds(10, 30, 249, 21);
		this.pnCliente.add(this.tfIpServidor);

		this.btnConectar = new JButton("Conectar");
		this.btnConectar.setBounds(270, 30, 130, 21);
		this.pnCliente.add(this.btnConectar);

		this.btnDesconectar = new JButton("Desconectar");
		this.btnDesconectar.setBounds(410, 30, 130, 21);
		this.pnCliente.add(this.btnDesconectar);

		this.pnMensagens = new JPanel();
		pnMensagens.setBackground(Color.WHITE);
		this.pnMensagens.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Mensagens",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		this.pnMensagens.setBounds(10, 375, 825, 253);
		this.pnMensagens.setLayout(null);
		pnTP_Inicio.add(this.pnMensagens);

		this.taMensagens = new JTextArea();
		this.taMensagens.setEditable(false);
		JScrollPane sbMensagens = new JScrollPane(this.taMensagens, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sbMensagens.setBounds(new Rectangle(10, 20, 805, 193));
		this.pnMensagens.add(sbMensagens);

		this.lblMensagem = new JLabel("Mensagem");
		this.lblMensagem.setBounds(10, 223, 65, 13);
		this.pnMensagens.add(this.lblMensagem);

		this.tfMensagem = new JTextField();
		this.tfMensagem.setBounds(85, 220, 590, 21);
		this.pnMensagens.add(this.tfMensagem);

		this.btnEnviar = new JButton("Enviar");
		this.btnEnviar.setBounds(685, 220, 130, 21);
		this.pnMensagens.add(this.btnEnviar);

		// ----------------------------------------------------------------------------------------------------
		// Tela Vila
		// ----------------------------------------------------------------------------------------------------

		this.pnTP_Vila = new JPanel();
		pnTP_Vila.setBackground(Color.DARK_GRAY);
		this.pnTP_Vila.setLayout(null);
		this.tpJogo.addTab("Vila", null, this.pnTP_Vila, null);

		JPanel pnAldeao = new JPanel();
		pnAldeao.setBackground(Color.GRAY);
		pnAldeao.setLayout(null);
		pnAldeao.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Alde\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnAldeao.setBounds(10, 10, 270, 620);
		pnTP_Vila.add(pnAldeao);

		String[] colunasAldeoes = { "Nº", "Ação" };
		this.tmAldeoes = (new DefaultTableModel(null, colunasAldeoes) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		this.tblAldeoes = new JTable(this.tmAldeoes);
		this.tblAldeoes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.tblAldeoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.tblAldeoes.getColumn("Ação").setCellRenderer(dtcrAldeaoAcao);
		this.tblAldeoes.getColumnModel().getColumn(0).setResizable(false);
		this.tblAldeoes.getColumnModel().getColumn(0).setCellRenderer(dtcrCentralizado);
		this.tblAldeoes.getColumnModel().getColumn(0).setPreferredWidth(30);
		this.tblAldeoes.getColumnModel().getColumn(1).setResizable(false);
		this.tblAldeoes.getColumnModel().getColumn(1).setPreferredWidth(202);

		JScrollPane spAldeoes = new JScrollPane(this.tblAldeoes);
		spAldeoes.setBounds(10, 20, 250, 460);
		spAldeoes.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnAldeao.add(spAldeoes);

		JButton btnAldeaoParar = new JButton("Parar");
		btnAldeaoParar.setBackground(Color.LIGHT_GRAY);
		btnAldeaoParar.setForeground(Color.BLACK);
		btnAldeaoParar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnAldeaoParar.setBounds(10, 485, 120, 21);
		pnAldeao.add(btnAldeaoParar);

		JButton btnAldeaoConstruir = new JButton("Construir");
		btnAldeaoConstruir.setBackground(Color.LIGHT_GRAY);
		btnAldeaoConstruir.setForeground(Color.BLACK);
		btnAldeaoConstruir.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnAldeaoConstruir.setBounds(10, 510, 120, 21);
		pnAldeao.add(btnAldeaoConstruir);

		JComboBox<String> cbConstruir = new JComboBox<String>();
		cbConstruir.setBackground(Color.LIGHT_GRAY);
		cbConstruir.setForeground(Color.BLACK);
		cbConstruir.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		cbConstruir.setBounds(140, 510, 119, 21);
		cbConstruir.addItem("Fazenda");
		cbConstruir.addItem("Mina de ouro");
		cbConstruir.addItem("Templo");
		cbConstruir.addItem("Maravilha");
		pnAldeao.add(cbConstruir);

		JButton btnAldeaoCultivar = new JButton("Cultivar");
		btnAldeaoCultivar.setBackground(Color.LIGHT_GRAY);
		btnAldeaoCultivar.setForeground(Color.BLACK);
		btnAldeaoCultivar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnAldeaoCultivar.setBounds(10, 535, 120, 21);
		pnAldeao.add(btnAldeaoCultivar);

		this.cbFazenda = new JComboBox<String>();
		cbFazenda.setBackground(Color.LIGHT_GRAY);
		cbFazenda.setForeground(Color.BLACK);
		cbFazenda.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		cbFazenda.setBounds(140, 535, 119, 21);
		pnAldeao.add(cbFazenda);

		JButton btnAldeaoMinerar = new JButton("Minerar");
		btnAldeaoMinerar.setBackground(Color.LIGHT_GRAY);
		btnAldeaoMinerar.setForeground(Color.BLACK);
		btnAldeaoMinerar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnAldeaoMinerar.setBounds(10, 560, 120, 21);
		pnAldeao.add(btnAldeaoMinerar);

		this.cbMinaOuro = new JComboBox<String>();
		cbMinaOuro.setBackground(Color.LIGHT_GRAY);
		cbMinaOuro.setForeground(Color.BLACK);
		cbMinaOuro.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		cbMinaOuro.setBounds(140, 560, 119, 21);
		pnAldeao.add(cbMinaOuro);

		JButton btnAldeaoOrar = new JButton("Orar");
		btnAldeaoOrar.setBackground(Color.LIGHT_GRAY);
		btnAldeaoOrar.setForeground(Color.BLACK);
		btnAldeaoOrar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnAldeaoOrar.setBounds(10, 585, 120, 21);
		pnAldeao.add(btnAldeaoOrar);

		JButton btnAldeaoSacrificar = new JButton("Sacrificar");
		btnAldeaoSacrificar.setBackground(Color.LIGHT_GRAY);
		btnAldeaoSacrificar.setForeground(Color.BLACK);
		btnAldeaoSacrificar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnAldeaoSacrificar.setBounds(140, 585, 120, 21);
		pnAldeao.add(btnAldeaoSacrificar);

		JPanel pnFazenda = new JPanel();
		pnFazenda.setBackground(Color.GRAY);
		pnFazenda.setLayout(null);
		pnFazenda.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Fazendas",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnFazenda.setBounds(290, 10, 270, 305);
		pnTP_Vila.add(pnFazenda);

		String[] colunasFazendas = { "Nº", "Aldeões" };
		this.tmFazendas = (new DefaultTableModel(null, colunasFazendas) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		this.tblFazendas = new JTable(this.tmFazendas);
		this.tblFazendas.setRowSelectionAllowed(false);
		this.tblFazendas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.tblFazendas.getColumnModel().getColumn(0).setResizable(false);
		this.tblFazendas.getColumnModel().getColumn(0).setCellRenderer(dtcrCentralizado);
		this.tblFazendas.getColumnModel().getColumn(0).setPreferredWidth(30);
		this.tblFazendas.getColumnModel().getColumn(1).setResizable(false);
		this.tblFazendas.getColumnModel().getColumn(1).setPreferredWidth(202);

		JScrollPane spFazendas = new JScrollPane(this.tblFazendas);
		spFazendas.setBounds(10, 20, 250, 275);
		spFazendas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnFazenda.add(spFazendas);

		JPanel pnMinaOuro = new JPanel();
		pnMinaOuro.setBackground(Color.GRAY);
		pnMinaOuro.setLayout(null);
		pnMinaOuro.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Minas de ouro", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnMinaOuro.setBounds(290, 325, 270, 305);
		pnTP_Vila.add(pnMinaOuro);

		String[] colunasMinas = { "Nº", "Aldeões" };
		this.tmMinasOuro = (new DefaultTableModel(null, colunasMinas) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		this.tblMinasOuro = new JTable(this.tmMinasOuro);
		this.tblMinasOuro.setRowSelectionAllowed(false);
		this.tblMinasOuro.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.tblMinasOuro.getColumnModel().getColumn(0).setResizable(false);
		this.tblMinasOuro.getColumnModel().getColumn(0).setCellRenderer(dtcrCentralizado);
		this.tblMinasOuro.getColumnModel().getColumn(0).setPreferredWidth(30);
		this.tblMinasOuro.getColumnModel().getColumn(1).setResizable(false);
		this.tblMinasOuro.getColumnModel().getColumn(1).setPreferredWidth(202);

		JScrollPane spMinas = new JScrollPane(this.tblMinasOuro);
		spMinas.setBounds(10, 20, 250, 275);
		spMinas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnMinaOuro.add(spMinas);

		JPanel pnPrefeitura = new JPanel();
		pnPrefeitura.setBackground(Color.LIGHT_GRAY);
		pnPrefeitura.setLayout(null);
		pnPrefeitura.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Prefeitura", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnPrefeitura.setBounds(570, 10, 270, 175);
		pnTP_Vila.add(pnPrefeitura);

		JPanel pnComida = new JPanel();
		((FlowLayout) pnComida.getLayout()).setAlignment(FlowLayout.LEFT);
		pnComida.setBorder(new TitledBorder(null, "Comida", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnComida.setBounds(8, 15, 127, 45);
		pnPrefeitura.add(pnComida);

		this.lblComida = new JLabel("0");
		this.lblComida.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnComida.add(this.lblComida);

		JPanel pnOuro = new JPanel();
		((FlowLayout) pnOuro.getLayout()).setAlignment(FlowLayout.LEFT);
		pnOuro.setBorder(new TitledBorder(null, "Ouro", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnOuro.setBounds(135, 15, 128, 45);
		pnPrefeitura.add(pnOuro);

		this.lblOuro = new JLabel("0");
		this.lblOuro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnOuro.add(this.lblOuro);

		this.tfPrefeitura = new JTextField();
		tfPrefeitura.setBounds(10, 65, 250, 20);
		this.tfPrefeitura.setEditable(false);
		pnPrefeitura.add(tfPrefeitura);

		JButton btnPrefeituraCriarAldeao = new JButton("Criar aldeão");
		btnPrefeituraCriarAldeao.setBounds(10, 90, 128, 21);
		pnPrefeitura.add(btnPrefeituraCriarAldeao);

		JComboBox<String> cbPrefeituraEvolucoes = new JComboBox<String>();
		cbPrefeituraEvolucoes.setBounds(10, 115, 248, 21);
		cbPrefeituraEvolucoes.addItem("Evolução de aldeão");
		cbPrefeituraEvolucoes.addItem("Evolução de fazenda");
		cbPrefeituraEvolucoes.addItem("Evolução de mina de ouro");
		pnPrefeitura.add(cbPrefeituraEvolucoes);

		JButton btnPrefeituraEvoluir = new JButton("Evoluir");
		btnPrefeituraEvoluir.setBounds(131, 140, 128, 21);
		pnPrefeitura.add(btnPrefeituraEvoluir);

		this.pnTemplo = new JPanel();
		pnTemplo.setBackground(Color.LIGHT_GRAY);
		this.pnTemplo.setLayout(null);
		this.pnTemplo.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Templo",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		this.pnTemplo.setBounds(570, 195, 270, 225);
		this.pnTemplo.setEnabled(false);
		pnTP_Vila.add(this.pnTemplo);

		this.pnOferenda = new JPanel();
		((FlowLayout) this.pnOferenda.getLayout()).setAlignment(FlowLayout.LEFT);
		this.pnOferenda.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Oferendas de fé", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		this.pnOferenda.setBounds(8, 15, 255, 45);
		this.pnOferenda.setEnabled(false);
		this.pnTemplo.add(this.pnOferenda);

		this.lblOferenda = new JLabel("0");
		this.lblOferenda.setHorizontalAlignment(SwingConstants.LEFT);
		this.lblOferenda.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.lblOferenda.setEnabled(false);
		this.pnOferenda.add(this.lblOferenda);

		this.tfTemplo = new JTextField();
		this.tfTemplo.setEditable(false);
		this.tfTemplo.setBounds(10, 65, 250, 20);
		this.pnTemplo.add(this.tfTemplo);

		this.cbTEmploEvolucoes = new JComboBox<String>();
		this.cbTEmploEvolucoes.setBounds(10, 90, 248, 21);
		this.cbTEmploEvolucoes.addItem("Nuvem de gafanhotos");
		this.cbTEmploEvolucoes.addItem("Morte dos primogênitos");
		this.cbTEmploEvolucoes.addItem("Chuva de pedras");
		this.cbTEmploEvolucoes.addItem("Proteção contra nuvem de gafanhotos");
		this.cbTEmploEvolucoes.addItem("Proteção contra morte dos primogênitos");
		this.cbTEmploEvolucoes.addItem("Proteção contra chuva de pedras");
		this.cbTEmploEvolucoes.setEnabled(false);
		this.pnTemplo.add(this.cbTEmploEvolucoes);

		this.btnTemploEvoluir = new JButton("Evoluir");
		this.btnTemploEvoluir.setBounds(131, 115, 128, 21);
		this.btnTemploEvoluir.setEnabled(false);
		this.pnTemplo.add(this.btnTemploEvoluir);

		this.cbTemploLancamentos = new JComboBox<String>();
		this.cbTemploLancamentos.setBounds(10, 140, 248, 21);
		this.cbTemploLancamentos.setEnabled(false);
		this.pnTemplo.add(this.cbTemploLancamentos);

		this.cbTemploInimigo = new JComboBox<String>();
		this.cbTemploInimigo.setEnabled(false);
		this.cbTemploInimigo.setBounds(10, 165, 248, 21);
		this.pnTemplo.add(this.cbTemploInimigo);

		this.btnTemploLancar = new JButton("Lançar");
		this.btnTemploLancar.setBounds(131, 190, 128, 21);
		this.btnTemploLancar.setEnabled(false);
		this.pnTemplo.add(this.btnTemploLancar);

		this.pnMaravilha = new JPanel();
		pnMaravilha.setBackground(Color.LIGHT_GRAY);
		this.pnMaravilha.setLayout(null);
		this.pnMaravilha.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Maravilha",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		this.pnMaravilha.setBounds(570, 430, 270, 200);
		this.pnMaravilha.setEnabled(false);
		pnTP_Vila.add(this.pnMaravilha);

		this.lblMaravilha = new JLabel();
		this.lblMaravilha.setBounds(10, 20, 215, 170);
		this.lblMaravilha.setIcon(new ImageIcon(Principal.class.getResource("/tela/img/maravilha.png")));
		this.lblMaravilha.setEnabled(false);
		this.pnMaravilha.add(this.lblMaravilha);

		this.pbMaravilha = new JProgressBar();
		this.pbMaravilha.setOrientation(SwingConstants.VERTICAL);
		this.pbMaravilha.setBounds(225, 20, 30, 170);
		this.pbMaravilha.setMaximum(100000);
		this.pbMaravilha.setStringPainted(true);
		this.pbMaravilha.setEnabled(false);
		this.pnMaravilha.add(pbMaravilha);
		tpJogo.setSelectedIndex(0);

		// -----------------------------------------------------------------------------
		this.situacaoInicio = SituacaoInicio.INICIAL_CRIAR;
		this.habilitarInicio();

		// *** Eventos INICIAR*************************************************

		this.rbCriarJogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				situacaoInicio = SituacaoInicio.INICIAL_CRIAR;
				habilitarInicio();
			}
		});

		this.rbConectarJogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				situacaoInicio = SituacaoInicio.INICIAL_CONECTAR;
				habilitarInicio();
			}
		});

		this.btnCriarJogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					comandoCriarJogo(tfNome.getText(), cbCivilizacoes.getSelectedItem().toString());
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});

		this.btnDestruirJogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comandoDestruirJogo();
			}
		});

		this.btnIniciarJogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comandoIniciarJogo();
			}
		});

		this.btnEncerrarJogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comandoEncerrarJogo();
			}
		});

		this.btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comandoConectar(tfNome.getText(), cbCivilizacoes.getSelectedItem().toString(), tfIpServidor.getText());
			}
		});

		this.btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comandoDesconectar();
			}
		});

		this.tfMensagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comandoEnviarMensagem(tfMensagem.getText());
			}
		});

		this.btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comandoEnviarMensagem(tfMensagem.getText());
			}
		});

		// *** Eventos VILA ********************************************************

		btnAldeaoParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comandoAldeaoParar(tblAldeoes.getSelectedRow());
			}
		});

		btnAldeaoConstruir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comandoAldeaoConstruir(tblAldeoes.getSelectedRow(), cbConstruir.getSelectedItem().toString());
			}
		});

		btnAldeaoCultivar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comandoAldeaoCultivar(tblAldeoes.getSelectedRow(), cbFazenda.getSelectedIndex());
			}
		});

		btnAldeaoMinerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comandoAldeaoMinerar(tblAldeoes.getSelectedRow(), cbMinaOuro.getSelectedIndex());
			}
		});

		btnAldeaoOrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comandoAldeaoOrar(tblAldeoes.getSelectedRow());
			}
		});

		btnAldeaoSacrificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comandoAldeaoSacrificar(tblAldeoes.getSelectedRow());
			}
		});

		btnPrefeituraCriarAldeao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comandoPrefeituraCriarAldeao();
			}
		});

		btnPrefeituraEvoluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comandoPrefeituraEvoluir(cbPrefeituraEvolucoes.getSelectedItem().toString());
			}
		});

		btnTemploEvoluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comandoTemploEvoluir(cbTEmploEvolucoes.getSelectedItem().toString());
			}
		});

		btnTemploLancar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					comandoTemploLancar(cbTemploLancamentos.getSelectedItem().toString(),
							cbTemploInimigo.getSelectedItem().toString());
				} catch (Exception e2) {
				}

			}
		});

	}

	public void base() {
		// Mostrar valores iniciais
		this.mostrarComida(prefeitura.vila.qtdComida);
		this.mostrarOuro(prefeitura.vila.qtdOuro);
		this.mostrarOferendaFe(prefeitura.vila.qtdoferendasFe);
		this.mostrarPrefeitura(prefeitura.toString(), Color.cyan);
		this.habilitarTemplo();
		this.mostrarAtaques(evolucoes);
		this.mostrarTemplo("Templo", Color.RED);

	}

	public void testarInicio() {
		this.adicionarJogador("Jose", "Brasileiro", "100.200.300.400", "vivo");
		this.mostrarSituacaoJogador(1, "morto");
		this.adicionarMensagem("oi");
		this.adicionarMensagem("tudo bem?");
	}

	// ---------------------------------------------------------------------------------

	public void adicionarJogador(String jogador, String civilizacao, String ip, String situacao) {

		String[] linha = { jogador, civilizacao, ip, situacao };
		// nome do jogador, civilizacao, ip do jogador os status do jogador no momento
		this.tmJogadores.addRow(linha);
	}

	public void mostrarSituacaoJogador(int jogadorLinha, String situacao) {
		this.tmJogadores.setValueAt(situacao, jogadorLinha - 1, 3);
	}

	public void removerJogador(int jogadorLinha) {
		this.tmJogadores.removeRow(jogadorLinha - 1);
	}

	public void limparJogadores() {
		this.tmJogadores.setRowCount(0);
	}

	public void adicionarMensagem(String mensagem) {
		this.taMensagens.insert(mensagem + "\n", 0);
	}

	public void limparMensagens() {
		this.taMensagens.setText("");
	}

	private void habilitarInicio() {
		this.desabilitarInicio();
		switch (this.situacaoInicio) {
		case INICIAL_CRIAR:
			this.pnJogador.setEnabled(true);
			this.tfNome.setEnabled(true);
			this.pnCivilizacao.setEnabled(true);
			this.cbCivilizacoes.setEnabled(true);
			this.rbCriarJogo.setEnabled(true);
			this.pnServidor.setEnabled(true);
			this.btnCriarJogo.setEnabled(true);
			this.rbConectarJogo.setEnabled(true);
			this.tpJogo.setEnabledAt(1, true);
			this.tpJogo.setEnabledAt(1, false);
			break;
		case INICIAL_CONECTAR:
			this.pnJogador.setEnabled(true);
			this.tfNome.setEnabled(true);
			this.pnCivilizacao.setEnabled(true);
			this.cbCivilizacoes.setEnabled(true);
			this.rbCriarJogo.setEnabled(true);
			this.pnServidor.setEnabled(true);
			this.rbConectarJogo.setEnabled(true);
			this.pnCliente.setEnabled(true);
			this.lblIPServidor.setEnabled(true);
			this.tfIpServidor.setEnabled(true);
			this.btnConectar.setEnabled(true);
			break;
		case CRIAR_CRIADO:
			this.pnServidor.setEnabled(true);
			this.btnDestruirJogo.setEnabled(true);
			this.btnIniciarJogo.setEnabled(true);
			this.pnMensagens.setEnabled(true);
			this.taMensagens.setEnabled(true);
			this.lblMensagem.setEnabled(true);
			this.tfMensagem.setEnabled(true);
			this.btnEnviar.setEnabled(true);
			this.tpJogo.setEnabledAt(1, true);
			this.tpJogo.setEnabledAt(1, false);
			break;
		case CRIAR_INICIADO:
			this.pnServidor.setEnabled(true);
			this.btnEncerrarJogo.setEnabled(true);
			this.pnMensagens.setEnabled(true);
			this.taMensagens.setEnabled(true);
			this.lblMensagem.setEnabled(true);
			this.tfMensagem.setEnabled(true);
			this.btnEnviar.setEnabled(true);
			this.tpJogo.setEnabledAt(1, true);
			break;
		case CONECTADO:
			this.pnCliente.setEnabled(true);
			this.btnDesconectar.setEnabled(true);
			this.pnMensagens.setEnabled(true);
			this.taMensagens.setEnabled(true);
			this.lblMensagem.setEnabled(true);
			this.tfMensagem.setEnabled(true);
			this.btnEnviar.setEnabled(true);
			this.tpJogo.setEnabledAt(1, true);
		}
	}

	private void desabilitarInicio() {
		this.pnJogador.setEnabled(false);
		this.tfNome.setEnabled(false);
		this.pnCivilizacao.setEnabled(false);
		this.cbCivilizacoes.setEnabled(false);
		this.rbCriarJogo.setEnabled(false);
		this.pnServidor.setEnabled(false);
		this.btnCriarJogo.setEnabled(false);
		this.btnDestruirJogo.setEnabled(false);
		this.btnIniciarJogo.setEnabled(false);
		this.btnEncerrarJogo.setEnabled(false);
		this.rbConectarJogo.setEnabled(false);
		this.pnCliente.setEnabled(false);
		this.lblIPServidor.setEnabled(false);
		this.tfIpServidor.setEnabled(false);
		this.btnConectar.setEnabled(false);
		this.btnDesconectar.setEnabled(false);
		this.pnMensagens.setEnabled(false);
		this.taMensagens.setEnabled(false);
		this.lblMensagem.setEnabled(false);
		this.tfMensagem.setEnabled(false);
		this.btnEnviar.setEnabled(false);
		this.tpJogo.setEnabledAt(1, false);
	}

	// ---------------------------------------------------------------------------------

	// ************************************************************************
	// *** Entrada=Apresentação - altera valores dos componentes **************

	public void mostrarMensagemErro(String titulo, String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.ERROR_MESSAGE);
	}

	public void adicionarAldeao(String numero, String acao) {
		String[] linha = { numero, acao };
		this.tmAldeoes.addRow(linha);
	}

	public void mostrarAldeao(int aldeao, String acao) {
		this.tblAldeoes.setValueAt(acao, aldeao - 1, 1);
	}

	public void adicionarFazenda(String numero, String aldeoes) {
		String[] linha = { numero, aldeoes };
		this.tmFazendas.addRow(linha);
		this.cbFazenda.addItem(numero);
	}

	public void mostrarFazenda(int fazenda, String aldeoes) {
		this.tblFazendas.setValueAt(aldeoes, fazenda - 1, 1);
	}

	public void mostrarComida(int qtd) {
		this.lblComida.setText(NumberFormat.getNumberInstance().format(qtd));
	}

	public void adicionarMinaOuro(String numero, String aldeoes) {
		String[] linha = { numero, aldeoes };
		this.tmMinasOuro.addRow(linha);
		this.cbMinaOuro.addItem(numero);
	}

	public void mostrarMinaOuro(int minaOuro, String aldeoes) {
		this.tblMinasOuro.setValueAt(aldeoes, minaOuro - 1, 1);
	}

	public void mostrarOuro(int qtd) {
		this.lblOuro.setText(NumberFormat.getNumberInstance().format(qtd));
	}

	public void mostrarOferendaFe(int qtd) {
		this.lblOferenda.setText(NumberFormat.getNumberInstance().format(qtd));
	}

	public void mostrarPrefeitura(String acao, Color cor) {
		this.tfPrefeitura.setText(acao);
		this.tfPrefeitura.setBackground(cor);
	}

	public void habilitarTemplo() {
		this.pnTemplo.setEnabled(true);
		this.pnOferenda.setEnabled(true);
		this.lblOferenda.setEnabled(true);
		this.cbTEmploEvolucoes.setEnabled(true);
		this.btnTemploEvoluir.setEnabled(true);
	}

	public void habilitarMaravilha() {
		this.pnMaravilha.setEnabled(true);
		this.lblMaravilha.setEnabled(true);
		this.pbMaravilha.setEnabled(true);
	}

	public void mostrarMaravilha(int tijolos) {
		this.pbMaravilha.setValue(tijolos);
	}

	public void mostrarAtaques(List<String> evolucoes) {
		this.cbTemploLancamentos.setEnabled(true);
		this.cbTemploInimigo.setEnabled(true);
		this.btnTemploLancar.setEnabled(true);
		this.cbTemploLancamentos.removeAllItems();
		for (String evolucao : evolucoes) {
			switch (evolucao) {
			case "NUVEM_GAFANHOTOS":
				this.cbTemploLancamentos.addItem("Nuvem de gafanhotos");
				break;
			case "MORTE_PRIMOGENITOS":
				this.cbTemploLancamentos.addItem("Morte dos primogênitos");
				break;
			case "CHUVA_PEDRAS":
				this.cbTemploLancamentos.addItem("Chuva de pedras");
			}
		}
	}

	public void limparInimigos() {
		this.cbTemploInimigo.removeAllItems();
	}

	public void adicionarInimigo(String strInimigo) {
		this.cbTemploInimigo.addItem(strInimigo);
	}

	public void mostrarTemplo(String acao, Color cor) {
		this.tfTemplo.setText(acao);
		this.tfTemplo.setBackground(cor);
	}
	// *** Saída=Ações/comandos - informa ação do usuário *********************
	// *** Inicio ************************************************************
	// ----------------------------------------------------------------------------

	private void comandoCriarJogo(String nome, String civilizacao) {
		boolean retorno;
		if (nome.length() < 2) {
			mostrarMensagemErro("Erro", "Informe um nome para o jogador");
			return;
		}
		retorno = true; // retorno da criação do jogo
		if (retorno) {
			// Criamos o servidor
			// Conectamos ao servidor

			subirServidor = new Subir_Servidor(this);
			subirServidor.start();
			cliente = new ClienteTCP(this);
			cliente.conectar("0.0.0.0", nome, civilizacao, "Aguardando...", prefeitura);
			this.situacaoInicio = SituacaoInicio.CRIAR_CRIADO;
			this.habilitarInicio();
		}
	}

	private void comandoDestruirJogo() {

		this.cliente.desconectar();
		this.subirServidor.encerrar_servidor();

		this.limparJogadores();
		this.limparMensagens();
		this.situacaoInicio = SituacaoInicio.INICIAL_CRIAR;
		this.habilitarInicio();
	}

	private void comandoIniciarJogo() {
		boolean retorno = true; // retorno da iniciação do jogo
		if (retorno) {
			this.base();
			this.situacaoInicio = SituacaoInicio.CRIAR_INICIADO;
			cliente.iniciarJogo();
			this.habilitarInicio();
			this.tpJogo.setSelectedIndex(1);
		}
	}

	private void comandoEncerrarJogo() {
		cliente.desconectar();
		subirServidor.encerrar_servidor();
		this.limparJogadores();
		this.limparMensagens();
		this.situacaoInicio = SituacaoInicio.INICIAL_CRIAR;
		this.habilitarInicio();
	}

	private void comandoConectar(String nome, String civilizacao, String ipServidor) {
		boolean retorno;
		if (nome.length() < 2) {
			mostrarMensagemErro("Erro", "Informe um nome para o jogador");
			return;
		}
		if (ipServidor.length() < 5) {
			mostrarMensagemErro("Erro", "Informe o IP do computador que criou o jogo");
			return;
		}
		retorno = true; // retorno da conexão
		if (retorno) {
			this.base();
			cliente = new ClienteTCP(this);
			cliente.conectar(ipServidor, nome, civilizacao, "Conectado!", prefeitura);
			this.situacaoInicio = SituacaoInicio.CONECTADO;
			this.habilitarInicio();

		}
	}

	private void comandoDesconectar() {
		this.cliente.desconectar();
		this.limparJogadores();
		this.limparMensagens();
		this.situacaoInicio = SituacaoInicio.INICIAL_CONECTAR;
		this.habilitarInicio();
	}

	public void comandoEnviarMensagem(String mensagem) {
		cliente.enviarMensagem(mensagem);
		tfMensagem.setText("");
		tfMensagem.requestFocus();

	}

	// *** Saída=Ações/comandos - informa ação do usuário *********************
	// *** Vila
	// ****************************************************************--------------------------------

	public void comandoAldeaoParar(int aldeao) {
		if (aldeao == -1)
			mostrarMensagemErro("Erro", "Escolha um aldeão");
		else {
			this.prefeitura.aldeoes.get(aldeao).fazer(AcaoAldeao.PARADO);

		}

	}

	public void comandoAldeaoConstruir(int aldeao, String qual) {
		if (aldeao == -1)
			mostrarMensagemErro("Erro", "Escolha um aldeão");
		else
			switch (qual) {
			case "Fazenda":
				// FAZENDA

				this.prefeitura.aldeoes.get(aldeao).fazer(AcaoAldeao.CONSTRUINDO_FAZENDA);

				break;
			case "Mina de ouro":
				// MINA DE OURO

				this.prefeitura.aldeoes.get(aldeao).fazer(AcaoAldeao.CONSTRUINDO_MINA);

				break;
			case "Templo":
				// TEMPLO

				this.prefeitura.aldeoes.get(aldeao).fazer(AcaoAldeao.CONSTRUINDO_TEMPLO);

				break;
			case "Maravilha":
				// MARAVILHA

				this.prefeitura.aldeoes.get(aldeao).fazer(AcaoAldeao.CONSTRUINDO_MARAVILHA);

				break;
			default:
				break;
			}

	}

	public void comandoAldeaoCultivar(int aldeao, int numeroFazenda) {
		if (aldeao == -1)
			mostrarMensagemErro("Erro", "Escolha um aldeão");
		else
			prefeitura.aldeoes.get(aldeao).fazenda = prefeitura.fazendas.get(numeroFazenda);
		prefeitura.aldeoes.get(aldeao).fazer(AcaoAldeao.CULTIVANDO);
	}

	public void comandoAldeaoMinerar(int aldeao, int numeroMinaOuro) {
		if (aldeao == -1)
			mostrarMensagemErro("Erro", "Escolha um aldeão");
		else
			prefeitura.aldeoes.get(aldeao).mina = prefeitura.minas.get(numeroMinaOuro);
		prefeitura.aldeoes.get(aldeao).fazer(AcaoAldeao.MINERANDO);
	}

	public void comandoAldeaoOrar(int aldeao) {
		if (aldeao == -1)
			mostrarMensagemErro("Erro", "Escolha um aldeão");
		else
			prefeitura.aldeoes.get(aldeao).fazer(AcaoAldeao.ORANDO);
	}

	public void comandoAldeaoSacrificar(int aldeao) {
		if (aldeao == -1)
			mostrarMensagemErro("Erro", "Escolha um aldeão");
		else
			prefeitura.aldeoes.get(aldeao).fazer(AcaoAldeao.SACRIFICANDO);
	}

	// Comandos prefeitura

	public void comandoPrefeituraCriarAldeao() {
		this.prefeitura.fazer(AcaoPrefeitura.CRIAR_ALDEAO);
	}

	public void comandoPrefeituraEvoluir(String strEvolucao) {
		switch (strEvolucao) {
		case "Evolução de aldeão":
			this.prefeitura.fazer(AcaoPrefeitura.EVOLUIR_ALDEAO);
			break;
		case "Evolução de fazenda":
			this.prefeitura.fazer(AcaoPrefeitura.EVOLUIR_FAZENDA);
			break;
		case "Evolução de mina de ouro":
			this.prefeitura.fazer(AcaoPrefeitura.EVOLUIR_MINA_OURO);
			break;
		default:
			break;
		}

	}

	public void comandoTemploEvoluir(String strEvolucao) {
		if (this.prefeitura.vila.contTemplo != 0) {
			switch (strEvolucao) {
			case "Nuvem de gafanhotos":
				this.prefeitura.vila.templo.fazer(AcaoTemplo.EVOLUCAO_NUVEM_GARFANHOTO);
				break;
			case "Morte dos primogênitos":
				this.prefeitura.vila.templo.fazer(AcaoTemplo.EVOLUCAO_MORTE_PRIMOGENITO);
				break;
			case "Chuva de pedras":
				this.prefeitura.vila.templo.fazer(AcaoTemplo.EVOLUCAO_CHUVA_PEDRA);
				break;
			case "Proteção contra nuvem de gafanhotos":
				this.prefeitura.vila.templo.fazer(AcaoTemplo.EVOLUCAO_PROTECAO_NUVEM_GARFANHOTO);
				this.evolucoes.add("NUVEM_GAFANHOTOS");

				break;
			case "Proteção contra morte dos primogênitos":
				this.prefeitura.vila.templo.fazer(AcaoTemplo.EVOLUCAO_PROTECAO_MORTE_PRIMOGENITO);
				this.evolucoes.add("NUVEM_GAFANHOTOS");

				break;
			case "Proteção contra chuva de pedras":
				this.prefeitura.vila.templo.fazer(AcaoTemplo.EVOLUCAO_PROTECAO_CHUVA_PEDRA);
				this.evolucoes.add("NUVEM_GAFANHOTOS");

				break;
			}
		} else {
			this.mostrarMensagemErro("ERRO - EVOLUCAO", "Crie um templo para evoluir: " + strEvolucao);
		}
	}

	public void comandoTemploLancar(String strPraga, String strInimigo) {
		this.cliente.lancar(strPraga, strInimigo);
	}

	public void ganharJogo() {
		this.cliente.ganharJogo();
	}

}