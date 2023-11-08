create table "conta"
(
   id serial,
   numero integer not null,
   agencia varchar(32) not null,
   correntista_id integer not null references "correntista" (id),
   version integer not null,
   created_at timestamp,
   modified_at timestamp,
   primary key (id)
);

create unique index "idx_conta_numero_agencia" on "conta" (numero, agencia);

insert into conta (id, numero, agencia, correntista_id, version)
values (nextval('conta_id_seq'), 1, '1111', 1, 0);
insert into conta (id, numero, agencia, correntista_id, version)
values (nextval('conta_id_seq'), 2, '2222', 2, 0);
insert into conta (id, numero, agencia, correntista_id, version)
values (nextval('conta_id_seq'), 3, '3333', 3, 0);
