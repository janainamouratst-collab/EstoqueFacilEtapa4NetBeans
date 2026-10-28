package estoquefacil;

public class Produto {
    private int id;
    private String nome;
    private String codigoBarras;
    private double precoCusto;
    private double precoVenda;
    private int quantidade;
    private String validade;

    public Produto() {
    }

    public Produto(int id, String nome, String codigoBarras, double precoCusto, double precoVenda, int quantidade, String validade) {
        this.id = id;
        this.nome = nome;
        this.codigoBarras = codigoBarras;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.quantidade = quantidade;
        this.validade = validade;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }
    public double getPrecoCusto() { return precoCusto; }
    public void setPrecoCusto(double precoCusto) { this.precoCusto = precoCusto; }
    public double getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(double precoVenda) { this.precoVenda = precoVenda; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public String getValidade() { return validade; }
    public void setValidade(String validade) { this.validade = validade; }
}
