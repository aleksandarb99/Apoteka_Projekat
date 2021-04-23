
/*Locations*/
insert into location (longitude, latitude) values (19.833549, 45.267136);       /*Novi Sad*/
insert into location (longitude, latitude) values (20.43157, 44.80886);       /*Beograd*/

/*Addresses*/
insert into address (street, city, country, location_id) values ('Omladinska 19', 'Novi Sad', 'Srbija', 1);
insert into address (street, city, country, location_id) values ('Jevrejska 4', 'Beograd', 'Srbija', 2);
insert into address (street, city, country, location_id) values ('Bulevar kralja Aleksandra 37', 'Beograd', 'Srbija', 2);
insert into address (street, city, country, location_id) values ('Fruskogorksa 14', 'Novi Sad', 'Srbija', 1);

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
                      values ('Brufen', 'M01AE01', 'Nema podataka.',
                              'Moze doci do ozbiljnih problema, ako se ne postuje doza.', 2, 'REQUIRED', 'Za otklanjanje simptoma prehlade i gripa kao što su povišena telesna temperatura i bolovi povezani sa prehladom.',
                              4.2, 10, 1, 1, 1);

insert into medicine (name, code, content, side_effects, daily_intake, recipe_required, additional_notes, avg_grade,
                      points, medicine_type_id, medicine_form_id, manufacturer_id)
                      values ('Paracetamol', 'N02BE01', 'Nema podataka.',
                            'Mucnina, povracanje, ...', 100, 'NOTREQUIRED', 'Takođe se preporučuje primena nakon imunizacije u cilju smanjenja povišene telesne temperature.',
                            4.7, 7, 1, 2, 2);

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

/*Medicine items*/
insert into medicine_item (amount, medicine_id) values (18, 1);
insert into medicine_item (amount, medicine_id) values (23, 2);
insert into medicine_item (amount, medicine_id) values (14, 3);
insert into medicine_item (amount, medicine_id) values (55, 4);
insert into medicine_item (amount, medicine_id) values (67, 5);
insert into medicine_item (amount, medicine_id) values (30, 1);

/*Supplier items*/
insert into supplier_item (amount, medicine_id) values (30, 1);
insert into supplier_item (amount, medicine_id) values (20, 2);
insert into supplier_item (amount, medicine_id) values (37, 3);
insert into supplier_item (amount, medicine_id) values (15, 4);
insert into supplier_item (amount, medicine_id) values (100, 4);

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
insert into order_item (amount, medicine_id) values (130, 4);

/*WorkCalendars*/
insert into work_calendar (id) values (1);
insert into work_calendar (id) values (2);
insert into work_calendar (id) values (3);

/*Price Lists*/
insert into price_list (id) values (1);
insert into price_list (id) values (2);
insert into price_list (id) values (3);

/*PriceList - MedicineItem*/
insert into price_list_medicine_items (price_list_id, medicine_items_id) values (1, 1);
insert into price_list_medicine_items (price_list_id, medicine_items_id) values (1, 2);
insert into price_list_medicine_items (price_list_id, medicine_items_id) values (2, 3);
insert into price_list_medicine_items (price_list_id, medicine_items_id) values (3, 5);
insert into price_list_medicine_items (price_list_id, medicine_items_id) values (3, 4);
insert into price_list_medicine_items (price_list_id, medicine_items_id) values (2, 6);


/*Workplace - WorkDays*/

/*WorkDays*/
insert into work_day (weekday, start_time, end_time) values ('MON', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('TUE', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('WED', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('THU', 7, 15);
insert into work_day (weekday, start_time, end_time) values ('FRI', 7, 15);
insert into work_day (weekday, start_time, end_time) values ('MON', 8, 16);
insert into work_day (weekday, start_time, end_time) values ('TUE', 10, 17);
insert into work_day (weekday, start_time, end_time) values ('THU', 10, 17);
insert into work_day (weekday, start_time, end_time) values ('FRI', 8, 16);

/*MyUsers*/
insert into my_user (password, first_name, last_name, email, telephone, user_type, address_id, is_password_changed, is_email_verified)
                    values ('$2y$12$qPMQwhqxS29Pn4bdnxtVROQXoFAHvLHpXfcWN70Ib8TASAzyN3nfq',
                            'Petar', 'Markovic', 'perazdera@gmail.com', '0642312343', 'PATIENT', 4, false, true);
insert into my_user (password, first_name, last_name, email, telephone, user_type, address_id, is_password_changed, is_email_verified)
                    values ('$2y$12$rjq.xA4OJ1hVOuaOEYhm2ugrUnh2a0Z/SW4fxBAE4/7PKrjWnDeOG',
                            'Mitar', 'Zivkovic', 'mitazita87@gmail.com', '06433332343', 'PATIENT', 4, false, false);
insert into my_user (password, first_name, last_name, email, telephone, user_type, address_id, is_password_changed, is_email_verified)
                    values ('$2y$12$RQzDkiUka7F5Ao1J14ATE.yWIbM6/JDHz0wAoRVbP0f0IfvmZFf0m',
                            'Djura', 'Djuraa', 'djule@gmail.com', '06433332341', 'PATIENT', 4, false, true);
insert into my_user (password, first_name, last_name, email, telephone, user_type, address_id, is_password_changed, is_email_verified)
                    values ('$2y$12$2qGKnufjF6BUB8JssWuxZe5V3yhnhThDNG8ojyvzqNYnzFmzx1kzK',
                            'Zdravko', 'Mitic', 'zlegenda78@gmail.com', '06233552343', 'SUPPLIER', 3, false, false);
insert into my_user (password, first_name, last_name, email, telephone, user_type, address_id, is_password_changed, is_email_verified)
                    values ('$2y$12$Oh4n4xXU5.2JykOL1DqK9uynsiTlFQgT2S54In0bRL4311R/zhoBq',
                            'Slavko', 'Vasic', 'slavisa@gmail.com', '0632232343', 'SUPPLIER', 3, false, true);
insert into my_user (password, first_name, last_name, email, telephone, user_type, address_id, is_password_changed, is_email_verified)
                    values ('$2y$12$QUxaJq2WEdqSwPxT.BePtuD9xI1S74agszK6CR.rN20BpZhbhleAG',
                            'Nebojsa', 'Radovanovic', 'nebojsa@gmail.com', '0637732343', 'PHARMACIST', 3, false, false);
insert into my_user (password, first_name, last_name, email, telephone, user_type, address_id, is_password_changed, is_email_verified)
                    values ('$2y$12$.9pfXfmpWsXqxlBiaxVs7e8zu2i4C.qTzDClIov4KcPaxRBEwSrt2',
                            'Marko', 'Maric', 'marcius@gmail.com', '0634632343', 'DERMATOLOGIST', 3, false, true);
insert into my_user (password, first_name, last_name, email, telephone, user_type, address_id, is_password_changed, is_email_verified)
                    values ('$2y$12$tPT/EHBxszNtPA17Hu9p4OgHuZwJZMsrhT5.jYuBB6arrpaPG5gx6',
                            'Dusko', 'Dragic', 'duskousko@gmail.com', '0628832343', 'ADMIN', 3, false, true);
insert into my_user (password, first_name, last_name, email, telephone, user_type, address_id, is_password_changed, is_email_verified)
                    values ('$2y$12$zMWFTm7cC3JYZkCn35bG8.pr9jHaF4Yzz5vftYC7Ggnl.qFuep0uW',
                            'Branko', 'Krasic', 'branimirko@gmail.com', '0658852343', 'PHARMACY_ADMIN', 3, false, true);
insert into my_user (password, first_name, last_name, email, telephone, user_type, address_id, is_password_changed, is_email_verified)
                    values ('$2y$12$RQzDkiUka7F5Ao1J14ATE.yWIbM6/JDHz0wAoRVbP0f0IfvmZFf0m',
                            'Djura', 'Djuraa', 'djule22@gmail.com', '06430032341', 'PATIENT', 4, false, true);
insert into my_user (password, first_name, last_name, email, telephone, user_type, address_id, is_password_changed, is_email_verified)
                    values ('$2y$12$RQzDkiUka7F5Ao1J14ATE.yWIbM6/JDHz0wAoRVbP0f0IfvmZFf0m',
                            'Marko', 'Markovic', 'djule00@gmail.com', '06930032341', 'PATIENT', 4, false, true);
insert into my_user (password, first_name, last_name, email, telephone, user_type, address_id, is_password_changed, is_email_verified)
                    values ('$2y$12$RQzDkiUka7F5Ao1J14ATE.yWIbM6/JDHz0wAoRVbP0f0IfvmZFf0m',
                            'Petar', 'Peric', 'djule1@gmail.com', '02430032341', 'PATIENT', 4, false, true);
insert into my_user (password, first_name, last_name, email, telephone, user_type, address_id, is_password_changed, is_email_verified)
                    values ('$2y$12$RQzDkiUka7F5Ao1J14ATE.yWIbM6/JDHz0wAoRVbP0f0IfvmZFf0m',
                            'Carli', 'Sin', 'djule009@gmail.com', '08430032341', 'PATIENT', 4, false, true);

/*Pharmacy Workers*/
insert into pharmacy_worker (avg_grade, workcalendar_id, id) values (4.9, 1, 5);
insert into pharmacy_worker (avg_grade, workcalendar_id, id) values (4.8, 2, 6);
insert into pharmacy_worker (avg_grade, workcalendar_id, id) values (4.8, 1, 7);

/*Patients*/
insert into patient (points, penalties, id) values (50, 2, 1);
insert into patient (points, penalties, id) values (43, 0, 2);
insert into patient (points, penalties, id) values (13, 0, 3);
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
insert into supplier (id) values (3);
insert into supplier (id) values (4);

/*Supplier - supplier items*/
insert into supplier_supplier_items (supplier_id, supplier_items_id) values (3, 1);
insert into supplier_supplier_items (supplier_id, supplier_items_id) values (3, 4);
insert into supplier_supplier_items (supplier_id, supplier_items_id) values (4, 2);
insert into supplier_supplier_items (supplier_id, supplier_items_id) values (4, 3);
insert into supplier_supplier_items (supplier_id, supplier_items_id) values (4, 5);

/*Ratings*/
insert into rating (grade, graded_type, gradedId, date, patient_id) values (4, 'MEDICINE', 1, 1616510769000, 1);
insert into rating (grade, graded_type, gradedId, date, patient_id) values (5, 'MEDICINE', 3, 1616586369000, 2);

/*RankingCategory*/
insert into ranking_category (name, points_required, discount) values ('Bronza', 50, 5.0);
insert into ranking_category (name, points_required, discount) values ('Srebro', 100, 15.0);
insert into ranking_category (name, points_required, discount) values ('Zlato', 200, 30.0);

/*ERecipe Items*/
insert into erecipe_item (medicine_code, medicine_name, quantity) values ('M01AE01', 'Brufen', 2);
insert into erecipe_item (medicine_code, medicine_name, quantity) values ('N02BE01', 'Paracetamol', 1);

/*ERecipes*/
insert into erecipe (dispensing_date, state, code, patient_id) values (1616846400000, 'NEW', '1234', 1);

/*ERecipes - ERecipes Items*/
insert into erecipe_e_recipe_items (erecipe_id, e_recipe_items_id) values (1, 1);
insert into erecipe_e_recipe_items (erecipe_id, e_recipe_items_id) values (1, 2);

/*Medicine Prices*/
insert into medicine_price (price, start_date) values (449, 1618077600000);
insert into medicine_price (price, start_date) values (489, 1618160400000);
insert into medicine_price (price, start_date) values (250, 1615482000000);
insert into medicine_price (price, start_date) values (670, 1613062800000);
insert into medicine_price (price, start_date) values (1200, 1610384400000);
insert into medicine_price (price, start_date) values (470, 1578769200000);
-- insert into medicine_price (price, start_date) values (879, 1616241600000;

/* MedicineItem - MedicinePrices*/
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (1, 1);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (1, 2);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (2, 3);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (3, 4);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (4, 5);
insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (6, 6);
-- insert into medicine_item_medicine_prices (medicine_item_id, medicine_prices_id) values (5, 6);

/*Complaints*/
insert into complaint (content, complaint_on, complaint_on_id, type, date, patient_id)
                    values ('Nije ispostovan dogovor, trazim da bude sankcionisan.', 'Nebojsa Radovanovic', 5, 'PHARMACIST', 1616587200000, 1);
insert into complaint (content, complaint_on, complaint_on_id, type, date, patient_id)
                    values ('Neprikladno ponasanje.', 'Marko Maric', 6, 'DERMATOLOGIST', 1616587203000, 2);

/*Complaint Response*/
insert into complaint_response (response_text, date, complaint_id, user_id)
                    values ('Bice sakcionisan!', 1616673600000, 1, 7);
insert into complaint_response (response_text, date, complaint_id, user_id)
                    values ('Uz duzno postovanje, bice preuzete najstroze mere!', 1616673602000, 2, 7);

/*Pharmacies*/
insert into pharmacy (avg_grade, description, name, address_id, price_list_id)
                    values (4.4, 'Najjaca apoteka u gradu.', 'Zelena Apoteka', 1, 1);
insert into pharmacy (avg_grade, description, name, address_id, price_list_id)
                    values (4.8, 'Poverenje, sigurnost i dostupnost su, već skoro 30 godina, glavna obeležja Apotekarske ustanove Jankovic.', 'Apoteka Jankovic', 1, 2);
insert into pharmacy (avg_grade, description, name, address_id, price_list_id)
                    values (2.9, 'Poručite sve što vam je potrebno na vašu kućnu adresu. Imas veliki izbor i sjajanu cenu samo na BENU online shop-u.', 'Benu Apoteka', 2, 3);

/*Pharmacy - Subscribers*/
insert into pharmacy_subscribers (pharmacy_id, subscribers_id) values (1, 1);
insert into pharmacy_subscribers (pharmacy_id, subscribers_id) values (3, 2);

/*Workplaces*/
insert into workplace (pharmacy_id, worker_id) values (1, 5);
insert into workplace (pharmacy_id, worker_id) values (1, 7);
insert into workplace (pharmacy_id, worker_id) values (2, 6);
insert into workplace (pharmacy_id, worker_id) values (3, 6);

/*Workplace - WorkDays*/
insert into workplace_work_days (workplace_id, work_days_id) values (1, 1);
insert into workplace_work_days (workplace_id, work_days_id) values (1, 2);
insert into workplace_work_days (workplace_id, work_days_id) values (2, 3);
insert into workplace_work_days (workplace_id, work_days_id) values (3, 4);
insert into workplace_work_days (workplace_id, work_days_id) values (3, 5);
insert into workplace_work_days (workplace_id, work_days_id) values (2, 6);
insert into workplace_work_days (workplace_id, work_days_id) values (2, 7);
insert into workplace_work_days (workplace_id, work_days_id) values (2, 8);
insert into workplace_work_days (workplace_id, work_days_id) values (2, 9);


/*Appointments*/
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id)
                    values ('FINISHED', 'CONSULTATION', 2, 1637354000000, 1637354003000, 'Placanje u kesu.', 1500, 1, 1, 5);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id)
                    values ('RESERVED', 'CHECKUP', 2, 1617354000000, 1617361200000, 'Placanje u unapred.', 1800, 2, 2, 6);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id)
                    values ('RESERVED', 'CONSULTATION', 2, 1617800000000, 1617800300000, 'Placanje u unapred.', 1800, 1, 1, 5);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id)
                    values ('FINISHED', 'CONSULTATION', 2, 1617800300000, 1617801300000, 'Placanje u unapred.', 1800, 3, 1, 5);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id)
                    values ('RESERVED', 'CHECKUP', 10, 1617804000000, 1617804000000, 'Placanje u unapred.', 1800, 3, 1, 7);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id)
                    values ('RESERVED', 'CHECKUP', 12, 1617800300000, 1617801300000, 'Placanje u unapred 2.', 1700, 2, 1, 7);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id)
                    values ('FINISHED', 'CONSULTATION', 2, 1618209900000, 1618217100000, 'Placanje u unapred.', 1800, 10, 1, 5);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id)
                    values ('FINISHED', 'CONSULTATION', 2, 1619084700000, 1619091900000, 'Placanje u unapred.', 1800, 11, 1, 5);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id)
                    values ('FINISHED', 'CONSULTATION', 2, 1623325500000, 1623330000000, 'Placanje u unapred.', 1800, 12, 1, 5);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id)
                    values ('FINISHED', 'CONSULTATION', 2, 1623744000000, 1623751200000, 'Placanje u unapred.', 1800, 13, 1, 5);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id)                    
                    values ('RESERVED', 'CONSULTATION', 2, 1618840200000, 1618840230000, 'Placanje u unapred.', 1800, 1, 1, 5);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id)
                    values ('RESERVED', 'CONSULTATION', 2, 1619937522009, 1619937622009, 'Placanje u unapred.', 1800, 1, 1, 5);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id)
values ('RESERVED', 'CONSULTATION', 2, 1618899780000, 1618899900000, 'Placanje u unapred.', 1800, 1, 1, 5);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id)
values ('RESERVED', 'CONSULTATION', 2, 1618899960000, 1618900020000, 'Placanje u unapred.', 1800, 1, 1, 5);
insert into appointment (appointment_state, appointment_type, duration, start_time, end_time, info, price, patient_id, pharmacy_id, worker_id)
values ('RESERVED', 'CONSULTATION', 2, 1618900080000, 1618900140000, 'Placanje u unapred.', 1800, 1, 1, 5);

/*WorkCalendar - Appointments*/
insert into work_calendar_appointments (work_calendar_id, appointments_id) values (1, 1);
insert into work_calendar_appointments (work_calendar_id, appointments_id) values (2, 2);

/*RequestForHoliday*/
insert into request_for_holiday (absence_type, decline_text, start_date, end_date, request_state, pharmacy_worker_id)
                    values ('VACATION', 'Odobreno.', 1619953200000, 1620817200000, 'ACCEPTED', 5);
insert into request_for_holiday (absence_type, decline_text, start_date, end_date, request_state, pharmacy_worker_id)
                    values ('LEAVE', 'Nemoguce da sada dobijete.', 1619953200000, 1620817200000, 'CANCELLED', 6);

/*MyOrders*/
insert into my_order (deadline, pharmacy_id) values (1620817200000, 1);
insert into my_order (deadline, pharmacy_id) values (1620903600000, 2);
insert into my_order (deadline, pharmacy_id) values (1620817200000, 1);
insert into my_order (deadline, pharmacy_id) values (1620817200000, 1);
insert into my_order (deadline, pharmacy_id) values (1620817200000, 1);
insert into my_order (deadline, pharmacy_id) values (1620817200000, 1);
insert into my_order (deadline, pharmacy_id) values (1613411940000, 1);

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

/*Offers*/
insert into offer (delivery_date, offer_state, price, order_id)
                    values (1620730800000, 'ACCEPTED', 2300, 1);
insert into offer (delivery_date, offer_state, price, order_id)
                    values (1620730800000, 'PENDING', 2100, 1);
insert into offer (delivery_date, offer_state, price, order_id)
                    values (1620730800000, 'ACCEPTED', 3560, 2);

/*Supplier - Offers*/
insert into supplier_offers (supplier_id, offers_id) values (3, 1);
insert into supplier_offers (supplier_id, offers_id) values (4, 2);
insert into supplier_offers (supplier_id, offers_id) values (3, 3);

/*Advertisements*/
insert into advertisement (advertisement_text, discount_percent, start_date, end_date, type, pharmacy_id)
                    values ('Ne propustite priliku!', 5.0, 1619953200000, 1620817200000, 'SALE', 1);
insert into advertisement (advertisement_text, discount_percent, start_date, end_date, type, pharmacy_id)
                    values ('Odlicna akcija!!!', 30.0, 1619953200000, 1620817200000, 'SALE', 2);

/*Advertising*/
insert into advertising (advertisement_id, medicine_price_id) values (1, 1);
insert into advertising (advertisement_id, medicine_price_id) values (2, 3);

/*MedicineReservations*/
insert into medicine_reservation (pickup_date, reservation_date, reservationid, state, medicine_item_id, pharmacy_id)
                    values (1618839601000, 1618666801000, 'f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454', 'RESERVED', 1, 1);

/*Patient - MedicineReservations*/
insert into patient_medicine_reservation (patient_id, medicine_reservation_id) values (3, 1);

/*Medicine - Alternative Medicines*/
insert into medicine_alternative_medicine (medicine_id, alternative_medicine_id) values (1, 2);
insert into medicine_alternative_medicine (medicine_id, alternative_medicine_id) values (2, 1);
insert into medicine_alternative_medicine (medicine_id, alternative_medicine_id) values (3, 4);
insert into medicine_alternative_medicine (medicine_id, alternative_medicine_id) values (4, 5);
insert into medicine_alternative_medicine (medicine_id, alternative_medicine_id) values (1, 5);
