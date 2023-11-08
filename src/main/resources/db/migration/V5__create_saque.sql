create table "saque"
(
   id serial,
   saldo_id integer not null references "saldo" (id),
   data date not null,
   valor decimal(12,2) not null,
   version integer not null,
   created_at timestamp,
   modified_at timestamp,
   primary key (id)
);
