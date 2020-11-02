ALTER SESSION SET CURRENT_SCHEMA = DBUSER;

BEGIN

    EXECUTE IMMEDIATE 'create table CHILD
        (
            ID NUMBER(19) not null primary key,
            BIRTHDAY DATE,
            CITIZENSHIP VARCHAR2(255),
            DOCUMENT VARCHAR2(255),
            FULL_NAME VARCHAR2(255),
            SPECIAL_WISHES VARCHAR2(2048),
            PARENT_ID NUMBER(19) not null
            )';

    EXECUTE IMMEDIATE 'create table CHILD_TRIP
        (
            CHILD_ID NUMBER(19) not null constraint FK_TRIP_CHILD_ID references CHILD,
            TRIP_ID NUMBER(19) not null constraint FK_TRIP_TRIP_ID references BOUGHT_TRIP,
            primary key (TRIP_ID, CHILD_ID)
            )';

    EXECUTE IMMEDIATE 'create table CAMP
        (
            ID NUMBER(19) not null primary key,
            ADDRESS VARCHAR2(255),
            DESCRIPTION VARCHAR2(2048),
            MAIN_PIC_NAME VARCHAR2(255),
            NAME_CAMP VARCHAR2(255),
            RATING FLOAT,
            USER_ID NUMBER(19) constraint FK_CAMP_USR_ID references USR
            )';

    execute immediate 'ALTER TABLE CHILD ADD CONSTRAINT FK_CHILD_USR_ID FOREIGN KEY (PARENT_ID) REFERENCES USR(ID)';
end;