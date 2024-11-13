create table HPA.MOVEMENTS
(
    PK_MOVEMENT   int identity
        constraint PK_MOVEMENTS
            primary key,
    PRISON_NUMBER varchar(8) not null,
    DATE          date,
    TIME          decimal(6),
    TYPE          char,
    MOVEMENT      varchar(28),
    ESTABLISHMENT varchar(30)
);

create index HPA_MOVEMENTS
    on HPA.MOVEMENTS (PRISON_NUMBER, DATE, TIME);

INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (1, N'AB111111', N'1987-09-28', 70000, N'R', N'UNCONVICTED REMAND', N'BELMARSH');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (2, N'AB111111', N'1987-12-21', 90000, N'D', N'DISCHARGED TO COURT', N'BELMARSH');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (3, N'AB111111', N'1987-12-21', 170000, N'R', N'UNCONVICTED REMAND', N'DURHAM');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (4, N'AB111111', N'1988-02-12', 90000, N'D', N'DISCHARGED TO COURT', N'FRANKLAND');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (5, N'AB111112', N'1988-02-12', 170000, N'R', N'DETENTION IN YO INSTITUTION', N'FULL SUTTON');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (6, N'AB111112', N'1988-03-30', 220000, N'D', N'TRANSFER IN ENGLAND & WALES', N'LONG LARTIN');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (7, N'AB111112', N'1988-03-30', 230000, N'R', N'TRANSFER IN FROM OTHER ESTAB', N'WAKEFIELD');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (8, N'AB111112', N'1989-10-26', 170000, N'R', N'DETENTION IN YO INSTITUTION', N'DURHAM');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (9, N'AB111112', N'1989-11-30', 233000, N'D', N'PAROLE/LICENSE', N'FRANKLAND');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (10, N'BF123451', N'1985-10-26', 233000, N'R', N'UNCONVICTED REMAND', N'HILL VALLEY');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (11, N'BF123451', N'1985-10-27', 233000, N'D', N'DISCHARGED TO COURT', N'HILL VALLEY');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (12, N'BF123451', N'1985-10-28', 233000, N'R', N'DETENTION IN YO INSTITUTION', N'HILL VALLEY');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (13, N'BF123451', N'1985-10-29', 233000, N'D', N'TRANSFER IN ENGLAND & WALES', N'HILL VALLEY');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (14, N'BF123452', N'1985-10-26', 233000, N'R', N'UNCONVICTED REMAND', N'HILL VALLEY');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (15, N'BF123452', N'1985-10-27', 233000, N'D', N'DISCHARGED TO COURT', N'HILL VALLEY');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (16, N'BF123452', N'1985-10-28', 233000, N'R', N'DETENTION IN YO INSTITUTION', N'HILL VALLEY');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (17, N'BF123452', N'1985-10-29', 233000, N'D', N'TRANSFER IN ENGLAND & WALES', N'HILL VALLEY');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (18, N'BF123453', N'1985-10-26', 233000, N'R', N'UNCONVICTED REMAND', N'HILL VALLEY');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (19, N'BF123453', N'1985-10-27', 233000, N'D', N'DISCHARGED TO COURT', N'HILL VALLEY');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (20, N'BF123453', N'1985-10-28', 233000, N'R', N'DETENTION IN YO INSTITUTION', N'HILL VALLEY');
INSERT INTO HPA.MOVEMENTS (PK_MOVEMENT, PRISON_NUMBER, DATE, TIME, TYPE, MOVEMENT, ESTABLISHMENT) VALUES (21, N'BF123453', N'1985-10-29', 233000, N'D', N'TRANSFER IN ENGLAND & WALES', N'HILL VALLEY');
