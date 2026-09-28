-- View 1: Histórico de IPVA por Veículo
CREATE OR REPLACE VIEW vw_historico_ipva AS
SELECT 
    v.placa, 
    p.nome AS proprietario, 
    mv.nome_modelo_veiculo AS modelo,
    hi.ano_exercicio, 
    hi.valor_pago, 
    hi.data_pagamento, 
    si.nome_status_ipva AS status
FROM veiculo v
JOIN pessoa p ON v.id_proprietario = p.id_pessoa
JOIN modelo_veiculo mv ON v.id_modelo_veiculo = mv.id_modelo_veiculo
JOIN historico_ipva hi ON v.id_veiculo = hi.id_veiculo
JOIN status_ipva si ON hi.id_status_ipva = si.id_status_ipva
ORDER BY v.placa, hi.ano_exercicio DESC;

-- View 2: Histórico de Infrações por Veículo
CREATE OR REPLACE VIEW vw_historico_infracoes AS
SELECT 
    v.placa, 
    COALESCE(p.nome, 'Não identificado') AS condutor_infrator, 
    ti.codigo_ctb,
    ti.descricao AS infracao, 
    ti.pontos, 
    ti.valor_base AS valor_multa,
    i.data_hora_infracao, 
    si.nome_status_infracao AS status_multa
FROM infracao i
JOIN veiculo v ON i.id_veiculo = v.id_veiculo
LEFT JOIN pessoa p ON i.id_condutor_infrator = p.id_pessoa
JOIN tipo_infracao ti ON i.id_tipo_infracao = ti.id_tipo_infracao
JOIN status_infracao si ON i.id_status_infracao = si.id_status_infracao
ORDER BY i.data_hora_infracao DESC;