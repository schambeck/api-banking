create table "movimentacao"
(
   id serial,
   conta_id integer not null references "conta" (id),
   data date not null,
   tipo char(10) not null,
   valor decimal(12,2) not null,
   saldo_anterior decimal(12,2) not null,
   version integer not null,
   created_at timestamp,
   modified_at timestamp,
   primary key (id)
);

insert into movimentacao (conta_id, data, tipo, valor, saldo_anterior, version)
values (1, '2023-11-01', 'CREDITO', 111, 0, 0);
insert into movimentacao (conta_id, data, tipo, valor, saldo_anterior, version)
values (1, '2023-11-02', 'CREDITO', 900, 111, 0);
insert into movimentacao (conta_id, data, tipo, valor, saldo_anterior, version)
values (1, '2023-11-02', 'CREDITO', 100, 1011, 0);
insert into movimentacao (conta_id, data, tipo, valor, saldo_anterior, version)
values (2, '2023-11-01', 'CREDITO', 2222, 0, 0);
