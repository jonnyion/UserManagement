insert into APP_GROUP  values (1,'SYSTEM');
insert into APP_GROUP  values (2,'DBA');
insert into APP_GROUP  values (3,'ADMIN');
insert into APP_GROUP  values (4,'REGULAR');

insert into APP_USER  values (1,'admin@yahoo.com','admin');
insert into APP_USER  values (2,'ionut@yahoo.com','ionut');
insert into APP_USER  values (3,'cristi@gmail.com','cristi');
insert into APP_USER  values (4,'user@yahoo.com','user');

insert into APP_USER_GROUP values (1,1);
insert into APP_USER_GROUP values (1,2);
insert into APP_USER_GROUP values (1,3);
insert into APP_USER_GROUP values (2,3);
insert into APP_USER_GROUP values (2,4);
insert into APP_USER_GROUP values (3,4);

