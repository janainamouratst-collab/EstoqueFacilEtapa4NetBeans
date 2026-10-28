CREATE DATABASE IF NOT EXISTS estoque_facil;
USE estoque_facil;

DROP TABLE IF EXISTS movimentacoes_estoque;
DROP TABLE IF EXISTS produtos;
DROP TABLE IF EXISTS fornecedores;
DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    perfil VARCHAR(50) NOT NULL
);

CREATE TABLE fornecedores (
    id_fornecedor INT AUTO_INCREMENT PRIMARY KEY,
    nome_fornecedor VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    telefone VARCHAR(20)
);

CREATE TABLE produtos (
    id_produto INT AUTO_INCREMENT PRIMARY KEY,
    nome_produto VARCHAR(100) NOT NULL,
    codigo_barras VARCHAR(50),
    preco_custo DECIMAL(10,2),
    preco_venda DECIMAL(10,2),
    quantidade_estoque INT,
    data_validade DATE,
    id_fornecedor INT,
    FOREIGN KEY (id_fornecedor) REFERENCES fornecedores(id_fornecedor)
);

CREATE TABLE movimentacoes_estoque (
    id_movimentacao INT AUTO_INCREMENT PRIMARY KEY,
    tipo_movimentacao VARCHAR(20) NOT NULL,
    quantidade INT NOT NULL,
    data_movimentacao DATE NOT NULL,
    id_produto INT NOT NULL,
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_produto) REFERENCES produtos(id_produto),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

INSERT INTO usuarios (nome, email, senha, perfil) VALUES
('Carlos Silva', 'carlos@empresa.com', '123', 'Administrador'),
('Mariana Souza', 'mariana@empresa.com', '123', 'Operador');

INSERT INTO fornecedores (nome_fornecedor, cidade, telefone) VALUES
('Metal Forte', 'São Paulo', '(11) 99999-1111'),
('Parafusos Brasil', 'Campinas', '(19) 98888-2222');

INSERT INTO produtos (nome_produto, codigo_barras, preco_custo, preco_venda, quantidade_estoque, data_validade, id_fornecedor) VALUES
('Parafuso Sextavado', '111111111', 10.00, 15.00, 8, DATE_ADD(CURDATE(), INTERVAL 15 DAY), 1),
('Porca Rosca Fina', '222222222', 5.00, 8.00, 20, DATE_ADD(CURDATE(), INTERVAL 40 DAY), 1),
('Arruela Lisa', '333333333', 2.00, 4.00, 6, DATE_ADD(CURDATE(), INTERVAL 5 DAY), 2),
('Chapa de Aço', '444444444', 50.00, 75.00, 30, DATE_ADD(CURDATE(), INTERVAL 90 DAY), 2);
