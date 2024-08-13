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

# Check file exists or not
if [ -e feednexa-$(date +%F).sql.gz ]
then
  echo "Backup successfully"
else
  echo "Backup was failure"
fi

exit