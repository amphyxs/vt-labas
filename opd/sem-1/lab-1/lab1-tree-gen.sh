# Task 1
mkdir lab0
cd lab0
touch charmander3
cat ../templates/charmander3>charmander3
mkdir chimecho6
cd chimecho6
touch jellicent
cat ../../templates/jellicent>jellicent
touch spoink
cat ../../templates/spoink>spoink
mkdir bellsprout
cd ..
mkdir drowzee7
cd drowzee7
mkdir beedrill
mkdir ledyba
touch drilbur
cat ../../templates/drilbur>drilbur
mkdir drowzee
cd ..
mkdir omastar5
cd omastar5
mkdir krookodile
touch simisage
cat ../../templates/simisage>simisage
mkdir magmortar
mkdir buizel
cd ..
touch swinub2
cat ../templates/swinub2>swinub2
touch wartortle7
cat ../templates/wartortle7>wartortle7
# Task 2
chmod u=r charmander3
chmod go= charmander3
chmod 550 chimecho6
chmod 664 chimecho6/jellicent 
chmod 444 chimecho6/spoink
chmod 551 chimecho6/bellsprout
chmod 330 drowzee7
chmod 511 drowzee7/beedrill 
chmod a=wx drowzee7/ledyba
chmod 066 drowzee7/drilbur 
chmod u=rwx drowzee7/drowzee
chmod g=wx drowzee7/drowzee
chmod o=rw drowzee7/drowzee
chmod u=rwx omastar5
chmod go=wx omastar5
chmod u=rwx omastar5/krookodile
chmod g=wx omastar5/krookodile
chmod u=rw omastar5/krookodile
chmod 444 omastar5/simisage
chmod a=rwx omastar5/magmortar
chmod 315 omastar5/buizel
chmod o=r swinub2  
chmod ug= swinub2  
chmod 664 wartortle7