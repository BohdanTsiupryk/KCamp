ALTER SESSION SET CURRENT_SCHEMA = DBUSER;

BEGIN

    EXECUTE IMMEDIATE 'create table CAMP_CHANGE
                       (
                           ID NUMBER(19) not null primary key,
                           CHANGE_NUMBER INTEGER,
                           PLACES INTEGER,
                           FREE_PLACE INTEGER,
                           PRICE INTEGER,
                           DESCRIPTION VARCHAR2(2048),
                           BEGIN_DATE DATE,
                           END_DATE DATE,
                           CHANGE_ID NUMBER(19),
                           CHANGES NUMBER(19)
                       )';

    EXECUTE IMMEDIATE 'create table COMMENTS
                       (
                           ID NUMBER(19) not null primary key,
                           RATE INTEGER,
                           MESSAGE VARCHAR2(2048),
                           AUTHOR VARCHAR2(255),
                           CAMP_ID NUMBER(19),
                           CAMP NUMBER(19)
                       )';

    execute immediate 'ALTER TABLE CAMP_CHANGE ADD CONSTRAINT FK_CAMP_CHANGE_CAMP_ID FOREIGN KEY (CHANGE_ID) REFERENCES CAMP(ID)';
    execute immediate 'ALTER TABLE COMMENTS ADD CONSTRAINT FK_COMMENTS_CAMP_ID FOREIGN KEY (CAMP_ID) REFERENCES CAMP(ID)';
END;