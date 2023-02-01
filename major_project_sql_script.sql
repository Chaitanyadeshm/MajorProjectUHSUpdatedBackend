create database major_project_universal_health_services;
use major_project_universal_health_services;
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
create table specialization (specialization_id bigint not null auto_increment, category text not null, 
primary key(specialization_id) );
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
use major_project_universal_health_services;
show tables;
select * from users;
desc users;
desc patients_appointments;
desc doctors_specialization;

select * from users_account_status;
select * from patient_complaints;
select * from patients_appointments;
select * from doctors_specialization;
desc doctors_specialization;
delete from users where users_id = 22;
select * from users_role;
insert into users_role values (1, 'Admin');
insert into users_role values (2, 'Doctor');
insert into users_role values (3, 'Patient');
insert into users_role (role_name) values ('Nurse');
insert into users_role (role_name) values('Rece	ptionist');
select * from specialization; 
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

select * from patients_appointments;
insert into patients_appointments (doctor_id, patient_id, appointment_date, appointment_time, appointment_status, created_time)
values(24, 5, "2022-11-12", "12:00", "APPROVE","2022-11-24 22:05:23.479000");

select * from users where users.users_id in (
select doctor_id from doctors_specialization ds inner join
specialization s on ds.specialization_id = s.specialization_id where s.category = (select category from specialization where specialization_id = 3));

--  alter table patient_complaints modify column  patient_complaints_id bigint;

select  appointment_id, patient_id, firstname as patients_firstname, 
lastname as patients_lastname, email, doctor_id,
(select firstname from users where users.users_id = patients_appointments.doctor_id) as doctors_firstname,
(select lastname from users where users.users_id = patients_appointments.doctor_id) as doctors_lastname,
(select email from users where users.users_id = patients_appointments.doctor_id) as doctors_email,
appointment_date, appointment_time,appointment_status,
feedback , doctors_ratings, patients_notes, prescription, created_time from users inner join patients_appointments on patients_appointments.patient_id = users.users_id
order by appointment_id desc;

-- select firstname ,lastname from users where users.user_id = appointments.doctor_id



Select * From Users where Users.usersId in (
			select doctorId from DoctorsSpecialization ds inner join 
			Specialization s on ds.specializationId = s.specializationId where s.category = 
			(select category from Specialization where specializationId = 3))

