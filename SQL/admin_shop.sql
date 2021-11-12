-- admin_shop 테이블 생성


create table admin_shop(
	admin_id varchar2(30) primary key,        -- 관리자 아이디
	admin_pwd varchar2(30) not null,          -- 관리자 비밀번호
	admin_name varchar2(30) not null,         -- 관리자 이름
	admin_email varchar2(100),                -- 관리자 이메일
	admin_date date                           -- 관리자 등록일
);


-- 관리자 등록하기
insert into admin_shop
	values('admin', 'admin', '관리자', 'admin@naver.com', sysdate);



