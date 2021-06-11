/*Medicine types*/
insert into medicine_type (name) values ('Antibiotik');
insert into medicine_type (name) values ('Anestetik');

/*Medicine forms*/
insert into medicine_form (name) values ('Šumeće granule');
insert into medicine_form (name) values ('Sirup');

/*Manufacturers*/
insert into manufacturer (name) values ('ABBVIE S.R.L. - Italija');
insert into manufacturer (name) values ('GALENIKA AD BEOGRAD - Republika Srbija');

/*Medicines*/
insert into medicine (name, code, content, side_effects, daily_intake, recipe_required, additional_notes, avg_grade,
                      points, medicine_type_id, medicine_form_id, manufacturer_id)
                        values ('Brufen', 'M01AE01', 'Najjaci lek za glavu.', 'Glavobolja.', 2, 'REQUIRED', 'Nema', 4.2, 10, 1, 1, 1);

insert into medicine (name, code, content, side_effects, daily_intake, recipe_required, additional_notes, avg_grade,
                      points, medicine_type_id, medicine_form_id, manufacturer_id)
                        values ('Paracetamol', 'M02AE01', 'Nema podataka.', 'Moze doci do ozbiljnih problema.', 2, 'REQUIRED', 'Nema', 4.5, 20, 2, 2, 2);