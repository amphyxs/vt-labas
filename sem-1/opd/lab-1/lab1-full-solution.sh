# Task 1
mkdir lab0
cd lab0
touch charmander3
mkdir chimecho6
cd chimecho6
touch jellicent
touch spoink
mkdir bellsprout
cd ..
mkdir drowzee7
cd drowzee7
mkdir beedrill
mkdir ledyba
touch drilbur
mkdir drowzee
cd ..
mkdir omastar5
cd omastar5
mkdir krookodile
touch simisage
mkdir magmortar
mkdir buizel
cd ..
touch swinub2
touch wartortle7
echo "charmander3:"
cat >charmander3
cd chimecho6
echo "jellicent:"
cat >jellicent
echo "spoink:"
cat >spoink
cd ..
cd drowzee7
echo "drilbur:"
cat >drilbur
cd ..
cd omastar5
echo "simisage:"
cat >simisage
cd ..
echo "swinub2:"
cat >swinub2
echo "wartotortle7:"
cat >wartortle7
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
# Task 3
chmod 777 drowzee7
chmod 777 swinub2 
cp swinub2 ./drowzee7/drowzee
chmod 004 swinub2  
chmod 330 drowzee7
cat chimecho6/spoink omastar5/simisage | cat >swinub2_13
ln wartortle7 omastar5/simisagewartortle
chmod 777 swinub2 
cat swinub2 | cat >drowzee7/drilburswinub
chmod 004 swinub2  
ln -s drowzee7 Copy_94
cp -r chimecho6 omastar5/magmortar
chmod 777 chimecho6
ln -s wartortle7 chimecho6/spoinkwartortle
chmod 550 chimecho6
# Task 4
echo "Task 4.1 output:"
touch baobab
cat ../templates/baobab>baobab
touch omastar5/bulbazavr
cat ../templates/bulbazavr>omastar5/bulbazavr
wc -l b* */b* */*/b* 2>/tmp/errors-lab0 | tail -r | tail -n +2 | tail -r | sort -r
echo "Task 4.2 output:"
ls -lR | grep ^-.*be.*$ | sort -nk 2
echo "Task 4.3 output:"
cat *t */*t */*/*t 2>&1 | sort -r
echo "Task 4.4 output:"
ls -ltr *t */*t */*/*t 2>/tmp/errors-lab0 | grep ^-.*t$
echo "Task 4.5 output:"
cat swinub2 2>&1 | grep -in NE
echo "Task 4.6 output:"
ls -ltR 2>/dev/null | grep ^-.*be.*$ | head -n 3
# Task 5
rm wartortle7
rm omastar5/simisage
chmod 777 chimecho6
rm chimecho6/spoinkwartort*
rm omastar5/simisagewartort*
rmdir chimecho6
chmod -R 777 chimecho6
rm -dr chimecho6
rmdir omastar5/magmortar
chmod -R 777 omastar5/magmortar
rm -dr omastar5/magmortar
# Task *
cat * */* */*/* 2>/dev/null | grep "\([a-zA-Z]\)\1"
# Symlink
ls -lR | grep "\-> wartortle7"
# Hard link                     
ls -liR | grep "7014238"
