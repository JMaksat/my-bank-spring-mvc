create database bankdb;
create user scott with password 'tiger' superuser;
create schema bank;

CREATE TABLE bank.users
(
  username character varying(45) NOT NULL,
  password character varying(45) NOT NULL,
  is_active integer,
  CONSTRAINT users_pk PRIMARY KEY (username)
);

CREATE TABLE bank.user_roles
(
  user_role_id serial NOT NULL,
  username character varying(45) NOT NULL,
  role character varying(45) NOT NULL,
  CONSTRAINT user_roles_pkey PRIMARY KEY (user_role_id),
  CONSTRAINT fk_username FOREIGN KEY (username)
      REFERENCES bank.users (username) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uni_username_role UNIQUE (role, username)
);

CREATE TABLE bank.customer_info
(
  customer_id serial NOT NULL,
  first_name character varying(64),
  last_name character varying(64),
  middle_name character varying(64),
  birth_date date,
  date_modified date,
  is_active integer,
  user_id character varying(32),
  date_created date,
  CONSTRAINT customer_info_pk PRIMARY KEY (customer_id)
);

/* - Bank account structure: BBB DDMMYYYY CCCCC TTT (e.g. 0032812201500152056). BBB - branch, DDMMYYYY - open date, CCCCC - counter which start from 0 every business day, TTT - type of the account (buffer, personal, interest, system) */
CREATE TABLE bank.accounts
(
  account_id serial NOT NULL,
  account_number character varying(32),
  account_owner integer,
  date_opened date,
  date_closed date,
  date_created date,
  date_modified date,
  user_id character varying(32),
  account_type integer,
  is_suspended integer,
  comment character varying(4000),
  CONSTRAINT accounts_pk PRIMARY KEY (account_id),
  CONSTRAINT account_owner_fk FOREIGN KEY (account_owner)
      REFERENCES bank.customer_info (customer_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
);

/*
- Domestic transactions. Domestic transaction can be fulfilled from one account (dropdown-list) to second.
- Transactions between banks. If transaction is incoming, then first account should be foreign and second is domestic. If transaction is outgoing, then the first account is domestic and second is foreign.
- Transactions between banks should use special one buffer account.
- Transaction contains several types of operations: Money transfer (incoming, outgoing, between domestic accounts...always from right to left), Refill, Withdrawal
*/
CREATE TABLE bank.transactions
(
  transaction_id serial NOT NULL,
  operation_type integer,
  is_reversed integer,
  transaction_sum real,
  transaction_date date,
  transaction_time time without time zone,
  user_id character varying(32),
  account_debit integer,
  account_credit integer,
  CONSTRAINT transactions_pk PRIMARY KEY (transaction_id),
  CONSTRAINT account_debit_fk FOREIGN KEY (account_debit)
      REFERENCES bank.accounts (account_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE bank.account_rest
(
  rest_id serial NOT NULL,
  account_id integer,
  rest_sum real,
  transaction_id integer,
  rest_date date,
  rest_time time without time zone,
  CONSTRAINT account_rest_pk PRIMARY KEY (rest_id),
  CONSTRAINT account_rest_fk FOREIGN KEY (account_id)
      REFERENCES bank.accounts (account_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT rest_transaction_fk FOREIGN KEY (transaction_id)
      REFERENCES bank.transactions (transaction_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE bank.customer_papers
(
  paper_id serial NOT NULL,
  value character varying(1024),
  date_created date,
  date_modified date,
  is_active integer,
  user_id character varying(32),
  paper_type integer,
  customer_id integer,
  CONSTRAINT customer_papers_pk PRIMARY KEY (paper_id),
  CONSTRAINT customer_papers_fk FOREIGN KEY (customer_id)
      REFERENCES bank.customer_info (customer_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE bank.customer_address
(
  address_id serial NOT NULL,
  value character varying(1024),
  date_created date,
  date_modified date,
  is_active integer,
  user_id character varying(32),
  address_type integer,
  customer_id integer,
  CONSTRAINT customer_address_pk PRIMARY KEY (address_id),
  CONSTRAINT customer_address_fk FOREIGN KEY (customer_id)
      REFERENCES bank.customer_info (customer_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE bank.customer_contacts
(
  contact_id serial NOT NULL,
  value character varying(1024),
  date_created date,
  date_modified date,
  is_active integer,
  user_id character varying(32),
  contact_type integer,
  customer_id integer,
  CONSTRAINT customer_contacts_pk PRIMARY KEY (contact_id),
  CONSTRAINT customer_contacts_fk FOREIGN KEY (customer_id)
      REFERENCES bank.customer_info (customer_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE bank.directory
(
  dir_id serial NOT NULL,
  dir_group character varying(64),
  dir_type character varying(64),
  description character varying(1024),
  date_created date,
  date_modified date,
  is_active integer,
  user_id character varying(32),
  CONSTRAINT directory_pk PRIMARY KEY (dir_id)
);