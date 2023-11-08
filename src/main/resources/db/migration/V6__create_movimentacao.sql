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
values (1, current_date, 'DEBITO', 123, 0, 0);
insert into movimentacao (conta_id, data, tipo, valor, saldo_anterior, version)
values (1, current_date, 'DEBITO', 321, 123, 0);
