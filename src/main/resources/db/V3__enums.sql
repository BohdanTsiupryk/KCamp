ALTER SESSION SET CURRENT_SCHEMA = DBUSER;

BEGIN

    EXECUTE IMMEDIATE 'create table CHILDHOOD
        (
            CAMP_ID NUMBER(19) not null constraint FK_CHILDHOOD_CAMP_ID references CAMP,
            CHILDHOODS VARCHAR2(255)
            )';

    EXECUTE IMMEDIATE 'create table CAMP_LOCATION
        (
            CAMP_ID NUMBER(19) not null constraint FK_CAMP_LOCATION_CAMP_ID references CAMP,
            LOCATIONS VARCHAR2(255)
            )';

    EXECUTE IMMEDIATE 'create table CAMP_INTEREST
        (
            CAMP_ID NUMBER(19) not null constraint FK_CAMP_INTEREST_CAMP_ID references CAMP,
            INTERESTING VARCHAR2(255)
            )';

    EXECUTE IMMEDIATE 'create table CAMP_PHOTO
        (
            CAMP_ID NUMBER(19) not null constraint FK_CAMP_PHOTO_CAMP_ID references CAMP,
            CAMP_PHOTOS VARCHAR2(255)
            )';

END;