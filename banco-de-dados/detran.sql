CREATE TABLE "veiculo" (
  "id_veiculo" SERIAL PRIMARY KEY,
  "placa" varchar(7) UNIQUE NOT NULL,
  "cod_renavam" varchar(11) UNIQUE NOT NULL,
  "num_chassi" varchar(17) UNIQUE NOT NULL,
  "ano_fabricacao" integer NOT NULL,
  "id_cor" integer,
  "id_modelo_veiculo" integer,
  "id_proprietario" integer NOT NULL,
  "id_motorista_principal" integer
);

CREATE TABLE "cor" (
  "id_cor" integer PRIMARY KEY,
  "nome_cor" varchar(255)
);

CREATE TABLE "marca_veiculo" (
  "id_marca_veiculo" integer PRIMARY KEY,
  "nome_marca_veiculo" varchar(255)
);

CREATE TABLE "tipo_veiculo" (
  "id_tipo_veiculo" integer PRIMARY KEY,
  "nome_tipo_veiculo" varchar(255)
);

CREATE TABLE "modelo_veiculo" (
  "id_modelo_veiculo" integer PRIMARY KEY,
  "id_marca_veiculo" integer,
  "id_tipo_veiculo" integer,
  "nome_modelo_veiculo" varchar(255)
);

CREATE TABLE "uf" (
  "id_uf" integer PRIMARY KEY,
  "nome_uf" varchar(255) NOT NULL,
  "sigla_uf" varchar(2) NOT NULL
);

CREATE TABLE "cidade" (
  "id_cidade" integer PRIMARY KEY,
  "id_uf" integer NOT NULL,
  "nome_cidade" varchar(255) NOT NULL,
  "sigla_cidade" varchar(255) NOT NULL
);

CREATE TABLE "bairro" (
  "id_bairro" integer PRIMARY KEY,
  "nome_bairro" varchar(255)
);

CREATE TABLE "tipo_logradouro" (
  "id_tipo_logradouro" integer PRIMARY KEY,
  "nome_tipo_logradouro" varchar(255) NOT NULL,
  "sigla_tipo_logradouro" varchar(10) NOT NULL
);

CREATE TABLE "logradouro" (
  "id_logradouro" integer PRIMARY KEY,
  "id_tipo_logradouro" integer NOT NULL,
  "nome_logradouro" varchar(255) NOT NULL
);

CREATE TABLE "endereco" (
  "id_endereco" integer PRIMARY KEY,
  "id_cidade" integer NOT NULL,
  "id_bairro" integer NOT NULL,
  "id_logradouro" integer NOT NULL,
  "cep" varchar(8)
);

CREATE TABLE "tipo_pessoa" (
  "id_tipo_pessoa" integer PRIMARY KEY,
  "nome_tipo_pessoa" varchar(255)
);

CREATE TABLE "pessoa" (
  "id_pessoa" SERIAL PRIMARY KEY,
  "id_tipo_pessoa" integer NOT NULL,
  "id_endereco" integer NOT NULL,
  "numero_endereco" integer NOT NULL,
  "complemento_endereco" varchar(255),
  "documento" varchar(14) UNIQUE NOT NULL,
  "nome" varchar(255) NOT NULL
);

CREATE TABLE "cnh" (
  "num_registro" varchar(15) PRIMARY KEY,
  "id_pessoa" integer UNIQUE NOT NULL,
  "data_validade" date NOT NULL,
  "pontuacao_atual" integer DEFAULT 0
);

CREATE TABLE "categoria_cnh" (
  "id_categoria_cnh" integer PRIMARY KEY,
  "nome_categoria_cnh" varchar(2)
);

CREATE TABLE "cnh_categoria" (
  "num_registro" varchar(15) NOT NULL,
  "id_categoria_cnh" integer NOT NULL,
  PRIMARY KEY ("num_registro", "id_categoria_cnh")
);

CREATE TABLE "ddd" (
  "codigo_ddd" varchar(3) PRIMARY KEY
);

CREATE TABLE "ddi" (
  "codigo_ddi" varchar(5) PRIMARY KEY
);

CREATE TABLE "telefone" (
  "id_telefone" SERIAL PRIMARY KEY,
  "id_pessoa" integer NOT NULL,
  "codigo_ddd" varchar(3) NOT NULL,
  "codigo_ddi" varchar(5) NOT NULL,
  "numero_telefone" varchar(15) NOT NULL
);

CREATE TABLE "email" (
  "id_email" SERIAL PRIMARY KEY,
  "id_pessoa" integer NOT NULL,
  "endereco_email" varchar(255) NOT NULL
);

CREATE TABLE "status_ipva" (
  "id_status_ipva" integer PRIMARY KEY,
  "nome_status_ipva" varchar(255)
);

CREATE TABLE "historico_ipva" (
  "id_ipva" SERIAL PRIMARY KEY,
  "id_veiculo" integer NOT NULL,
  "ano_exercicio" integer NOT NULL,
  "valor_base" decimal NOT NULL,
  "valor_pago" decimal,
  "data_pagamento" date,
  "data_vencimento" date NOT NULL,
  "id_status_ipva" integer NOT NULL
);

CREATE TABLE "gravidade_infracao" (
  "id_gravidade" integer PRIMARY KEY,
  "nome_gravidade" varchar(50) NOT NULL
);

CREATE TABLE "tipo_infracao" (
  "id_tipo_infracao" integer PRIMARY KEY,
  "codigo_ctb" varchar(10) UNIQUE NOT NULL,
  "descricao" varchar(255) NOT NULL,
  "id_gravidade" integer NOT NULL,
  "pontos" integer NOT NULL,
  "valor_base" decimal NOT NULL
);

CREATE TABLE "status_infracao" (
  "id_status_infracao" integer PRIMARY KEY,
  "nome_status_infracao" varchar(255)
);

CREATE TABLE "jurisdicao_via" (
  "id_jurisdicao_via" integer PRIMARY KEY,
  "nome_jurisdicao_via" varchar(50)
);

CREATE TABLE "tipo_via" (
  "id_tipo_via" integer PRIMARY KEY,
  "nome_tipo_via" varchar(50) NOT NULL
);

CREATE TABLE "via_transito" (
  "id_via" SERIAL PRIMARY KEY,
  "id_tipo_via" integer NOT NULL,
  "nome_oficial" varchar(255) NOT NULL,
  "id_jurisdicao_via" integer NOT NULL
);

CREATE TABLE "local_infracao" (
  "id_local_infracao" SERIAL PRIMARY KEY,
  "id_via" integer NOT NULL,
  "id_cidade" integer NOT NULL,
  "latitude" decimal,
  "longitude" decimal,
  "referencia" varchar(255)
);

CREATE TABLE "infracao" (
  "id_infracao" SERIAL PRIMARY KEY,
  "id_veiculo" integer NOT NULL,
  "id_status_infracao" integer NOT NULL,
  "id_tipo_infracao" integer NOT NULL,
  "id_condutor_infrator" integer,
  "id_local_infracao" integer NOT NULL,
  "data_hora_infracao" timestamp NOT NULL,
  "data_vencimento" date NOT NULL,
  "data_pagamento" date
);

ALTER TABLE "veiculo" ADD FOREIGN KEY ("id_cor") REFERENCES "cor" ("id_cor");

ALTER TABLE "veiculo" ADD FOREIGN KEY ("id_modelo_veiculo") REFERENCES "modelo_veiculo" ("id_modelo_veiculo");

ALTER TABLE "modelo_veiculo" ADD FOREIGN KEY ("id_marca_veiculo") REFERENCES "marca_veiculo" ("id_marca_veiculo");

ALTER TABLE "modelo_veiculo" ADD FOREIGN KEY ("id_tipo_veiculo") REFERENCES "tipo_veiculo" ("id_tipo_veiculo");

ALTER TABLE "cidade" ADD FOREIGN KEY ("id_uf") REFERENCES "uf" ("id_uf");

ALTER TABLE "logradouro" ADD FOREIGN KEY ("id_tipo_logradouro") REFERENCES "tipo_logradouro" ("id_tipo_logradouro");

ALTER TABLE "endereco" ADD FOREIGN KEY ("id_cidade") REFERENCES "cidade" ("id_cidade");

ALTER TABLE "endereco" ADD FOREIGN KEY ("id_bairro") REFERENCES "bairro" ("id_bairro");

ALTER TABLE "endereco" ADD FOREIGN KEY ("id_logradouro") REFERENCES "logradouro" ("id_logradouro");

ALTER TABLE "pessoa" ADD FOREIGN KEY ("id_endereco") REFERENCES "endereco" ("id_endereco");

ALTER TABLE "pessoa" ADD FOREIGN KEY ("id_tipo_pessoa") REFERENCES "tipo_pessoa" ("id_tipo_pessoa");

ALTER TABLE "veiculo" ADD FOREIGN KEY ("id_proprietario") REFERENCES "pessoa" ("id_pessoa");

ALTER TABLE "veiculo" ADD FOREIGN KEY ("id_motorista_principal") REFERENCES "pessoa" ("id_pessoa");

ALTER TABLE "cnh" ADD FOREIGN KEY ("id_pessoa") REFERENCES "pessoa" ("id_pessoa");

ALTER TABLE "cnh_categoria" ADD FOREIGN KEY ("num_registro") REFERENCES "cnh" ("num_registro");

ALTER TABLE "cnh_categoria" ADD FOREIGN KEY ("id_categoria_cnh") REFERENCES "categoria_cnh" ("id_categoria_cnh");

ALTER TABLE "telefone" ADD FOREIGN KEY ("id_pessoa") REFERENCES "pessoa" ("id_pessoa");

ALTER TABLE "telefone" ADD FOREIGN KEY ("codigo_ddd") REFERENCES "ddd" ("codigo_ddd");

ALTER TABLE "telefone" ADD FOREIGN KEY ("codigo_ddi") REFERENCES "ddi" ("codigo_ddi");

ALTER TABLE "email" ADD FOREIGN KEY ("id_pessoa") REFERENCES "pessoa" ("id_pessoa");

ALTER TABLE "historico_ipva" ADD FOREIGN KEY ("id_veiculo") REFERENCES "veiculo" ("id_veiculo");

ALTER TABLE "historico_ipva" ADD FOREIGN KEY ("id_status_ipva") REFERENCES "status_ipva" ("id_status_ipva");

ALTER TABLE "via_transito" ADD FOREIGN KEY ("id_tipo_via") REFERENCES "tipo_via" ("id_tipo_via");

ALTER TABLE "via_transito" ADD FOREIGN KEY ("id_jurisdicao_via") REFERENCES "jurisdicao_via" ("id_jurisdicao_via");

ALTER TABLE "local_infracao" ADD FOREIGN KEY ("id_via") REFERENCES "via_transito" ("id_via");

ALTER TABLE "local_infracao" ADD FOREIGN KEY ("id_cidade") REFERENCES "cidade" ("id_cidade");

ALTER TABLE "infracao" ADD FOREIGN KEY ("id_local_infracao") REFERENCES "local_infracao" ("id_local_infracao");

ALTER TABLE "infracao" ADD FOREIGN KEY ("id_veiculo") REFERENCES "veiculo" ("id_veiculo");

ALTER TABLE "infracao" ADD FOREIGN KEY ("id_tipo_infracao") REFERENCES "tipo_infracao" ("id_tipo_infracao");

ALTER TABLE "infracao" ADD FOREIGN KEY ("id_condutor_infrator") REFERENCES "pessoa" ("id_pessoa");

ALTER TABLE "infracao" ADD FOREIGN KEY ("id_status_infracao") REFERENCES "status_infracao" ("id_status_infracao");

ALTER TABLE "tipo_infracao" ADD FOREIGN KEY ("id_gravidade") REFERENCES "gravidade_infracao" ("id_gravidade");

