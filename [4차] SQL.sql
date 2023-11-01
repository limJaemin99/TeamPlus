
/* Drop Tables */

DROP TABLE SubComments CASCADE CONSTRAINTS;
DROP TABLE Comments CASCADE CONSTRAINTS;
DROP TABLE DataShare CASCADE CONSTRAINTS;
DROP TABLE MyNote CASCADE CONSTRAINTS;
DROP TABLE PrivateTodo CASCADE CONSTRAINTS;
DROP TABLE Team CASCADE CONSTRAINTS;
DROP TABLE TeamTodo CASCADE CONSTRAINTS;
DROP TABLE Project CASCADE CONSTRAINTS;
DROP TABLE Users CASCADE CONSTRAINTS;
DROP TABLE PRIVATECALENDAR CASCADE CONSTRAINTS;
DROP TABLE PROJECTCALENDAR CASCADE CONSTRAINTS;




/* Create Tables */

CREATE TABLE Comments
(
	-- 시퀀스 1부터 시작
	CommentNo number NOT NULL,
	-- 프로젝트 기간 (예상 종료일) - 프로젝트명_유저아이디_1
	TodoNo varchar2(4000) NOT NULL,
	-- user1
	UserNo varchar2(4000) NOT NULL,
	Content varchar2(4000) NOT NULL,
	regDate date DEFAULT sysdate NOT NULL,
	PRIMARY KEY (CommentNo)
);


CREATE TABLE DataShare
(
	-- share1
	ShareNo varchar2(4000) NOT NULL,
	-- 프로젝트 번호 (ProjectNo) - project1
	ProjectNo varchar2(1000) NOT NULL,
	-- user1
	UserNo varchar2(4000) NOT NULL,
	Title varchar(500) NOT NULL,
	Content varchar2(4000) NOT NULL,
	DataURL varchar2(4000) NOT NULL,
	Count number DEFAULT 0 NOT NULL,
	regDate date DEFAULT sysdate NOT NULL,
	PRIMARY KEY (ShareNo)
);


CREATE TABLE MyNote
(
	-- user1
	UserNo varchar2(4000) NOT NULL,
	-- insert into 테이블명
	-- values (max+1)
	-- where 사용자 번호 = #{사용자 번호}
	-- 
	NoteNo number NOT NULL,
	NoteDate date NOT NULL,
	Title varchar2(4000) NOT NULL,
	Content varchar2(4000) NOT NULL,
	password varchar2(100) NOT NULL,
	isPrivate varchar2(100),
	PRIMARY KEY (UserNo, NoteNo)
);


CREATE TABLE PrivateCalendar
(
    -- user1
    UserNo varchar2(1000) NOT NULL,
    -- 할 일 번호 - 유저아이디_1
    id varchar2(4000) NOT NULL,
    title varchar2(1000) NOT NULL,
    startDate date NOT NULL,
    endDate date,
    description varchar2(1000),
    location varchar(200),
    PRIMARY KEY (UserNo, id)
);


CREATE TABLE PrivateTodo
(
	-- 프로젝트 기간 (예상 종료일) - 프로젝트명_유저아이디_1
	TodoNo varchar2(4000) NOT NULL,
	-- user1
	UserNo varchar2(4000) NOT NULL,
	Title varchar2(4000) NOT NULL,
	Description varchar2(4000),
	TodoDate date default sysdate,
	EndDate date,
	Status number(1) DEFAULT 3,
	PRIMARY KEY (TodoNo)
);


CREATE TABLE Project
(
	-- 프로젝트 번호 (ProjectNo) - project1
	ProjectNo varchar2(1000) NOT NULL,
	Title varchar2(4000) NOT NULL,
	Description varchar2(4000),
	StartDate date NOT NULL,
	DueDate date NOT NULL,
	EndDate date,
	Password varchar2(4000) NOT NULL,
	Status number(1) DEFAULT 3 NOT NULL,
	PRIMARY KEY (ProjectNo)
);


CREATE TABLE ProjectCalendar
(
    -- 프로젝트 번호 (ProjectNo) - project1
    ProjectNo varchar2(1000) NOT NULL,
    -- 할 일 번호 - 유저아이디_1
    id varchar2(4000) NOT NULL,
    title varchar2(1000) NOT NULL,
    startDate date NOT NULL,
    endDate date,
    description varchar2(1000),
    location varchar2(200),
    PRIMARY KEY (ProjectNo, id)
);


CREATE TABLE SubComments
(
	-- 시퀀스 1부터 시작
	SubCommentNo number NOT NULL,
	-- user1
	UserNo varchar2(4000) NOT NULL,
	-- 시퀀스 1부터 시작
	CommentNo number NOT NULL,
	SubContent varchar2(4000) NOT NULL,
	regDate date DEFAULT sysdate NOT NULL,
	PRIMARY KEY (SubCommentNo)
);


CREATE TABLE Team
(
	-- user1
	UserNo varchar2(4000) NOT NULL,
	-- 프로젝트 번호 (ProjectNo) - project1
	ProjectNo varchar2(1000) NOT NULL,
	Leader number(1) DEFAULT 0 NOT NULL,
	PRIMARY KEY (UserNo, ProjectNo)
);


CREATE TABLE TeamTodo
(
	-- 프로젝트 기간 (예상 종료일) - 프로젝트명_유저아이디_1
	TodoNo varchar2(4000) NOT NULL,
	-- 프로젝트 번호 (ProjectNo) - project1
	ProjectNo varchar2(1000) NOT NULL,
	-- user1
	UserNo varchar2(4000) NOT NULL,
	Title varchar2(4000) NOT NULL,
	Description varchar2(4000),
	TodoDate date NOT NULL,
	DueDate date NOT NULL,
	EndDate date,
	Status number(1) DEFAULT 3 NOT NULL,
	PRIMARY KEY (TodoNo)
);


CREATE TABLE Users
(
	-- user1
	UserNo varchar2(1000) NOT NULL,
	Id varchar2(100) NOT NULL UNIQUE,
	-- Spring sequrity 사용
	password varchar2(1000) NOT NULL,
	name varchar2(50) NOT NULL,
	email varchar2(1000) NOT NULL UNIQUE,
	NickName varchar2(100) NOT NULL UNIQUE,
	job varchar2(100),
	description varchar2(4000),
    profileURL varchar2(4000),
    sns varchar2(50),
	PRIMARY KEY (UserNo)
);




/* Create Foreign Keys */

ALTER TABLE SubComments
	ADD FOREIGN KEY (CommentNo)
	REFERENCES Comments (CommentNo)
;


ALTER TABLE Team
	ADD FOREIGN KEY (ProjectNo)
	REFERENCES Project (ProjectNo)
;


ALTER TABLE TeamTodo
	ADD FOREIGN KEY (ProjectNo)
	REFERENCES Project (ProjectNo)
;


ALTER TABLE DataShare
	ADD FOREIGN KEY (ProjectNo, UserNo)
	REFERENCES Team (ProjectNo, UserNo)
;


ALTER TABLE Comments
	ADD FOREIGN KEY (TodoNo)
	REFERENCES TeamTodo (TodoNo)
;


ALTER TABLE Comments
	ADD FOREIGN KEY (UserNo)
	REFERENCES Users (UserNo)
;


ALTER TABLE MyNote
	ADD FOREIGN KEY (UserNo)
	REFERENCES Users (UserNo)
;


ALTER TABLE PrivateTodo
	ADD FOREIGN KEY (UserNo)
	REFERENCES Users (UserNo)
;


ALTER TABLE SubComments
	ADD FOREIGN KEY (UserNo)
	REFERENCES Users (UserNo)
;


ALTER TABLE Team
	ADD FOREIGN KEY (UserNo)
	REFERENCES Users (UserNo)
;


ALTER TABLE TeamTodo
	ADD FOREIGN KEY (UserNo)
	REFERENCES Users (UserNo)
;

----------------------------------------------------------------------------------------------------------------

/* Sequence */
DROP SEQUENCE projectNo_seq;
DROP SEQUENCE teamTodoNo_seq;
DROP SEQUENCE commentNo_seq;
DROP SEQUENCE subCommentNo_seq;
DROP SEQUENCE userTodoNo_seq;
DROP SEQUENCE userNo_seq;
DROP SEQUENCE shareNo_seq;

CREATE SEQUENCE projectNo_seq start with 1;		-- 프로젝트 번호 시퀀스 : Project.ProjectNo ▶ project1
CREATE SEQUENCE teamTodoNo_seq start with 1;	-- (팀) 할 일 번호 시퀀스 : TeamTodo.TodoNo ▶ 프로젝트명_유저아이디_1
CREATE SEQUENCE commentNo_seq start with 1;		-- 댓글 번호 시퀀스 : Comments.CommentNo ▶ 시퀀스 1부터 시작
CREATE SEQUENCE subCommentNo_seq start with 1;	-- 대댓글 번호 시퀀스 : SubComments.SubCommentNo ▶ 시퀀스 1부터 시작
CREATE SEQUENCE userTodoNo_seq start with 1;	-- (개인) 할 일 번호 시퀀스 : PrivateTodo.TodoNo ▶ 유저아이디_1
CREATE SEQUENCE userNo_seq start with 1;		-- 유저 번호 시퀀스 : Users.UserNo ▶ user1
CREATE SEQUENCE shareNo_seq start with 1;			-- 공유 번호 시퀀스 : DataShare.ShareNo ▶ share1

-- ★★★★★ 노트 번호는 시퀀스 사용 X , (MAX + 1) 사용 O ★★★★★


