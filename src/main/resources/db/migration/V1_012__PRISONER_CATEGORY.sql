create table HPA.PRISONER_CATEGORY
(
    PK_PRISONER_CATEGORY int identity
        constraint PK_PRISONER_CATEGORIES
            primary key,
    PRISON_NUMBER        varchar(8) not null,
    DATE                 date,
    CATEGORY             varchar(28)
);

create index HPA_PRISONER_CATEGORY
    on HPA.PRISONER_CATEGORY (PRISON_NUMBER);

INSERT INTO HPA.PRISONER_CATEGORY (PK_PRISONER_CATEGORY, PRISON_NUMBER, DATE, CATEGORY) VALUES (1, N'AB111111', N'2001-01-01', N'CATEGORY D');
INSERT INTO HPA.PRISONER_CATEGORY (PK_PRISONER_CATEGORY, PRISON_NUMBER, DATE, CATEGORY) VALUES (2, N'AB111111', N'2001-01-02', N'UNCATEGORISED (SENT MALES)');
INSERT INTO HPA.PRISONER_CATEGORY (PK_PRISONER_CATEGORY, PRISON_NUMBER, DATE, CATEGORY) VALUES (3, N'AB111112', N'2001-01-01', N'CATEGORY C');
