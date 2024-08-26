#!/bin/zsh
echo "$SHELL"
echo "Datetime: $(date)"

# Backup database from server
echo "Connect database";
hostname=127.0.0.1
user=root
password=password
database=test_db
fileName=feednexa-$(date +%F).sql.gz

echo "hostname: $(hostname)"
echo "user: $(user)"
echo "password: $(password)"
echo "database: $(database)"

# Execute backup database
mysqldump -h $hostname -u $user -p$password $database | gzip > "$fileName"

sleep 1

# Check file exists or not
if [ -e "$fileName" ]
then
  echo "Backup file: $fileName"
else
  echo "Backup was failure"
fi

sleep 1

# Install sshpass
# sudo apt-get install sshpass

## VPS
serverHost=root@ip
serverPassword=xxx

# Connect remote server
echo "Begin upload at $(date)"
sshpass -p $serverPassword scp "$fileName" $serverHost:/backup/"$fileName"
echo "Finish upload at $(date)"

sleep 1

# Remove file backup
#rm -rf "$fileName"

exit