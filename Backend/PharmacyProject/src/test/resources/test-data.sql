
-- Roles
INSERT INTO ROLE (name) VALUES ('PATIENT');
INSERT INTO ROLE (name) VALUES ('ADMIN');
INSERT INTO ROLE (name) VALUES ('DERMATOLOGIST');
INSERT INTO ROLE (name) VALUES ('PHARMACIST');
INSERT INTO ROLE (name) VALUES ('PHARMACY_ADMIN');
INSERT INTO ROLE (name) VALUES ('SUPPLIER');

/*Locations*/
insert into location (longitude, latitude) values (19.833549, 45.267136);       /*Novi Sad*/
insert into location (longitude, latitude) values (20.43157, 44.80886);       /*Beograd*/
insert into location (longitude, latitude) values (19.8419405, 45.2474401);

/*Addresses*/
insert into address (street, city, country, location_id) values ('Omladinska 19', 'Novi Sad', 'Srbija', 1);
insert into address (street, city, country, location_id) values ('Jevrejska 4', 'Beograd', 'Srbija', 2);
insert into address (street, city, country, location_id) values ('Bulevar kralja Aleksandra 37', 'Beograd', 'Srbija', 2);
insert into address (street, city, country, location_id) values ('Fruskogorksa 14', 'Novi Sad', 'Srbija', 1);
insert into address (street, city, country, location_id) values ('Bulevar oslobodjenja 119', 'Novi Sad', 'Srbija', 3);

/*Medicine types*/
insert into medicine_type (name) values ('Antibiotik');
insert into medicine_type (name) values ('Anestetik');

/*Medicine forms*/
insert into medicine_form (name) values ('Šumeće granule');
insert into medicine_form (name) values ('Sirup');
insert into medicine_form (name) values ('Komprimovana lozenga');
insert into medicine_form (name) values ('Film tableta');

/*Manufacturers*/
insert into manufacturer (name) values ('ABBVIE S.R.L. - Italija');
insert into manufacturer (name) values ('GALENIKA AD BEOGRAD - Republika Srbija');
insert into manufacturer (name) values ('BOSNALIJEK D.D. - Bosna i Hercegovina');
insert into manufacturer (name) values ('IL CSM CLINICAL SUPPLIES MANAGEMENT - Nemačka');
insert into manufacturer (name) values ('ANPHARM PRZEDSIEBIORSTWO FARMACEUTYCZNE S.A. - Poljska');

/*Medicines*/
insert into medicine (name, code, content, side_effects, daily_intake, recipe_required, additional_notes, avg_grade,
                      points, medicine_type_id, medicine_form_id, manufacturer_id)
values ('Brufen', 'M01AE01', 'Najjaci lek za glavu.', 'Glavobolja.', 2, 'REQUIRED', 'Nema', 4.2, 10, 1, 1, 1);

insert into medicine (name, code, content, side_effects, daily_intake, recipe_required, additional_notes, avg_grade,
                      points, medicine_type_id, medicine_form_id, manufacturer_id)
values ('Paracetamol', 'M02AE01', 'Nema podataka.', 'Moze doci do ozbiljnih problema.', 2, 'REQUIRED', 'Nema', 4.5, 20, 2, 2, 2);

insert into medicine (name, code, content, side_effects, daily_intake, recipe_required, additional_notes, avg_grade,
                      points, medicine_type_id, medicine_form_id, manufacturer_id)
values ('Lysobact', 'R02AA', 'Nema podataka.',
        'Pacijenti sa retkim naslednim oboljenjem intolerancije na galaktozu, nedostatkom laktaze ili glukozno-galaktoznom malapsorpcijom ne smeju koristiti ovaj lek.', 7, 'NOTREQUIRED', 'Uobičajena doza je 6 do 8 komprimovanih lozengi tokom dana.',
        3.9, 5, 2, 3, 3);

insert into medicine (name, code, content, side_effects, daily_intake, recipe_required, additional_notes, avg_grade,
                      points, medicine_type_id, medicine_form_id, manufacturer_id)
values ('Kytril', 'A04AA02', 'Nema podataka.',
        'Kod pacijenata koji su prethodno imali aritmije ili poremećaje u srčanoj provodljivosti, ovo može dovesti do kliničkih posledica.', 2, 'REQUIRED', '1 mg dva puta na dan ili 2 mg jednom dnevno tokom perioda do nedelju dana nakon radioterapije ili hemioterapije. ',
        2.8, 15, 2, 4, 4);

insert into medicine (name, code, content, side_effects, daily_intake, recipe_required, additional_notes, avg_grade,
                      points, medicine_type_id, medicine_form_id, manufacturer_id)
values ('Prexanil', 'C09BA04', 'Nema podataka.',
        'Nema podataka.', 2, 'REQUIRED', 'Za lečenje esencijalne arterijske hipertenzije (I10) ukoliko se tromesečno lečenje pojedinačnim lekovima koji se koriste za lečenje hipertenzije, uključujući lečenje sa više pojedinačnih lekova istovremeno, pokazalo nedovoljno efikasno.',
        2.1, 3, 1, 4, 5);

/*Order items*/
insert into order_item (amount, medicine_id) values (50, 2);
insert into order_item (amount, medicine_id) values (150, 2);
insert into order_item (amount, medicine_id) values (70, 3);
insert into order_item (amount, medicine_id) values (60, 4);
insert into order_item (amount, medicine_id) values (100, 4);
insert into order_item (amount, medicine_id) values (100, 1);
insert into order_item (amount, medicine_id) values (150, 2);
insert into order_item (amount, medicine_id) values (50, 3);
insert into order_item (amount, medicine_id) values (20, 4);
insert into order_item (amount, medicine_id) values (110, 5);
insert into order_item (amount, medicine_id) values (130, 5);
insert into order_item (amount, medicine_id) values (110, 1);
insert into order_item (amount, medicine_id) values (130, 4);
insert into order_item (amount, medicine_id) values (110, 1);
insert into order_item (amount, medicine_id) values (130, 5);
insert into order_item (amount, medicine_id) values (10, 1);
insert into order_item (amount, medicine_id) values (2, 2);
insert into order_item (amount, medicine_id) values (25, 1);

/*Workplace - WorkDays*/

/*WorkDays*/
insert into work_day (weekday, start_time, end_time) values ('MON', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('TUE', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('WED', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('THU', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('FRI', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('MON', 7, 15);
insert into work_day (weekday, start_time, end_time) values ('TUE', 7, 15);
insert into work_day (weekday, start_time, end_time) values ('THU', 7, 15);
insert into work_day (weekday, start_time, end_time) values ('FRI', 7, 15);
insert into work_day (weekday, start_time, end_time) values ('WED', 7, 15);
insert into work_day (weekday, start_time, end_time) values ('MON', 15, 23);
insert into work_day (weekday, start_time, end_time) values ('TUE', 15, 23);
insert into work_day (weekday, start_time, end_time) values ('THU', 15, 23);
insert into work_day (weekday, start_time, end_time) values ('FRI', 15, 23);
insert into work_day (weekday, start_time, end_time) values ('WED', 15, 23);
insert into work_day (weekday, start_time, end_time) values ('SAT', 10, 18);
insert into work_day (weekday, start_time, end_time) values ('SUN', 10, 18);
insert into work_day (weekday, start_time, end_time) values ('MON', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('TUE', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('WED', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('THU', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('FRI', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('SUN', 10, 18);
insert into work_day (weekday, start_time, end_time) values ('SAT', 10, 18);
insert into work_day (weekday, start_time, end_time) values ('SUN', 9, 17);
insert into work_day (weekday, start_time, end_time) values ('SAT', 9, 17);
insert into work_day (weekday, start_time, end_time) values ('MON', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('TUE', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('WED', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('THU', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('FRI', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('SAT', 9, 17);
insert into work_day (weekday, start_time, end_time) values ('MON', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('TUE', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('WED', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('THU', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('FRI', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('SAT', 9, 17);
insert into work_day (weekday, start_time, end_time) values ('MON', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('TUE', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('WED', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('THU', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('FRI', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('SAT', 9, 17);
insert into work_day (weekday, start_time, end_time) values ('MON', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('TUE', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('WED', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('THU', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('FRI', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('MON', 7, 15);
insert into work_day (weekday, start_time, end_time) values ('TUE', 7, 15);
insert into work_day (weekday, start_time, end_time) values ('WED', 7, 15);

/*MyUsers*/
insert into my_user (password, first_name, last_name, email, telephone, role_id, address_id, is_password_changed, is_email_verified)
values ('$2y$12$qPMQwhqxS29Pn4bdnxtVROQXoFAHvLHpXfcWN70Ib8TASAzyN3nfq',
        'Petar', 'Markovic', 'perazdera@gmail.com', '0642312343', 1, 4, false, true);
insert into my_user (password, first_name, last_name, email, telephone, role_id, address_id, is_password_changed, is_email_verified)
values ('$2y$12$rjq.xA4OJ1hVOuaOEYhm2ugrUnh2a0Z/SW4fxBAE4/7PKrjWnDeOG',
        'Mitar', 'Zivkovic', 'mitazita87@gmail.com', '06433332343', 1, 4, false, false);
insert into my_user (password, first_name, last_name, email, telephone, role_id, address_id, is_password_changed, is_email_verified)
values ('$2y$12$RQzDkiUka7F5Ao1J14ATE.yWIbM6/JDHz0wAoRVbP0f0IfvmZFf0m',
        'Djura', 'Djuric', 'djule@gmail.com', '06433332341', 1, 4, false, true);
insert into my_user (password, first_name, last_name, email, telephone, role_id, address_id, is_password_changed, is_email_verified)
values ('$2y$12$2qGKnufjF6BUB8JssWuxZe5V3yhnhThDNG8ojyvzqNYnzFmzx1kzK',
        'Zdravko', 'Mitic', 'zlegenda78@gmail.com', '06233552343', 6, 3, false, true);
insert into my_user (password, first_name, last_name, email, telephone, role_id, address_id, is_password_changed, is_email_verified)
values ('$2y$12$Oh4n4xXU5.2JykOL1DqK9uynsiTlFQgT2S54In0bRL4311R/zhoBq',
        'Slavko', 'Vasic', 'slavisa@gmail.com', '0632232343', 6, 3, true, true);
insert into my_user (password, first_name, last_name, email, telephone, role_id, address_id, is_password_changed, is_email_verified)
values ('$2a$10$wBkTScEG1SWqwMp2..OmeOquvud4bQo0/MawPyXgWoUu6cHmpyc1i',
        'Nebojsa', 'Radovanovic', 'nebojsa@gmail.com', '0637732343', 4, 3, true, true);
insert into my_user (password, first_name, last_name, email, telephone, role_id, address_id, is_password_changed, is_email_verified)
values ('$2y$12$.9pfXfmpWsXqxlBiaxVs7e8zu2i4C.qTzDClIov4KcPaxRBEwSrt2',
        'Marko', 'Maric', 'marcius@gmail.com', '0634632343', 3, 3, true, true);
insert into my_user (password, first_name, last_name, email, telephone, role_id, address_id, is_password_changed, is_email_verified)
values ('$2y$12$tPT/EHBxszNtPA17Hu9p4OgHuZwJZMsrhT5.jYuBB6arrpaPG5gx6',
        'Dusko', 'Dragic', 'duskousko@gmail.com', '0628832343', 2, 3, false, true);
insert into my_user (password, first_name, last_name, email, telephone, role_id, address_id, is_password_changed, is_email_verified)
values ('$2y$12$zMWFTm7cC3JYZkCn35bG8.pr9jHaF4Yzz5vftYC7Ggnl.qFuep0uW',
        'Branko', 'Krasic', 'branimirko@gmail.com', '0658852343', 5, 3, false, true);
insert into my_user (password, first_name, last_name, email, telephone, role_id, address_id, is_password_changed, is_email_verified)
values ('$2y$12$RQzDkiUka7F5Ao1J14ATE.yWIbM6/JDHz0wAoRVbP0f0IfvmZFf0m',
        'Djura', 'Markovic', 'djule22@gmail.com', '06430032341', 1, 4, false, true);
insert into my_user (password, first_name, last_name, email, telephone, role_id, address_id, is_password_changed, is_email_verified)
values ('$2y$12$RQzDkiUka7F5Ao1J14ATE.yWIbM6/JDHz0wAoRVbP0f0IfvmZFf0m',
        'Marko', 'Markovic', 'djule00@gmail.com', '06930032341', 1, 4, false, true);
insert into my_user (password, first_name, last_name, email, telephone, role_id, address_id, is_password_changed, is_email_verified)
values ('$2y$12$RQzDkiUka7F5Ao1J14ATE.yWIbM6/JDHz0wAoRVbP0f0IfvmZFf0m',
        'Petar', 'Peric', 'djule1@gmail.com', '02430032341', 1, 4, false, true);
insert into my_user (password, first_name, last_name, email, telephone, role_id, address_id, is_password_changed, is_email_verified)
values ('$2y$12$RQzDkiUka7F5Ao1J14ATE.yWIbM6/JDHz0wAoRVbP0f0IfvmZFf0m',
        'Carli', 'Sin', 'djule009@gmail.com', '08430032341', 1, 4, false, true);
insert into my_user (password, first_name, last_name, email, telephone, role_id, address_id, is_password_changed, is_email_verified)
values ('$2y$12$RQzDkiUka7F5Ao1J14ATE.yWIbM6/JDHz0wAoRVbP0f0IfvmZFf0m',
        'Carli', 'Barli', 'carli@gmail.com', '08435555341', 3, 4, false, true);
insert into my_user (password, first_name, last_name, email, telephone, role_id, address_id, is_password_changed, is_email_verified)
values ('$2y$12$QUxaJq2WEdqSwPxT.BePtuD9xI1S74agszK6CR.rN20BpZhbhleAG',
        'Pavle', 'Majstorovic', 'pavle@gmail.com', '0631111111', 4, 3, false, true);
insert into my_user (password, first_name, last_name, email, telephone, role_id, address_id, is_password_changed, is_email_verified)
values ('$2y$12$QUxaJq2WEdqSwPxT.BePtuD9xI1S74agszK6CR.rN20BpZhbhleAG',
        'Milica', 'Golubovic', 'milicag@gmail.com', '0632222222', 4, 3, false, true);
insert into my_user (password, first_name, last_name, email, telephone, role_id, address_id, is_password_changed, is_email_verified)
values ('$2y$12$QUxaJq2WEdqSwPxT.BePtuD9xI1S74agszK6CR.rN20BpZhbhleAG',
        'Tatjana', 'Lukic', 'tatjanal@gmail.com', '0633333333', 4, 3, false, true);
insert into my_user (password, first_name, last_name, email, telephone, role_id, address_id, is_password_changed, is_email_verified)
values ('$2y$12$QUxaJq2WEdqSwPxT.BePtuD9xI1S74agszK6CR.rN20BpZhbhleAG',
        'Lea', 'Savic', 'leas@gmail.com', '0634444444', 4, 3, false, true);
insert into my_user (password, first_name, last_name, email, telephone, role_id, address_id, is_password_changed, is_email_verified)
values ('$2y$12$tPT/EHBxszNtPA17Hu9p4OgHuZwJZMsrhT5.jYuBB6arrpaPG5gx6',
        'Dusko', 'Dragic Novi Profil', 'duskousko2@gmail.com', '0628832344', 2, 3, false, true);
/*Pharmacy Workers*/
insert into pharmacy_worker (avg_grade, id) values (4.8, 6);
insert into pharmacy_worker (avg_grade, id) values (4.8, 7);
insert into pharmacy_worker (avg_grade, id) values (3.3, 14);
insert into pharmacy_worker (avg_grade, id) values (3.7, 15);
insert into pharmacy_worker (avg_grade, id) values (4.5, 16);
insert into pharmacy_worker (avg_grade, id) values (1.2, 17);
insert into pharmacy_worker (avg_grade, id) values (5.0, 18);

/*Patients*/
insert into patient (points, penalties, id) values (50, 2, 1);
insert into patient (points, penalties, id) values (43, 0, 2);
insert into patient (points, penalties, id) values (13, 3, 3);
insert into patient (points, penalties, id) values (150, 0, 10);
insert into patient (points, penalties, id) values (200, 0, 11);
insert into patient (points, penalties, id) values (305, 0, 12);
insert into patient (points, penalties, id) values (13, 0, 13);

/*Patients allergies*/
insert into patient_allergies (patient_id, allergies_id) values (1, 3);
insert into patient_allergies (patient_id, allergies_id) values (1, 4);
insert into patient_allergies (patient_id, allergies_id) values (2, 5);
insert into patient_allergies (patient_id, allergies_id) values (2, 4);

/*Suppliers*/
insert into supplier (id) values (5);
insert into supplier (id) values (4);

/*Supplier items*/
insert into supplier_item (amount, medicine_id, supplier_id) values (30, 1, 5);
insert into supplier_item (amount, medicine_id, supplier_id) values (20, 2, 5);
insert into supplier_item (amount, medicine_id, supplier_id) values (37, 3, 4);
insert into supplier_item (amount, medicine_id, supplier_id) values (15, 4, 4);
insert into supplier_item (amount, medicine_id, supplier_id) values (100, 4, 4);

/*Ratings*/
insert into rating (grade, graded_type, gradedId, date, patient_id) values (4, 'MEDICINE', 1, 1616510769000, 1);
insert into rating (grade, graded_type, gradedId, date, patient_id) values (5, 'MEDICINE', 3, 1616586369000, 2);
insert into rating (grade, graded_type, gradedId, date, patient_id) values (5, 3, 6, 1616586369000, 1);
insert into rating (grade, graded_type, gradedId, date, patient_id) values (4, 3, 7, 1616586369000, 1);

/*RankingCategory*/
insert into ranking_category (name, points_required, discount) values ('Bronza', 50, 5.0);
insert into ranking_category (name, points_required, discount) values ('Srebro', 100, 15.0);
insert into ranking_category (name, points_required, discount) values ('Zlato', 200, 30.0);

/*Medicine Prices*/
insert into medicine_price (price, start_date) values (449, 1618077600000);
insert into medicine_price (price, start_date) values (489, 1618160400000);
insert into medicine_price (price, start_date) values (250, 1615482000000);
insert into medicine_price (price, start_date) values (670, 1613062800000);
insert into medicine_price (price, start_date) values (1200, 1610384400000);

insert into medicine_price (price, start_date) values (470, 1578769200000);
insert into medicine_price (price, start_date) values (470, 1578769200000);
insert into medicine_price (price, start_date) values (410, 1616241600000);
insert into medicine_price (price, start_date) values (1345, 1616241600000);
insert into medicine_price (price, start_date) values (233, 1616241600000);

insert into medicine_price (price, start_date) values (459, 1616241600000);
insert into medicine_price (price, start_date) values (467, 1616241650000);
insert into medicine_price (price, start_date) values (278, 1616241600000);
insert into medicine_price (price, start_date) values (845, 1616241600000);
insert into medicine_price (price, start_date) values (640, 1616241600000);

insert into medicine_price (price, start_date) values (700, 1616241600000);


/*Complaints*/
insert into complaint (content, complaint_on, complaint_on_id, type, state, date, patient_id)
values ('Nije ispostovan dogovor, trazim da bude sankcionisan.', 'Nebojsa Radovanovic', 5, 'PHARMACIST', 'RESOLVED', 1616587200000, 1);
insert into complaint (content, complaint_on, complaint_on_id, type, state, date, patient_id)
values ('Neprikladno ponasanje.', 'Marko Maric', 6, 'DERMATOLOGIST', 'RESOLVED', 1616587203000, 2);
insert into complaint (content, complaint_on, complaint_on_id, type, state, date, patient_id)
values ('Neprikladno ponasanje OPET!!!.', 'Marko Maric', 6, 'DERMATOLOGIST', 'IN_PROGRESS', 1616587203000, 2);

/*Complaint Response*/
insert into complaint_response (response_text, date, complaint_id, user_id)
values ('Bice sakcionisan!', 1616673600000, 1, 8);
insert into complaint_response (response_text, date, complaint_id, user_id)
values ('Uz duzno postovanje, bice preuzete najstroze mere!', 1616673602000, 2, 8);

/*Pharmacies*/
insert into pharmacy (avg_grade, consultation_price, consultation_duration, description, name, points, address_id)
values (4.4, 500, 15, 'Najjaca apoteka u gradu.', 'Zelena Apoteka', 5, 5);
insert into pharmacy (avg_grade, consultation_price, consultation_duration, description, name, points, address_id)
values (4.8, 450, 10, 'Poverenje, sigurnost i dostupnost su, već skoro 30 godina, glavna obeležja Apotekarske ustanove Jankovic.', 'Apoteka Jankovic', 10, 1);
insert into pharmacy (avg_grade, consultation_price, consultation_duration, description, name, points, address_id)
values (2.9, 200, 5, 'Poručite sve što vam je potrebno na vašu kućnu adresu. Imas veliki izbor i sjajanu cenu samo na BENU online shop-u.', 'Benu Apoteka', 15, 2);


/*Price Lists*/
insert into price_list (pharmacy_id) values (1);
insert into price_list (pharmacy_id) values (2);
insert into price_list (pharmacy_id) values (3);

/*Medicine items*/
insert into medicine_item (amount, medicine_id, price_list_id) values (18, 1, 1);
insert into medicine_item (amount, medicine_id, price_list_id) values (25, 2, 1);
insert into medicine_item (amount, medicine_id, price_list_id) values (14, 3, 2);
insert into medicine_item (amount, medicine_id, price_list_id) values (55, 4, 3);
insert into medicine_item (amount, medicine_id, price_list_id) values (67, 5, 3);
insert into medicine_item (amount, medicine_id, price_list_id) values (30, 1, 2);
insert into medicine_item (amount, medicine_id, price_list_id) values (0, 5, 1);
insert into medicine_item (amount, medicine_id, price_list_id) values (100, 4, 1);
insert into medicine_item (amount, medicine_id, price_list_id) values (12, 2, 2);
insert into medicine_item (amount, medicine_id, price_list_id) values (63, 1, 3);
insert into medicine_item (amount, medicine_id, price_list_id) values (2, 2, 3);
insert into medicine_item (amount, medicine_id, price_list_id) values (9, 3, 1);

/* MedicineItem - MedicinePrices*/
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (1, 1);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (1, 2);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (2, 3);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (3, 4);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (4, 5);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (6, 6);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (7, 7);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (5, 8);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (8, 9);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (9, 10);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (10, 11);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (10, 12);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (11, 13);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (12, 14);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (12, 15);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (12, 16);

/*Pharmacy - Subscribers*/
insert into pharmacy_subscribers (pharmacy_id, subscribers_id) values (1, 1);
insert into pharmacy_subscribers (pharmacy_id, subscribers_id) values (2, 1);
insert into pharmacy_subscribers (pharmacy_id, subscribers_id) values (3, 1);
insert into pharmacy_subscribers (pharmacy_id, subscribers_id) values (3, 2);

/*Workplaces*/
insert into workplace (pharmacy_id, worker_id) values (1, 6); /* Farmaceut */
insert into workplace (pharmacy_id, worker_id) values (1, 7); /* Dermatolog */
insert into workplace (pharmacy_id, worker_id) values (2, 7); /* Dermatolog */
insert into workplace (pharmacy_id, worker_id) values (2, 14); /* Dermatolog */
insert into workplace (pharmacy_id, worker_id) values (2, 15); /* Farmaceut */
insert into workplace (pharmacy_id, worker_id) values (2, 16); /* Farmaceut */
insert into workplace (pharmacy_id, worker_id) values (3, 17); /* Farmaceut */
insert into workplace (pharmacy_id, worker_id) values (3, 18); /* Farmaceut */
insert into workplace (pharmacy_id, worker_id) values (3, 14); /* Dermatolog */

/*Workplace - WorkDays*/
/* Farmaceut 6 - radi prvih 6 dana u nedelji*/
insert into workplace_work_days (workplace_id, work_days_id) values (1, 1);
insert into workplace_work_days (workplace_id, work_days_id) values (1, 2);
insert into workplace_work_days (workplace_id, work_days_id) values (1, 3);
insert into workplace_work_days (workplace_id, work_days_id) values (1, 4);
insert into workplace_work_days (workplace_id, work_days_id) values (1, 5);
insert into workplace_work_days (workplace_id, work_days_id) values (1, 16);
/* Dermatolog 7 u apoteci 1 - radi prvih 5 dana u nedelji i nedeljom*/
insert into workplace_work_days (workplace_id, work_days_id) values (2, 18);
insert into workplace_work_days (workplace_id, work_days_id) values (2, 19);
insert into workplace_work_days (workplace_id, work_days_id) values (2, 23);
/* Dermatolog 7 u apoteci 2 - radi prvih 6 dana u nedelji*/
insert into workplace_work_days (workplace_id, work_days_id) values (3, 9);
insert into workplace_work_days (workplace_id, work_days_id) values (3, 10);
insert into workplace_work_days (workplace_id, work_days_id) values (3, 24);
/* Dermatolog 14 u apoteci 2 - radi samo vikednom */
insert into workplace_work_days (workplace_id, work_days_id) values (4, 25);
insert into workplace_work_days (workplace_id, work_days_id) values (4, 26);
/* Farmaceut 15 - radi prvih 5 dana u nedelji*/
insert into workplace_work_days (workplace_id, work_days_id) values (5, 27);
insert into workplace_work_days (workplace_id, work_days_id) values (5, 28);
insert into workplace_work_days (workplace_id, work_days_id) values (5, 29);
insert into workplace_work_days (workplace_id, work_days_id) values (5, 30);
insert into workplace_work_days (workplace_id, work_days_id) values (5, 31);
/* Farmaceut 16 - radi prvih 6 dana u nedelji*/
insert into workplace_work_days (workplace_id, work_days_id) values (6, 12);
insert into workplace_work_days (workplace_id, work_days_id) values (6, 13);
insert into workplace_work_days (workplace_id, work_days_id) values (6, 34);
insert into workplace_work_days (workplace_id, work_days_id) values (6, 35);
insert into workplace_work_days (workplace_id, work_days_id) values (6, 36);
insert into workplace_work_days (workplace_id, work_days_id) values (6, 37);
/* Farmaceut 17 - radi prvih 6 dana u nedelji*/
insert into workplace_work_days (workplace_id, work_days_id) values (7, 38);
insert into workplace_work_days (workplace_id, work_days_id) values (7, 39);
insert into workplace_work_days (workplace_id, work_days_id) values (7, 40);
insert into workplace_work_days (workplace_id, work_days_id) values (7, 41);
insert into workplace_work_days (workplace_id, work_days_id) values (7, 42);
insert into workplace_work_days (workplace_id, work_days_id) values (7, 43);
/* Farmaceut 18 - radi prvih 6 dana u nedelji*/
insert into workplace_work_days (workplace_id, work_days_id) values (8, 44);
insert into workplace_work_days (workplace_id, work_days_id) values (8, 45);
insert into workplace_work_days (workplace_id, work_days_id) values (8, 46);
insert into workplace_work_days (workplace_id, work_days_id) values (8, 47);
insert into workplace_work_days (workplace_id, work_days_id) values (8, 48);
insert into workplace_work_days (workplace_id, work_days_id) values (8, 49);
/* Dermatolog 14 u apoteci 3 - radi prva 3 dana u nedelji*/
insert into workplace_work_days (workplace_id, work_days_id) values (9, 50);
insert into workplace_work_days (workplace_id, work_days_id) values (9, 51);
insert into workplace_work_days (workplace_id, work_days_id) values (9, 52);

/*Appointments*/

-- Dodano zbog izvestaja
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1557937039000, 1557940639000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1557937039000, 1557940639000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1557937039000, 1557940639000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1526404639000, 1526406319000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1526404639000, 1526406319000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1526404639000, 1526406319000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1605462319000, 1605463819000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1605462319000, 1605463819000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1599750619000, 1599754219000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1599750619000, 1599754219000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1599703819000, 1599754219000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1441901419000, 1441901519000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1441901419000, 1441901519000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1410365419000, 1410366419000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1410365419000, 1410366419000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1410365419000, 1410366419000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1607616619000, 1607617619000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1607616619000, 1607617619000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1607616619000, 1607617619000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1607616619000, 1607617619000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1589127019000, 1589128019000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1589127019000, 1589128019000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1613466603000, 1613466603000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1613466603000, 1613466603000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1621156203000, 1621156203000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1621156203000, 1621156203000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1621156203000, 1621156203000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);


insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1611800300000, 1611801300000, 'Placanje u unapred.', 1700, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('RESERVED', 'CONSULTATION', 2, 1637354000000, 1637354030000, 'Placanje u kesu.', 1500, 1, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CHECKUP', 2, 1617354000000, 1617361200000, 'Placanje u unapred.', 1800, 2, 2, 7, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1617800000000, 1617800300000, 'Placanje u unapred.', 1800, 1, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1617800300000, 1617801300000, 'Placanje u unapred.', 1400, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1617800300000, 1617801300000, 'Placanje u unapred.', 2500, 3, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('RESERVED', 'CHECKUP', 10, 1621855200000, 1621856200000, 'Placanje u unapred.', 1800, 3, 1, 7, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CHECKUP', 12, 1617800300000, 1617801300000, 'Placanje u unapred 2.', 1700, 2, 1, 7, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1618209900000, 1618217100000, 'Placanje u unapred.', 1800, 10, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('RESERVED', 'CONSULTATION', 2, 1619547300000, 1619547500000, 'Placanje u unapred.', 1800, 11, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('RESERVED', 'CONSULTATION', 2, 1623325500000, 1623330000000, 'Placanje u unapred.', 1800, 12, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('RESERVED', 'CONSULTATION', 2, 1623744000000, 1623751200000, 'Placanje u unapred.', 1800, 13, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CHECKUP', 2, 1618840200000, 1618840230000, 'Placanje u unapred.', 1800, 1, 1, 7, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('RESERVED', 'CHECKUP', 2, 1621955200000, 1621955700000, 'Placanje u unapred.', 1800, 1, 1, 7, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CHECKUP', 2, 1618899780000, 1618899900000, 'Placanje u unapred.', 1800, 1, 1, 7, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CHECKUP', 2, 1618899960000, 1618900020000, 'Placanje u unapred.', 1800, 1, 1, 7, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CHECKUP', 2, 1618900080000, 1618900140000, 'Placanje u unapred.', 1800, 1, 1, 7, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('EMPTY', 'CHECKUP', 2, 1623754800000, 1623756600000, 'Placanje u kesu.', 1300, null, 1, 7, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('EMPTY', 'CHECKUP', 2, 1623756900000, 1623758400000, 'Placanje u kesu.', 2000, null, 1, 7, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('EMPTY', 'CHECKUP', 2, 1623758700000, 1623762300000, 'Placanje u kesu.', 3500, null, 1, 7, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('EMPTY', 'CHECKUP', 2, 1623844800000, 1623846600000, 'Placanje u kesu.', 2500, null, 1, 14, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1614800300000, 1614801300000, 'Placanje u unapred.', 1800, 10, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('RESERVED', 'CONSULTATION', 2, 1620712800000, 1620713800000, 'Placanje u unapred.', 1800, 1, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1617354000000, 1617354500000, 'Placanje u unapred.', 1800, 2, 1, 6, 0);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id, reservation_discount)
values ('FINISHED', 'CONSULTATION', 2, 1617654000000, 1617654500000, 'Placanje u unapred.', 1800, 11, 1, 6, 0);


/*RequestForHoliday*/
insert into request_for_holiday (absence_type, decline_text, start_date, end_date, request_state, pharmacy_worker_id)
values ('VACATION', '', 1619953200000, 1620817200000, 'ACCEPTED', 6);
insert into request_for_holiday (absence_type, decline_text, start_date, end_date, request_state, pharmacy_worker_id)
values ('LEAVE', '', 1619953200000, 1620817200000, 'ACCEPTED', 7);
insert into request_for_holiday (absence_type, decline_text, start_date, end_date, request_state, pharmacy_worker_id)
values ('LEAVE', '', 1623754800000, 1624190195000, 'PENDING', 6);
insert into request_for_holiday (absence_type, decline_text, start_date, end_date, request_state, pharmacy_worker_id)
values ('VACATION', '', 1623754800000, 1624190195000, 'PENDING', 7);
insert into request_for_holiday (absence_type, decline_text, start_date, end_date, request_state, pharmacy_worker_id)
values ('VACATION', '', 1628754800000, 1629190195000, 'PENDING', 6);


/*MyOrders*/
insert into my_order (admin_id, deadline, pharmacy_id, order_state) values (9, 1620817200000, 1, 'IN_PROGRESS');
insert into my_order (admin_id, deadline, pharmacy_id, order_state) values (9, 1620903600000, 2, 'IN_PROGRESS');
insert into my_order (admin_id, deadline, pharmacy_id, order_state) values (9, 1620817200000, 1, 'IN_PROGRESS');
insert into my_order (admin_id, deadline, pharmacy_id, order_state) values (9, 1620817200000, 1, 'IN_PROGRESS');
insert into my_order (admin_id, deadline, pharmacy_id, order_state) values (9, 1620817200000, 1, 'IN_PROGRESS');
insert into my_order (admin_id, deadline, pharmacy_id, order_state) values (9, 1620817200000, 1, 'IN_PROGRESS');
insert into my_order (admin_id, deadline, pharmacy_id, order_state) values (9, 1613411940000, 1, 'ON_HOLD');
insert into my_order (admin_id, deadline, pharmacy_id, order_state) values (9, 1624652935000, 1, 'IN_PROGRESS');
insert into my_order (admin_id, deadline, pharmacy_id, order_state) values (9, 1624652935000, 3, 'IN_PROGRESS');

/*MyOrders - OrderItems*/
insert into my_order_order_item (my_order_id, order_item_id) values (1, 1);
insert into my_order_order_item (my_order_id, order_item_id) values (1, 4);
insert into my_order_order_item (my_order_id, order_item_id) values (2, 2);
insert into my_order_order_item (my_order_id, order_item_id) values (2, 3);
insert into my_order_order_item (my_order_id, order_item_id) values (2, 5);
insert into my_order_order_item (my_order_id, order_item_id) values (3, 6);
insert into my_order_order_item (my_order_id, order_item_id) values (3, 7);
insert into my_order_order_item (my_order_id, order_item_id) values (4, 8);
insert into my_order_order_item (my_order_id, order_item_id) values (4, 9);
insert into my_order_order_item (my_order_id, order_item_id) values (5, 10);
insert into my_order_order_item (my_order_id, order_item_id) values (5, 11);
insert into my_order_order_item (my_order_id, order_item_id) values (6, 12);
insert into my_order_order_item (my_order_id, order_item_id) values (6, 13);
insert into my_order_order_item (my_order_id, order_item_id) values (7, 14);
insert into my_order_order_item (my_order_id, order_item_id) values (7, 15);
insert into my_order_order_item (my_order_id, order_item_id) values (8, 16);
insert into my_order_order_item (my_order_id, order_item_id) values (8, 17);
insert into my_order_order_item (my_order_id, order_item_id) values (9, 18);

/*Offers*/
insert into offer (delivery_date, offer_state, price, order_id)
values (1620730800000, 'ACCEPTED', 2300, 1);
insert into offer (delivery_date, offer_state, price, order_id)
values (1620730800000, 'PENDING', 2100, 3);
insert into offer (delivery_date, offer_state, price, order_id)
values (1620730800000, 'ACCEPTED', 3560, 2);
insert into offer (delivery_date, offer_state, price, order_id)
values (1612256008000, 'PENDING', 3560, 7);
insert into offer (delivery_date, offer_state, price, order_id)
values (1612256008000, 'PENDING', 2500, 7);

/*Supplier - Offers*/
insert into supplier_offers (supplier_id, offers_id) values (5, 1);
insert into supplier_offers (supplier_id, offers_id) values (4, 2);
insert into supplier_offers (supplier_id, offers_id) values (5, 3);
insert into supplier_offers (supplier_id, offers_id) values (4, 4);
insert into supplier_offers (supplier_id, offers_id) values (5, 5);

/*ERecipe Items*/
insert into erecipe_item (medicine_code, medicine_name, quantity) values ('M01AE01', 'Brufen', 2);
insert into erecipe_item (medicine_code, medicine_name, quantity) values ('N02BE01', 'Paracetamol', 1);

/*ERecipes*/
insert into erecipe (prescription_date, dispensing_date, state, code, total_price, total_price_with_discount, patient_id, pharmacy_id) values (1616846400000, 1620812258708, 'PROCESSED', 'EP0000', 1228.0, 1228.0, 1, 1);

/*ERecipes - ERecipes Items*/
insert into erecipe_e_recipe_items (erecipe_id, e_recipe_items_id) values (1, 1);
insert into erecipe_e_recipe_items (erecipe_id, e_recipe_items_id) values (1, 2);

/*Advertisements*/
insert into advertisement (advertisement_text, discount_percent, start_date, end_date, type, pharmacy_id, medicine_item_id)
values ('Ne propustite priliku!', 5.0, 1619953200000, 1626780633000, 'SALE', 1, 1);
insert into advertisement (advertisement_text, discount_percent, start_date, end_date, type, pharmacy_id, medicine_item_id)
values ('Odlicna akcija!!!', 30.0, 1619953200000, 1626780633000, 'SALE', 2, 6);
insert into advertisement (advertisement_text, discount_percent, start_date, end_date, type, pharmacy_id, medicine_item_id)
values ('Brufen po najnizoj ceni!!!', 50.0, 1619953200000, 1626780633000, 'SALE', 3, 10);

/*MedicineReservations*/
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1618666801000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RESERVED', 1, 1, 450);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1618444800000, 1618666801000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3455', 'RESERVED', 1, 1, 500);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1618531200000, 1618666801000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3456', 'RESERVED', 1, 1, 1000);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1618531200000, 1618666801000, 'f8c3de44-1fea-4d7c-a8b0-29f63c4c3456', 'RECEIVED', 1, 1, 249);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1618489800000, 1617714000000, 'f8c3de44-1fea-4d7c-a8b0-29f63c4c6666', 'RECEIVED', 2, 1, 789);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1618920720000, 1617717600000, 'f8c3de44-1fea-4d7c-a8b0-29f63c4c7777', 'RECEIVED', 3, 2, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1619352720000, 1617804000000, 'f8c3de44-1fea-4d7c-a8b0-29f63c4c8888', 'RECEIVED', 4, 3, 467);


-- Rezervacije za  izvestaj
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1594397419000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1594397419000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1594397419000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1597075819000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1597075819000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1597075819000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1597075819000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1602346219000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1607616619000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1607616619000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1607616619000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1575994219000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1575994219000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1575994219000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1575994219000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1607616619000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1607616619000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1607616619000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1575994219000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1575994219000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1575994219000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1575994219000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1607616619000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1607616619000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1607616619000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1575994219000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1525968619000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1525968619000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1525968619000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1512922219000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1512922219000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1512922219000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1544458219000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1544458219000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1618071019000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1618071019000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1618071019000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1618071019000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1618071019000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1620663019000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1620663019000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1620663019000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1620663019000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1620663019000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1620663019000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1620663019000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1612973419000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1612973419000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1612973419000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1618071019000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1618071019000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1618071019000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id, price)
values (1626307200000, 1618071019000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RECEIVED', 1, 1, 459);

/*Patient - MedicineReservations*/
insert into patient_medicine_reservation (patient_id, medicine_reservation_id) values (1, 1);
insert into patient_medicine_reservation (patient_id, medicine_reservation_id) values (1, 2);
insert into patient_medicine_reservation (patient_id, medicine_reservation_id) values (3, 3);
insert into patient_medicine_reservation (patient_id, medicine_reservation_id) values (1, 4);
insert into patient_medicine_reservation (patient_id, medicine_reservation_id) values (1, 5);
insert into patient_medicine_reservation (patient_id, medicine_reservation_id) values (1, 6);
insert into patient_medicine_reservation (patient_id, medicine_reservation_id) values (3, 7);

/*Medicine - Alternative Medicines*/
insert into medicine_alternative_medicine (medicine_id, alternative_medicine_id) values (1, 2);
insert into medicine_alternative_medicine (medicine_id, alternative_medicine_id) values (2, 1);
insert into medicine_alternative_medicine (medicine_id, alternative_medicine_id) values (3, 4);
insert into medicine_alternative_medicine (medicine_id, alternative_medicine_id) values (4, 5);
insert into medicine_alternative_medicine (medicine_id, alternative_medicine_id) values (1, 5);
insert into medicine_alternative_medicine (medicine_id, alternative_medicine_id) values (5, 1);
insert into medicine_alternative_medicine (medicine_id, alternative_medicine_id) values (5, 2);
insert into medicine_alternative_medicine (medicine_id, alternative_medicine_id) values (5, 3);

/*Pharmacy - PharmacyAdmin*/
insert into pharmacy_admins (pharmacy_id, admins_id) values (1, 9);

/*Inquiries*/
insert into inquiry (pharmacy_id, worker_id, medicine_items_id, is_active, date) values (1, 6, 7, true, 1619974515000);
insert into inquiry (pharmacy_id, worker_id, medicine_items_id, is_active, date) values (1, 6, 1, false , 1617382515000);
insert into inquiry (pharmacy_id, worker_id, medicine_items_id, is_active, date) values (1, 7, 2, false, 1617382515000);
insert into inquiry (pharmacy_id, worker_id, medicine_items_id, is_active, date) values (1, 7, 1, false, 1617382515000);
insert into inquiry (pharmacy_id, worker_id, medicine_items_id, is_active, date) values (1, 6, 2, false, 1617382515000);
