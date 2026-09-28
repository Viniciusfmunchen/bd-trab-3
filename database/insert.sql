INSERT INTO cor (id_cor, nome_cor) VALUES 
(1, 'Prata'), (2, 'Preto'), (3, 'Branco');

INSERT INTO marca_veiculo (id_marca_veiculo, nome_marca_veiculo) VALUES 
(1, 'Chevrolet'), (2, 'Toyota'), (3, 'Volkswagen');

INSERT INTO tipo_veiculo (id_tipo_veiculo, nome_tipo_veiculo) VALUES 
(1, 'Automóvel'), (2, 'Caminonete');

INSERT INTO modelo_veiculo (id_modelo_veiculo, id_marca_veiculo, id_tipo_veiculo, nome_modelo_veiculo) VALUES 
(1, 1, 1, 'Onix 1.0'), 
(2, 2, 2, 'Corolla Cross'), 
(3, 3, 1, 'Gol 1.6');

INSERT INTO uf (id_uf, nome_uf, sigla_uf) VALUES 
(1, 'Paraná', 'PR');

INSERT INTO cidade (id_cidade, id_uf, nome_cidade, sigla_cidade) VALUES 
(1, 1, 'Foz do Iguaçu', 'FOZ'), 
(2, 1, 'Santa Terezinha de Itaipu', 'STI');

INSERT INTO bairro (id_bairro, nome_bairro) VALUES 
(1, 'Centro'), (2, 'Vila A');

INSERT INTO tipo_logradouro (id_tipo_logradouro, nome_tipo_logradouro, sigla_tipo_logradouro) VALUES 
(1, 'Avenida', 'AV'), (2, 'Rua', 'R');

INSERT INTO logradouro (id_logradouro, id_tipo_logradouro, nome_logradouro) VALUES 
(1, 1, 'Brasil'), (2, 2, 'Juscelino Kubitschek');

INSERT INTO tipo_pessoa (id_tipo_pessoa, nome_tipo_pessoa) VALUES 
(1, 'PF'), (2, 'PJ');

INSERT INTO categoria_cnh (id_categoria_cnh, nome_categoria_cnh) VALUES 
(1, 'B'), (2, 'AB');

INSERT INTO status_ipva (id_status_ipva, nome_status_ipva) VALUES 
(1, 'Quitado'), (2, 'Em Aberto');

INSERT INTO gravidade_infracao (id_gravidade, nome_gravidade) VALUES 
(1, 'Leve'), (2, 'Média'), (3, 'Grave'), (4, 'Gravíssima');

INSERT INTO tipo_infracao (id_tipo_infracao, codigo_ctb, descricao, id_gravidade, pontos, valor_base) VALUES 
(1, '7455-0', 'Excesso de velocidade até 20%', 2, 4, 130.16),
(2, '5185-1', 'Deixar de usar o cinto de segurança', 3, 5, 195.23);

INSERT INTO status_infracao (id_status_infracao, nome_status_infracao) VALUES 
(1, 'Paga'), (2, 'Pendente');

INSERT INTO jurisdicao_via (id_jurisdicao_via, nome_jurisdicao_via) VALUES 
(1, 'Municipal'), (2, 'Federal');

INSERT INTO tipo_via (id_tipo_via, nome_tipo_via) VALUES 
(1, 'Avenida'), (2, 'Rodovia');

INSERT INTO via_transito (id_via, id_tipo_via, nome_oficial, id_jurisdicao_via) VALUES 
(1, 1, 'Avenida Brasil', 1),
(2, 2, 'BR-277', 2);

INSERT INTO endereco (id_endereco, id_cidade, id_bairro, id_logradouro, cep) VALUES 
(1, 1, 1, 1, '85851000'),
(2, 2, 2, 2, '85875000');

INSERT INTO pessoa (id_pessoa, id_tipo_pessoa, id_endereco, numero_endereco, complemento_endereco, documento, nome) VALUES 
(1, 1, 1, 100, 'Apto 201', '12345678901', 'Vinicius Fontana'),
(2, 1, 2, 450, NULL, '98765432109', 'Carlos Eduardo'),
(3, 2, 1, 1500, 'Sala 01', '12345678000199', 'Locadora de Veiculos Foz Ltda');

INSERT INTO cnh (
    num_registro,
    id_pessoa,
    data_validade,
    pontuacao_atual
) VALUES 
('11111111111', 1, '2028-05-10', 4),
('22222222222', 2, '2029-11-20', 0);

INSERT INTO cnh_categoria (
    num_registro,
    id_categoria_cnh
) VALUES 
('11111111111', 1),
('22222222222', 2);

INSERT INTO ddd (codigo_ddd) VALUES ('45');
INSERT INTO ddi (codigo_ddi) VALUES ('+55');

INSERT INTO telefone (id_telefone, id_pessoa, codigo_ddd, codigo_ddi, numero_telefone) VALUES 
(1, 1, '45', '+55', '999991111'),
(2, 2, '45', '+55', '988882222');

INSERT INTO email (id_email, id_pessoa, endereco_email) VALUES 
(1, 1, 'vinicius@email.com'),
(2, 2, 'carlos@email.com');

INSERT INTO veiculo (id_veiculo, placa, cod_renavam, num_chassi, ano_fabricacao, id_cor, id_modelo_veiculo, id_proprietario, id_motorista_principal) VALUES 
(1, 'ABC1D23', '11111111111', '9BWZZZ377VT000001', 2020, 1, 1, 1, 1),
(2, 'XYZ9W87', '22222222222', '9BWZZZ377VT000002', 2022, 2, 2, 2, 2),
(3, 'BRA2E19', '33333333333', '9BWZZZ377VT000003', 2023, 3, 3, 3, 1);

INSERT INTO historico_ipva (id_ipva, id_veiculo, ano_exercicio, valor_base, valor_pago, data_pagamento, data_vencimento, id_status_ipva) VALUES 
(1, 1, 2023, 1200.00, 1200.00, '2023-03-10', '2023-03-15', 1),
(2, 1, 2024, 1300.00, 1300.00, '2024-03-12', '2024-03-15', 1),
(3, 1, 2025, 1350.00, 1350.00, '2025-03-10', '2025-03-15', 1),
(4, 1, 2026, 1400.00, 1400.00, '2026-03-05', '2026-03-15', 1),

(5, 2, 2023, 3500.00, 3500.00, '2023-04-02', '2023-04-10', 1),
(6, 2, 2024, 3800.00, 3800.00, '2024-04-05', '2024-04-10', 1),
(7, 2, 2025, 4000.00, 4000.00, '2025-04-08', '2025-04-10', 1),
(8, 2, 2026, 4200.00, 4200.00, '2026-04-01', '2026-04-10', 1),

(9, 3, 2023, 900.00, 900.00, '2023-02-20', '2023-02-28', 1),
(10, 3, 2024, 950.00, 950.00, '2024-02-22', '2024-02-28', 1),
(11, 3, 2025, 1000.00, 1000.00, '2025-02-25', '2025-02-28', 1),
(12, 3, 2026, 1050.00, 1050.00, '2026-02-10', '2026-02-28', 1);

INSERT INTO local_infracao (id_local_infracao, id_via, id_cidade, latitude, longitude, referencia) VALUES 
(1, 1, 1, -25.5159, -54.5854, 'Em frente ao nº 1500'),
(2, 2, 2, -25.5081, -54.3051, 'Km 714');

INSERT INTO infracao (id_infracao, id_veiculo, id_status_infracao, id_tipo_infracao, id_condutor_infrator, id_local_infracao, data_hora_infracao, data_vencimento, data_pagamento) VALUES 
(1, 1, 1, 1, 1, 1, '2026-05-10 14:30:00', '2026-06-10', '2026-05-20'),
(2, 2, 2, 2, 2, 2, '2026-06-01 09:15:00', '2026-07-01', NULL);