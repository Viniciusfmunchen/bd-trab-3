DROP TABLE IF EXISTS "infracao" CASCADE;
DROP TABLE IF EXISTS "local_infracao" CASCADE;
DROP TABLE IF EXISTS "via_transito" CASCADE;
DROP TABLE IF EXISTS "tipo_via" CASCADE;
DROP TABLE IF EXISTS "jurisdicao_via" CASCADE;
DROP TABLE IF EXISTS "status_infracao" CASCADE;
DROP TABLE IF EXISTS "tipo_infracao" CASCADE;
DROP TABLE IF EXISTS "gravidade_infracao" CASCADE;
DROP TABLE IF EXISTS "historico_ipva" CASCADE;
DROP TABLE IF EXISTS "status_ipva" CASCADE;
DROP TABLE IF EXISTS "email" CASCADE;
DROP TABLE IF EXISTS "telefone" CASCADE;
DROP TABLE IF EXISTS "ddi" CASCADE;
DROP TABLE IF EXISTS "ddd" CASCADE;
DROP TABLE IF EXISTS "cnh_categoria" CASCADE;
DROP TABLE IF EXISTS "cnh" CASCADE;
DROP TABLE IF EXISTS "categoria_cnh" CASCADE;
DROP TABLE IF EXISTS "veiculo" CASCADE;
DROP TABLE IF EXISTS "pessoa" CASCADE;
DROP TABLE IF EXISTS "tipo_pessoa" CASCADE;
DROP TABLE IF EXISTS "endereco" CASCADE;
DROP TABLE IF EXISTS "logradouro" CASCADE;
DROP TABLE IF EXISTS "tipo_logradouro" CASCADE;
DROP TABLE IF EXISTS "bairro" CASCADE;
DROP TABLE IF EXISTS "cidade" CASCADE;
DROP TABLE IF EXISTS "uf" CASCADE;
DROP TABLE IF EXISTS "modelo_veiculo" CASCADE;
DROP TABLE IF EXISTS "tipo_veiculo" CASCADE;
DROP TABLE IF EXISTS "marca_veiculo" CASCADE;
DROP TABLE IF EXISTS "cor" CASCADE;
TRUNCATE TABLE
    infracao,
    local_infracao,
    via_transito,
    tipo_via,
    jurisdicao_via,
    status_infracao,
    tipo_infracao,
    gravidade_infracao,
    historico_ipva,
    status_ipva,
    email,
    telefone,
    ddi,
    ddd,
    cnh_categoria,
    cnh,
    categoria_cnh,
    veiculo,
    pessoa,
    tipo_pessoa,
    endereco,
    logradouro,
    tipo_logradouro,
    bairro,
    cidade,
    uf,
    modelo_veiculo,
    tipo_veiculo,
    marca_veiculo,
    cor
RESTART IDENTITY CASCADE;