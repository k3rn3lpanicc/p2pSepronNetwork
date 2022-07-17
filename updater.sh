#killall -9 java
kill $(jps | grep MainClient | awk '{print $1}')
#ssh-add ~/ssh_git
git pull;sleep 4;./run.sh
