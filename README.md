MyBank Retail Bank System v 0.2
=======

MyBank is example of Core banking, which includes customers information and transaction accounts, except loans and deposits. It is Spring based MVC application, which allows to manage customers information, accounts and transaction management. Also, there is user management including user roles to distribute responsibilities.


Technology used:
=======

-	JDK 1.8
-	Apache Tomcat 9.0
-	PostgreSQL 9.4.1212
-	Spring Framework 4.2.2.RELEASE
-	Spring MVC 4.2.2.RELEASE
-	Spring Security 4.0.3.RELEASE
-	Hibernate 5.2.10.Final
-	Velocity Template 1.7
-	Bootstrap 3.3.7
-	DataTables 1.10.13
-	Log4j 1.2.17

Installation:
=======

1.	Install PostgreSQL 
2.	Create database "bankdb"
3.	Open PgAdmin (or psql) and execute script.sql, then also execute data.sql
4.	Install Tomcat
5.	Copy my-bank.war into directory "webapps" of Tomcat (or use localhost:8080/manager to deploy web archive).
6.	Startup Tomcat
7.	Type in browser: localhost:8080/my-bank

Customer information
=======

Customer is the main entity of MyBank retail bank system. Every customer must have its major information (first and last name, date of birth) and related optional entities like papers, addresses, contacts and accounts (more information about accounts in the next section). Every of those entities can be made only in customer details page. In order to open customer details one should click on "Customers" menu item and then click on appropriate record in the list to get more information about selected customer. Also, there is "New customer" button on the top of the list to make new customer in the MyBank.

Once you are on the customer details page, you can click on the button "Contacts" and see all his or her existing contact information. Here, you also can add new contact information through button "Add contact". You can jump to contact details page by clicking on desirable record in the contacts list. Besides, you can see list or add a new account, address or paper in the same manner as with contacts.

Every "details" page (Customer details, contacts details and so on) has extra two buttons: edit and block. If you need to change some details you can click on "Edit", make your corrections and click "Save" (or "Cancel" if you change your mind).
Blocking customer prevents from changing any related data, including prevention of making transactions on customer's accounts. Moreover, you can block addresses, contacts and papers locally (accounts can be only suspended, but it takes same effect). However, you can unblock customer (or other blocked entities) whenever you want.

Accounts
=======

All accounts of MyBank are located on "All accounts" page ("Accounts" menu), but accounts related to specific customer can be found only via "Customer details" page. Also, new account can be made only on customer details page (by click on "Add account" on accounts list modal window). There are four buttons on account details page: Transactions, Edit, Close, Suspend. Using the button "Close" will close account. Closed account cannot be reopened. Account can be suspended by clicking on the button "Suspend" in order to prevent any actions on it. Later, if it is necessary, account could be released.

Money transfer between accounts operates using transactions. Full list of transactions can be found on "All transactions" page ("Transactions" menu), and on the account page you can find only transactions related to current account. New transaction can be made only on account details page.

Every new account has 0 on its balance and one 'VOID' transaction which made that balance. Every transaction (except VOID) decreases balance of one account and increases of another, and vice versa if it is reverse transaction. 

Here is the possible structure of bank account (not compulsory): BBB DDMMYYYY CCCCC TTT (e.g. 0032812201500152056).  BBB - branch, DDMMYYYY - opening date, CCCCC - counter starting 0 every business day, TTT - type of the account (ID of following account types: buffer, personal, interest, system).

There are six types of transactions. Five of them should correspond with buffer-type account (incoming, outgoing, refill, withdrawal, void). Only domestic-type account transfer funds between concrete accounts (e.g. between accounts of two different customers).


Parameters
=======

Directory contains various grouped typed values which used in almost every entity of MyBank. For instance, type SKYPE in CONTACTS group allows to add a skype ID into contacts list of concrete customer.

Bank parameters has global values identified by meaningful name. It could be, for example, name of bank or version of retail bank system which will be used somewhere in the program.

Security
=======

System has three roles: ROLE_OPERATOR, ROLE_ACCOUNTANT, ROLE_ADMIN. Each of which responsible for specific area of the retail bank system. ROLE_OPERATOR allowed to operate with customer, address, contacts, papers and reports. On the other hand ROLE_ACCOUNTANT responsible for accounts, transactions and reports too. Finally, ROLE_ADMIN rule following sections: directory, bank parameters, users.
>Every role (if it's active) can be referenced to specific user. System already has four users:
>- harry (has role ROLE_OPERATOR)
>- simon (has role ROLE_ACCOUNTANT)
>- john (has role ROLE_ADMIN)
>- demo (has all three roles)

>Password is one for all: 12345

Service fields.
=======

Most of the entities have information about when record was added (dateCreated), when it was modified (dateModified) and by whom it was made (userId). Besides, there is information whether record is active or not (isActive). Some entities might have information about their period of life (activeFrom, activeTo) or opening date and closing date (dateOpened, dateClosed) instead of defining whether record active or not. Moreover, no rows can be deleted, only deactivation is possible.


Step-by-step example:
=======

1.	First, it is necessary to create a new customer in MyBank. To do so, go to "Customers" menu on the left panel to open  "Customers list" page, then click on green "New customer" button on the top of the page. Fill all fields and click on "Save".
2.	Second, filling addresses, contacts, papers and accounts. They can be filled in any order and procedure of filling is very similar to each other. Therefore, it is enough to show only one of them for how to make it. For customer accounts one should select as a type "PERSONAL" because MyBank is considered only for individuals. Let’s create new address for current customer, opening him or her in "Customer list" mentioned in previous section. As a result "Customer details" page will be opened. On this page click on blue "Addresses" button on the top of the page to get list of the addresses in the modal window. Since current customer is brand new there is no address. In order to add new address click on "Add address" button. It leads to "New address" page where one should fill all fields and click on "Save" button. 
3.	Finally, creating transactions. Transaction involves two accounts (debit and credit), but in order to create one it is necessary to start to create it on debit account (usually money moves from debit to credit). To do so, pick desirable account in the accounts list on "Customer info" page or pick one on the page "All accounts" by clicking on "Accounts" on the left panel. After that, click on the green "Transactions" button on the "Account" page. Next, click on the "New transaction" to get reach filling form. On the filling form the debit account is current account and credit account should be selected from the list, then pick operation type and indicate amount. Also, it is possible to make transaction reversed, so money moves from credit to debit. And, to finish transaction creation click on "Save" button.
