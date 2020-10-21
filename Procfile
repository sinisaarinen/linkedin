web: java $JAVA_OPTS -Dspring.profiles.active=production -Dserver.port=$PORT -jar target/*.jarweb: gunicorn --preload --workers 1 application:app
