27.12.2024
*  Inceput schelet proiect

05.01.2025
*  Intors din vacanta + aproape terminat schelet proiect si adaugat prima functionalitate
*  JavaFX moartea pasiunii + adaugat loginController + logica la el

07.01.2025
* Rezolvat problema JavaFX la rulare + adaugat logica pentru login dupa ce fac password encoding/decoding

08.01.2025
* Modificat putin GUI + adaugat GUI pentru register
* Adaugat posibilitatea de a coda parola (decodarea o sa fie amuzanta)
* Adaugat controllere pentru inregistrarea studentilor/profesorilor si de asemenea logica pentru scrierea in fisier a noilor studenti/profesori
* Momentan Controllerul pentru profesor este bugged dar o sa fie fixat ori azi ori maine --REZOLVAT
* Adaugat functionalitate FileDisplay
* Adaugat CreateCoursesData() in FileDataManager

09.01.2025
* Trecut pe citirea din fisier pentru login si scrierea in fisier pentru register
* Encodingul si decodingul parolei este functional (momentan toata lumea are parola test pentru simplicitate)

10-11.01.2025
* Cel mai puternic update de pana acum
* Modificat clasa Curs deoarece lipsea atributul anCurs
* Modificat functia de createCoursesData pentru a include si anul cursului si pentru a putea fi folosita in alte clase fara probleme
* Modificat clasa LoginController pentru a putea da parse la username-ul studentului pentru a-i afla notele, cursurile etc
* Adaugat template pentru thread-uri care v-a fi inclus dupa adaugarea functionalitatii in consola
* Rezolvat bug-uri din clasa Nota
* Adaugat cateva failsafe-uri in pagine de register
* Adaugat pagina de dashboard pentru studenti cu toate functionalitatile necesare
* Adaugat un testCase pentru a verifica citirea corecta a cursurilor in functia de createCoursesData
* Facut putina curatenie de iarna in structura proiectului(nu este gata)
* Majoritatea functiilor au fost create in engleza, pentru simplicitate o sa le redenumesc inainte de finalizarea proiectului in romana

12.01.2025
* Finalizarea proiectului
* Tradus tot textul vizibil in romana(99% acuratete)
* Adaugat threaduri si functionalitatea de a rula aplicatia in consola
* Adaugat butoane de logout
* BUGURI CUNOSCUTE: - Teoretic nu mai sunt buguri cunoscute.

13.01.2025
* Adaugat check-uri sa nu fie user identici, sa nu fie cu cifre numele sau cu litere anul si grupa.
* Adaugat ca la inregistrarea unui student, sa i se adauge in fisierul de note o nota de 0 pentru fiecare curs din anul introdus.
* (ISSUE) Studentii creati inainte de schimbare(adica unul singur dar totusi) nu o sa poată avea note, se poate rezolva prin stergerea si inregistrarea din nou a studentului.
* Schimbat logica de notare pentru a edita notele studentilor cu 0 din fisier.
* Facut clasa FileDataManager sa fie singleton pentru a se putea crea instante din aceasta(nu mergea folosita pe amandoua threadurile)
* bis
