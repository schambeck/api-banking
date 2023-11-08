create table "saldo"
(
   id serial,
   conta_id integer not null references "conta" (id),
   valor decimal(12,2) not null,
   version integer not null,
   created_at timestamp,
   modified_at timestamp,
   primary key (id)
);

create unique index "idx_saldo_conta" on "saldo" (conta_id);

insert into saldo (conta_id, valor, version)
values (3, 0, 0);
