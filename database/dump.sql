create table url (id bigint not null, created_at datetime not null, host varchar(255) not null, identifier varchar(255) not null, last_access datetime, port integer, protocol varchar(255) not null, total_access bigint, url varchar(255) not null, primary key (id)) engine=MyISAM
alter table url add constraint UK_URL_IDENTIFIER unique (identifier)
