use new_universal_health_services;
--------------------------------------------------------------------------------------------------------
-- Roles table
create table roles (role_id int not null auto_increment , role_name varchar(255), primary key (role_id));
insert into roles (role_name) values ('Admin');
insert into roles (role_name) values ('Doctor');
insert into roles (role_name) values ('Patient');
insert into roles (role_name) values ('Nurse');
insert into roles (role_name) values('Receptionist');
select * from roles;
 
--------------------------------------------------------------------------------------------------------
-- Specialization table
insert into specialization (category) values ('Cardiologist');
insert into specialization (category) values ('General Pediatrician');
insert into specialization (category) values ('Physician');
insert into specialization (category) values ('Dermatologists');
insert into specialization (category) values ('Ophthalmologists');
insert into specialization (category) values ('Neurologists');
insert into specialization (category) values ('Radiologists');
insert into specialization (category) values ('General Surgeons');
insert into specialization (category) values ('Oncologists');
insert into specialization (category) values ('Allergists');
select * from specialization;

--------------------------------------------------------------------------------------------------------
-- Doctor's Specialization table
create table doctors_specialization (doctors_specialization_id bigint not null auto_increment, 
specialization_id bigint not null, doctor_id bigint not null, primary key(doctors_specialization_id));
select * from doctors_specialization;

--------------------------------------------------------------------------------------------------------
-- Users table
create table users ( 
user_id bigint not null auto_increment, role_id int not null, firstname varchar(255) not null, 
lastname varchar(255) not null, email varchar(255) not null, mobile varchar(255) not null,
active int not null,  profile_url varchar(255),
login_attempts int, account_created datetime not null, last_login datetime, primary key (user_id) );

select * from users;

create table user_status_table (
user_status_id int not null auto_increment, account_type varchar(255) not null,
primary key (user_status_id)
);
select * from user_status_table;

create table patients_complaint(
patient_complaint_id bigint not null, complaint_type varchar(255) not null,
complaint_text mediumtext not null, reply_text mediumtext, complaint_status varchar(255),
primary key (patient_complaint_id)
);
select * from patients_complaint;


create database major_project_universal_health_services;
-- drop database major_project_universal_health_services;
use new_universal_health_services;
show tables;
use schema_versionslayered_api_warehouse;
select * from schema_version;
	
desc users;
desc patients_appointments;
select * from patients_appointments;
select * from users_role;
select * from users_account_status;
select * from specialization;
select * from doctors_specializations;
select * from users;
select * from users where users_role_id = 2;
select * from users_account_status;
insert into users_account_status (status) values('ACTIVE');
insert into users_account_status (status) values('INACTIVE');
insert into users_account_status (status) values('BLOCKED');
insert into users_account_status (status) values('BLOCKED_BY_ADMIN');
insert into users_account_status (status) values('DELETED');
select * from patient_complaints;
desc complaintspatient_complaints;

select pc.patient_complaints_id, pc.patient_id, u.firstname, u.lastname, u.email, 
u.account_status, pc.complaint_description, pc.complaint_status, pc.complaint_type, pc.admin_reply,
pc.reminder_sent, pc.reminder_sent_time, pc.created_time   from patient_complaints pc inner join users u on pc.patient_id = u.users_id where pc.patient_id = 27;

select pc.patient_complaints_id, pc.patient_id, u.firstname, u.lastname, u.email, 
u.account_status, pc.complaint_description, pc.complaint_status, pc.complaint_type, pc.admin_reply,
pc.reminder_sent, pc.reminder_sent_time, pc.created_time   from patient_complaints pc inner join users u on pc.patient_id = u.users_id;





select * from users;
select * from patients_appointments;
select * from doctors_specializations;
select * from users where users_role_id = 3;
desc doctors_specialization;
delete from users where users_id = 22;
select * from users_role;
select * from users_account_status; 
desc users_role;
insert into users_role (role_name) values ('ADMIN');
insert into users_role (role_name) values ('DOCTOR');
insert into users_role (role_name) values ('PATIENT');
insert into users_role (role_name) values ('NURSE');
insert into users_role (role_name) values('RECEPTIONIST');
select * from specialization; 
desc specialization;
insert into specialization (category) values ('Cardiologist');
insert into specialization (category) values ('General Pediatrician');
insert into specialization (category) values ('Physician');
insert into specialization (category) values ('Dermatologists');
insert into specialization (category) values ('Ophthalmologists');
insert into specialization (category) values ('Neurologists');
insert into specialization (category) values ('Radiologists');
insert into specialization (category) values ('General Surgeons');
insert into specialization (category) values ('Oncologists');
insert into specialization (category) values ('Allergists');

select * from users;
desc users;
select * from patients_appointments;

insert into patients_appointments (doctor_id, patient_id, appointment_date, appointment_time, appointment_status, created_time)
values(35, 37, "2022-11-13", "12:00", "PENDING","2022-11-24 22:05:23.479000");

select * from users where users.users_id in (
select doctor_id from doctors_specialization ds inner join
specialization s on ds.specialization_id = s.specialization_id where s.category = (select category from specialization where specialization_id = 3));

select * from patient_complaints;


Select * From Users where Users.usersId in (
			select doctorId from DoctorsSpecialization ds inner join 
			Specialization s on ds.specializationId = s.specializationId where s.category = 
			(select category from Specialization where specializationId = 3))

