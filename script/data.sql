insert into bank.directory (dir_id, dir_group, dir_type, description, date_created, date_modified, is_active, user_id)
values (4, 'CONTACTS', 'PHONE_NUMBER', 'Phone number', '2017-03-04', '2017-03-04', 1, 'scott');
insert into bank.directory (dir_id, dir_group, dir_type, description, date_created, date_modified, is_active, user_id)
values (5, 'CONTACTS', 'EMAIL', 'Email address', '2017-03-04', '2017-03-04', 1, 'scott');
insert into bank.directory (dir_id, dir_group, dir_type, description, date_created, date_modified, is_active, user_id)
values (6, 'CONTACTS', 'SKYPE', 'Skype name', '2017-03-04', '2017-03-04', 1, 'scott');
insert into bank.directory (dir_id, dir_group, dir_type, description, date_created, date_modified, is_active, user_id)
values (7, 'ADDRESS', 'ADDRESS_LINE1', 'First address line', '2017-03-04', '2017-03-04', 1, 'scott');
insert into bank.directory (dir_id, dir_group, dir_type, description, date_created, date_modified, is_active, user_id)
values (8, 'ADDRESS', 'ADDRESS_LINE2', 'Second address line', '2017-03-04', '2017-03-04', 1, 'scott');
insert into bank.directory (dir_id, dir_group, dir_type, description, date_created, date_modified, is_active, user_id)
values (9, 'OPERATIONS', 'INCOMING_TRANSFER', 'Transfer money from buffer account for incomings to personal account.', '2017-03-04', '2017-03-04', 1, 'scott');
insert into bank.directory (dir_id, dir_group, dir_type, description, date_created, date_modified, is_active, user_id)
values (10, 'OPERATIONS', 'OUTGOING_TRANSFER', 'Transfer money from personal account to buffer account for outgoings.', '2017-03-04', '2017-03-04', 1, 'scott');
insert into bank.directory (dir_id, dir_group, dir_type, description, date_created, date_modified, is_active, user_id)
values (11, 'OPERATIONS', 'DOMESTIC_TRANSFER', 'Transfer money personal to personal account. Always right to left.', '2017-03-04', '2017-03-04', 1, 'scott');
insert into bank.directory (dir_id, dir_group, dir_type, description, date_created, date_modified, is_active, user_id)
values (12, 'OPERATIONS', 'REFILL', 'Transfer money from buffer account for refills to personal account.', '2017-03-04', '2017-03-04', 1, 'scott');
insert into bank.directory (dir_id, dir_group, dir_type, description, date_created, date_modified, is_active, user_id)
values (13, 'OPERATIONS', 'WITHDRAWAL', 'Transfer money from personal account to buffer account for withdrawals.', '2017-03-04', '2017-03-04', 1, 'scott');
insert into bank.directory (dir_id, dir_group, dir_type, description, date_created, date_modified, is_active, user_id)
values (14, 'OPERATIONS', 'VOID', 'This operation doing nothing.', '2017-03-04', '2017-03-04', 1, 'scott');
insert into bank.directory (dir_id, dir_group, dir_type, description, date_created, date_modified, is_active, user_id)
values (15, 'ACCOUNTS', 'BUFFER', 'Buffer accounts for temporary keep money. Bank may contain more than one of buffer accounts.', '2017-03-04', '2017-03-04', 1, 'scott');
insert into bank.directory (dir_id, dir_group, dir_type, description, date_created, date_modified, is_active, user_id)
values (16, 'ACCOUNTS', 'PERSONAL', 'Personal accounts for clients.', '2017-03-04', '2017-03-04', 1, 'scott');
insert into bank.directory (dir_id, dir_group, dir_type, description, date_created, date_modified, is_active, user_id)
values (17, 'ACCOUNTS', 'INTEREST', 'Accounts can keep any kind of interest money. Bank may contain more than one of interest accounts.', '2017-03-04', '2017-03-04', 1, 'scott');
insert into bank.directory (dir_id, dir_group, dir_type, description, date_created, date_modified, is_active, user_id)
values (18, 'ACCOUNTS', 'SYSTEM', 'Whole balance of the bank. Bank must containt only one system account.', '2017-03-04', '2017-03-04', 1, 'scott');
insert into bank.directory (dir_id, dir_group, dir_type, description, date_created, date_modified, is_active, user_id)
values (19, 'PAPERS', 'PASSPORT', 'Passport', '2017-03-04', '2017-03-04', 1, 'scott');
insert into bank.directory (dir_id, dir_group, dir_type, description, date_created, date_modified, is_active, user_id)
values (20, 'PAPERS', 'IDENTITY_CARD', 'Identity document', '2017-03-04', '2017-03-04', 1, 'scott');
insert into bank.directory (dir_id, dir_group, dir_type, description, date_created, date_modified, is_active, user_id)
values (21, 'PAPERS', 'DRIVING_LICENCE', 'Driving licence', '2017-03-04', '2017-03-04', 1, 'scott');
alter sequence bank.directory_dir_id_seq restart with 22;


insert into bank.customer_info values (0, 'MyBank', 'Retail Bank', 'Branch 001', '2015-12-31', null, 1, 'scott', '2017-03-04');
insert into bank.customer_info values (1, 'Scott', 'Ridley', 'Alien', '1937-11-30', null, 1, 'scott', '2017-03-04');
insert into bank.customer_info values (2, 'Ted', 'Mosby', 'Josh', '1974-06-29', null, 1, 'scott', '2017-03-04');
insert into bank.customer_info values (3, 'Barney', 'Stinson', 'Patrick', '1973-06-15', null, 1, 'scott', '2017-03-04');
insert into bank.customer_info values (4, 'Marshall', 'Eriksen', 'Jason', '1980-01-18', '2017-03-04', 0, 'scott', '2017-03-04');
alter sequence bank.customer_info_customer_id_seq restart with 5;


insert into bank.customer_papers values (1, 'PSP#0012345', '2017-03-04', null, 1, 'scott', 19, 1);
insert into bank.customer_papers values (2, 'PSP#0123456', '2017-03-04', null, 1, 'scott', 19, 2);
insert into bank.customer_papers values (3, 'DRV#0123456', '2017-03-04', null, 1, 'scott', 21, 2);
insert into bank.customer_papers values (4, 'PSP#1234567', '2017-03-04', null, 1, 'scott', 19, 3);
insert into bank.customer_papers values (5, 'ID#0012345', '2017-03-04', '2017-03-04', 0, 'scott', 20, 4);
insert into bank.customer_papers values (6, 'DRV#0012345', '2017-03-04', '2017-03-04', 0, 'scott', 21, 4);
alter sequence bank.customer_papers_paper_id_seq restart with 7;


insert into bank.customer_address values (1, 'USA, CA, San Andreas, 123', '2017-03-04', null, 1, 'scott', 7, 1);
insert into bank.customer_address values (2, 'USA, NY, New York City, 123', '2017-03-04', null, 1, 'scott', 8, 1);
insert into bank.customer_address values (3, 'Brazil, Brasilia, Segunda Avenida 321', '2017-03-04', null, 1, 'scott', 7, 2);
insert into bank.customer_address values (4, 'UK, London, Winston Churchill Ave 194', '2017-03-04', null, 1, 'scott', 7, 3);
insert into bank.customer_address values (5, 'Russia, Moscow, Ulitsa Lenina 24 building 2', '2017-03-04', null, 1, 'scott', 7, 4);
alter sequence bank.customer_address_address_id_seq restart with 6;


insert into bank.customer_contacts values (1, '+17573453256', '2017-03-04', null, 1, 'scott', 4, 1);
insert into bank.customer_contacts values (2, 'scott@gmail.com', '2017-03-04', null, 1, 'scott', 5, 1);
insert into bank.customer_contacts values (3, '+55573453256', '2017-03-04', null, 1, 'scott', 4, 2);
insert into bank.customer_contacts values (4, 'ted@gmail.com', '2017-03-04', null, 1, 'scott', 5, 2);
insert into bank.customer_contacts values (5, '+44573453256', '2017-03-04', null, 1, 'scott', 4, 3);
insert into bank.customer_contacts values (6, 'barney@gmail.com', '2017-03-04', null, 1, 'scott', 5, 3);
insert into bank.customer_contacts values (7, '+77573453256', '2017-03-04', null, 1, 'scott', 4, 4);
insert into bank.customer_contacts values (8, 'marshall@gmail.com', '2017-03-04', null, 1, 'scott', 5, 4);
alter sequence bank.customer_contacts_contact_id_seq restart with 9;


insert into bank.accounts values (1, '0010101201600001016', 1, '2017-03-04', null, '2017-03-04', null, 'scott', 16, 0, null);
insert into bank.accounts values (2, '0010101201600002015', 0, '2017-03-04', null, '2017-03-04', null, 'scott', 15, 0, 'This account keeps money transferred from another banks to then transfer them to personal accounts.');
insert into bank.accounts values (3, '0010101201600003018', 0, '2017-03-04', null, '2017-03-04', null, 'scott', 18, 0, null);
insert into bank.accounts values (4, '0010101201600004017', 0, '2017-03-04', null, '2017-03-04', null, 'scott', 17, 0, null);
insert into bank.accounts values (8, '0010403201700001016',  1, '2017-03-04', null, '2017-03-04', null, 'scott', 16, 0, null);
insert into bank.accounts values (9, '0010101201600005015', 0, '2017-03-04', null, '2017-03-04', null, 'scott', 15, 0, 'This account keeps money to then transfer them to another banks.');
insert into bank.accounts values (10, '0010101201600006015', 0, '2017-03-04', null, '2017-03-04', null, 'scott', 15, 0, 'This account temporarily keeps money deposited by client, until operator transfer them to clients personal account.');
insert into bank.accounts values (11, '0010101201600007015', 0, '2017-03-04', null, '2017-03-04', null, 'scott', 15, 0, 'This account temporarily keeps money transferred from client''s personal account by operator, until client physically gets money.');

insert into bank.accounts values (12, '0010403201700002016',  2, '2017-03-04', null, '2017-03-04', null, 'scott', 16, 0, null);
insert into bank.accounts values (13, '0010403201700003016',  2, '2017-03-04', null, '2017-03-04', null, 'scott', 16, 0, null);
insert into bank.accounts values (14, '0010403201700004016',  3, '2017-03-04', null, '2017-03-04', null, 'scott', 16, 0, null);
insert into bank.accounts values (15, '0010403201700005016',  4, '2017-03-04', null, '2017-03-04', null, 'scott', 16, 0, null);
insert into bank.accounts values (16, '0010403201700006016',  4, '2017-03-04', null, '2017-03-04', null, 'scott', 16, 0, null);
alter sequence bank.accounts_seq restart with 17;

insert into bank.transactions values (0, 14, 0, 0,  '2017-03-04', '19:10:41.554173', 'scott', 2, 3);
insert into bank.transactions values (1, 12, 0, 2000, '2017-03-04', '18:42:14.941146', 'scott', 2, 1);
insert into bank.transactions values (4, 14, 0, 0,  '2017-03-04', '17:55:45.573993', 'scott', 2, 8);

insert into bank.transactions values (5, 14, 0, 0,  '2017-03-04', '17:00:45.573993', 'scott', 2, 12);
insert into bank.transactions values (6, 14, 0, 0,  '2017-03-04', '17:00:45.573993', 'scott', 2, 13);
insert into bank.transactions values (7, 14, 0, 0,  '2017-03-04', '17:00:45.573993', 'scott', 2, 14);
insert into bank.transactions values (8, 14, 0, 0,  '2017-03-04', '17:00:45.573993', 'scott', 2, 15);
insert into bank.transactions values (9, 14, 0, 0,  '2017-03-04', '17:00:45.573993', 'scott', 2, 16);

insert into bank.transactions values (10, 12, 0, 15000,  '2017-03-04', '17:05:45.573993', 'scott', 10, 12);
insert into bank.transactions values (11, 11, 0, 10000,  '2017-03-04', '17:35:45.573993', 'scott', 12, 16);
insert into bank.transactions values (12, 13, 0, 10000,  '2017-03-04', '17:55:45.573993', 'scott', 16, 11);

insert into bank.transactions values (13, 11, 0, 5000,  '2017-03-04', '17:56:45.573993', 'scott', 12, 14);
insert into bank.transactions values (14, 11, 1, 5000,  '2017-03-04', '17:57:45.573993', 'scott', 12, 14);
alter sequence bank.transactions_seq restart with 15;

insert into bank.account_rest values (1, 1, 2000,  1, '2017-03-04', '18:46:51.652568');
insert into bank.account_rest values (2, 2, 1000000, 0, '2017-03-04', '18:47:29.324624');
insert into bank.account_rest values (3, 3, 1000000, 0, '2017-03-04', '18:47:42.796646');
insert into bank.account_rest values (4, 4, 1000000, 0, '2017-03-04', '18:47:53.576661');
insert into bank.account_rest values (6, 8, 0, 4, '2017-03-04', '17:55:45.573993');
insert into bank.account_rest values (7, 9, 1000000, 0, '2017-03-04', '18:47:29.324624');
insert into bank.account_rest values (8, 10, 1000000, 0, '2017-03-04', '18:47:29.324624');
insert into bank.account_rest values (9, 11, 1000000, 0, '2017-03-04', '18:47:29.324624');
insert into bank.account_rest values (10, 12, 0, 5, '2017-03-04', '17:00:45.573993');
insert into bank.account_rest values (11, 13, 0, 6, '2017-03-04', '17:00:45.573993');
insert into bank.account_rest values (12, 14, 0, 7, '2017-03-04', '17:00:45.573993');
insert into bank.account_rest values (13, 15, 0, 8, '2017-03-04', '17:00:45.573993');
insert into bank.account_rest values (14, 16, 0, 9, '2017-03-04', '17:00:45.573993');
insert into bank.account_rest values (15, 10, 985000, 10, '2017-03-04', '17:05:45.573993');
insert into bank.account_rest values (16, 12, 15000, 10, '2017-03-04', '17:05:45.573993');
insert into bank.account_rest values (17, 12, 5000, 11, '2017-03-04', '17:35:45.573993');
insert into bank.account_rest values (18, 16, 10000, 11, '2017-03-04', '17:35:45.573993');
insert into bank.account_rest values (19, 16, 0, 12, '2017-03-04', '17:55:45.573993');
insert into bank.account_rest values (20, 11, 1010000, 12, '2017-03-04', '17:55:45.573993');
insert into bank.account_rest values (21, 12, 0, 13, '2017-03-04', '17:58:45.573993');
insert into bank.account_rest values (22, 14, 5000, 13, '2017-03-04', '17:58:45.573993');
insert into bank.account_rest values (23, 14, 0, 14, '2017-03-04', '17:59:45.573993');
insert into bank.account_rest values (24, 12, 5000, 14, '2017-03-04', '17:59:45.573993');
alter sequence bank.account_rest_seq restart with 25;

INSERT INTO bank.users(username, password, is_active)
VALUES ('john', '$2a$10$oLYgFhseqsx8c/YttTFB2uFc6J79po8FukVMf8KST.Y8L1mXSLNli', 1);
INSERT INTO bank.users(username, password, is_active)
VALUES ('harry', '$2a$10$S8MubWHS/fbzQ/RCfudGaeQdDMmn6ERHu19byQYFpXMIUUyG13nYS', 1);
INSERT INTO bank.users(username, password, is_active)
VALUES ('simon', '$2a$10$aw6Vv911UjceBhkNCCNmgORsRi9LQ/JQ9B/j3NTkm6TC6XlB4SdYC', 1);
INSERT INTO bank.users(username, password, is_active)
VALUES ('demo', '$2a$10$k1LiqtAmofNHyEToJz5yCez29bqbX/us8FGap//huecy.w53.QNH2', 1);

INSERT INTO bank.user_roles (user_role_id, username, user_role)
VALUES (1, 'harry', 'ROLE_OPERATOR');
INSERT INTO bank.user_roles (user_role_id, username, user_role)
VALUES (2, 'john', 'ROLE_ADMIN');
INSERT INTO bank.user_roles (user_role_id, username, user_role)
VALUES (3, 'simon', 'ROLE_ACCOUNTANT');
INSERT INTO bank.user_roles (user_role_id, username, user_role)
VALUES (4, 'demo', 'ROLE_ACCOUNTANT');
INSERT INTO bank.user_roles (user_role_id, username, user_role)
VALUES (5, 'demo', 'ROLE_OPERATOR');
INSERT INTO bank.user_roles (user_role_id, username, user_role)
VALUES (6, 'demo', 'ROLE_ADMIN');
alter sequence bank.user_roles_user_role_id_seq restart with 7;

insert into bank.bank_parameters (parent_id, parameter_name, value, date_created, date_modified, active_from, active_to, user_id)
values (null, 'TRANSFER_ACCOUNT', '0010101201600002015', now(), now(), '2017-03-04', '2036-01-01', user);
insert into bank.bank_parameters (parent_id, parameter_name, value, date_created, date_modified, active_from, active_to, user_id)
values (null, 'BANK_NAME', 'MyBank', now(), now(), '2017-03-04', '2036-01-01', user);
insert into bank.bank_parameters (parent_id, parameter_name, value, date_created, date_modified, active_from, active_to, user_id)
values (null, 'BANK_LICENCE', 'WB0003$7373737', now(), now(), '2017-03-04', '2036-01-01', user);
