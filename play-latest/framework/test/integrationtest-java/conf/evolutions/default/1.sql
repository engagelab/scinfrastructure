# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table hello_world (
  id                        bigint not null,
  field1                    varchar(255),
  field2                    varchar(255),
  constraint pk_hello_world primary key (id))
;

create sequence hello_world_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists hello_world;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists hello_world_seq;

