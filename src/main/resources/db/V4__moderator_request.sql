ALTER SESSION SET CURRENT_SCHEMA = DBUSER;

BEGIN

    EXECUTE IMMEDIATE 'create table MODERATOR_REQUEST
                       (
                           ID NUMBER(19) not null primary key,
                           FULL_NAME VARCHAR2(255),
                           CAMP_NAME VARCHAR2(255),
                           CAMP_URL VARCHAR2(255),
                           MESSAGE VARCHAR2(2048),
                           PERSON_ID NUMBER(19) not null
                       )';

    execute immediate 'ALTER TABLE MODERATOR_REQUEST ADD CONSTRAINT FK_MODERATOR_REQ_USR_ID FOREIGN KEY (PERSON_ID) REFERENCES USR(ID)';
END;