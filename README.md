# Apoteka_Projekat

Aleksandar Buljević, sw25-2018

Darko Tica, sw22-2018

Dejan Todorović, sw17-2018

Jovan Simić, sw26-2018

## Pokretanje projekta

- Projektu se može pristupiti sa linka: https://apotekaprojekat.herokuapp.com/

- Projekat se takođe može pokrenuti sa develop grane (https://github.com/aleksandarb99/Apoteka_Projekat/tree/develop):
  - Kako bi se pokrenula sa develop grane,
potrebno je instalirati npm (može u okviru Node.js) radi pokretanja frontend-a, dok je za
backend potrebna Java-11 kao i maven. 
  - Da bi se buildovao frontend deo aplikacije, potrebno je pozicionirati se u Frontend/pharmacy-front folder, 
  i pokrenuti naredbu npm install.
  - Da bi se buildovao backend deo aplikacije, potrebno je pozicionirati se u Backend/PharmacyProject folder, 
  i buildovati i pokrenuti aplikaciju uz pomoć maven-a pokretanjem komande mvn spring-boot:run.
   - Nakon toga, potrebno je pokrenuti frontend server pozicioniranjem u Frontend/pharmacy-front folder, 
  i pokretanjem naredbe npm start.
  
## Skripta za popunjavanje baze
- Nalazi se u okviru aplikacije, u data-postgres.sql
