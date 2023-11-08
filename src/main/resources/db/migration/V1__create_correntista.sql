create table "correntista"
(
   id serial,
   nome varchar(256) not null,
   email varchar(256) not null,
   cpf char(11) not null,
   version integer not null,
   created_at timestamp,
   modified_at timestamp,
   primary key (id)
);

create unique index "idx_correntista_email" on "correntista" (email);
create unique index "idx_correntista_cpf" on "correntista" (cpf);

insert into correntista (id, nome, email, cpf, version)
values (nextval('correntista_id_seq'), 'Scott Anton', 'scottanton@gmail.com', '73190252050', 0);
insert into correntista (id, nome, email, cpf, version)
values (nextval('correntista_id_seq'), 'Robert Davis', 'robertdavis@gmail.com', '35814875003', 0);
insert into correntista (id, nome, email, cpf, version)
values (nextval('correntista_id_seq'), 'Charles Benson', 'charlesbenson@gmail.com', '07095797056', 0);
