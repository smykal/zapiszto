1. Uruchomienie lokalnie w docker: 
    docker-compose: 
        backend
        db
    aby uruchomić lokalnie z dockerem musi być uzupełniony plik .env_render o zmienne srodowiskowe
    uruchamiamy poleceniem:
    `docker-compose --env-file .env_render up -d`

2. Uruchomienie lokalnie z intellij'a
    zmienne srodowiskowe należy ustawić w panelu konfiguracyjnym `RUN/DEBUG Configurations`
    analogiczne jak w pliku .env_reader tylko wartosci dla local
    uruchamiamy poleceniem:
    `mvn clean install -Dspring.datasource.password=12345Mateusz -Dspring.datasource.username=postgres -Dencryption.key=12345NIKULSHYNA! -Dencryption.initVector=12345NIKULSHYNA! -Dspring.mail.password="itdr pwoc qhdv zcgo" -Dspring.mail.username=presq333@gmail.com -DzapiszTo.app.jwtSecret="======================zapiszTo==========================="`

3. Uruchomienie na renderze
    render wymaga ustawienia zmiennych srodowiskowych w konfiguracji web-service
    na renderze uruchamia się wszystko co wleci na mastera
    uruchamiane poleceniem:
    `src/main/resources/docker/render/Dockerfile`

4. CI/CD
2. Sentry
3. Postgres
4. MailSender
4. Liquibase:
   -Dliquibase.clearCheckSums=true za użyciem tej flagi można dokonać zmian w historycznych changesetach