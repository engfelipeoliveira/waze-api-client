# Build
### DEV
```console
mvn clean install -Pdev
```

### PROD
```console
mvn clean install -Ppro
```

Obter o arquivo waze-api-client-0.0.1-SNAPSHOT.jar no diretorio /target

# Deploy Docker

### Instalacao
- Linux : Instalar Docker e Docker-Compose.
- Windows : Instalar Docker Desktop.

### Etapas de Deploy
```console
docker-compose down
docker-compose up -d --build
```

### Listar containers
```console
docker ps
```

### Conectar no container
```console
docker exec -it waze-api-client sh
```

### Logs
```console
docker logs -f waze-api-client
```