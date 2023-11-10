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
values (1, 1111, 0);
insert into saldo (conta_id, valor, version)
values (2, 2222, 0);
insert into saldo (conta_id, valor, version)
values (3, 3333, 0);
