cd ..
call mvn -U -Dmaven.test.skip=true clean package 
cd deploy
call mvn assembly:assembly
@pause
