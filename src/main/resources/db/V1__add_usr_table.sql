ALTER SESSION SET CURRENT_SCHEMA = DBUSER;

BEGIN

    EXECUTE IMMEDIATE 'create table USR
        (
            ID NUMBER(19) not null primary key,
            ACTIVATION_CODE VARCHAR2(255),
            ACTIVE NUMBER(1) not null,
            EMAIL VARCHAR2(255) constraint UK_EMAIL_USR unique,
            FULL_NAME VARCHAR2(255),
            PASSWORD VARCHAR2(255),
            PHONE VARCHAR2(14),
            USERNAME VARCHAR2(255),
            ADDRESS NUMBER(19),
            PERSON_ID NUMBER(19)
            )';

    EXECUTE IMMEDIATE 'create table USER_ROLE
        (
            USER_ID NUMBER(19),
            ROLE VARCHAR2(255)
            )';

    EXECUTE IMMEDIATE 'create table USER_INFO (
            ID NUMBER(19) not null primary key,
            BIRTHDAY DATE,
            CITIZENSHIP VARCHAR2(255),
            PASSPORT_NUMBER VARCHAR2(255),
            USER_ID NUMBER(19)
            )';

    EXECUTE IMMEDIATE 'create table USER_ADDRESS
        (
            ID NUMBER(19) not null primary key,
            ADDRESS VARCHAR2(255),
            CITY VARCHAR2(255)
            )';


    EXECUTE IMMEDIATE 'create table BOUGHT_TRIP
        (
            ID NUMBER(19) not null primary key,
            ORDER_ID VARCHAR2(255),
            CHANGE_ID NUMBER(19) not null,
            BOUGHT_TRIPS NUMBER(19)
            )';

    execute immediate 'ALTER TABLE USR ADD CONSTRAINT FK_ADDRESS FOREIGN KEY (ADDRESS) REFERENCES USER_ADDRESS(ID)';
    execute immediate 'ALTER TABLE USER_ROLE ADD CONSTRAINT FK_ROLE_USER_ID FOREIGN KEY (USER_ID) REFERENCES USR(ID)';
    execute immediate 'ALTER TABLE USER_INFO ADD CONSTRAINT FK_INFO_USER_ID FOREIGN KEY (USER_ID) REFERENCES USR(ID)';
    execute immediate 'ALTER TABLE BOUGHT_TRIP ADD CONSTRAINT FK_TRIP_USER_ID FOREIGN KEY (BOUGHT_TRIPS) REFERENCES USR(ID)';


    execute immediate 'CREATE SEQUENCE HIBERNATE_SEQUENCE';
END ;

