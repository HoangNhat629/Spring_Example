#!/bin/zsh
echo $SHELL
echo "Datetime: $(date)"

# Backup database from server
echo "Connect database";
hostname=127.0.0.1
user=root
password=password
database=test_db

echo "hostname: ${hostname}"
echo "user: ${user}"
echo "password: ${password}"
echo "database: ${database}"

# Execute backup database
mysqldump -h ${hostname} -u ${user} -p${password} ${database} | gzip > feednexa-$(date +%F).sql.gz

sleep 1

# Check file exists or not
if [ -e feednexa-$(date +%F).sql.gz ]
then
  echo "Backup successfully"
else
  echo "Backup was failure"
fi

sleep 1

# Connect remote server
sshpass -p '88Q7M3PTwUL5fM' scp feednexa-$(date +%F).sql.gz root@66.94.125.167:/backup/feednexa-$(date +%F).sql.gz

sleep 1

# Remove file backup
rm -rf feednexa-$(date +%F).sql.gz

exit