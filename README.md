# WT-todo (Backend)

Spring Boot Backend fuer die WT-Todo App.

## Lokales Starten

### Voraussetzungen
- Java 21
- Postgres (oder H2 fuer Tests)

### Umgebungsvariablen

**Pflicht** (z.B. in Render als Environment Variables setzen):
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

**Optional / empfohlen**
- `FRONTEND_URL` (Default: die Render-Frontend-URL aus `application.properties`)
- `JWT_SECRET` (wichtig: mind. 32 Zeichen empfehlenswert)
- `JWT_EXPIRATION_SECONDS` (Default: 604800 = 7 Tage)

### Start
```bash
./gradlew bootRun
```

Backend laeuft standardmaessig auf `http://localhost:8080`.

## API

### Auth
- `POST /api/v1/auth/register`  Body: `{ "username": "...", "password": "..." }`
- `POST /api/v1/auth/login`     Body: `{ "username": "...", "password": "..." }`

Antwort:
```json
{ "token": "<jwt>", "username": "<name>" }
```

Der JWT muss bei allen Todo-Requests als Header mitgeschickt werden:
`Authorization: Bearer <jwt>`

### Todos (authentifiziert)
- `GET /api/v1/todos`
- `POST /api/v1/todos`
- `PUT /api/v1/todos/{id}`
- `PATCH /api/v1/todos/{id}/done`
- `DELETE /api/v1/todos/{id}`
- `DELETE /api/v1/todos/completed`

Alle Todos sind pro User getrennt.

## Continuous Deployment (Render Auto Deploy)

Damit CD erfuellt ist (jeder Push auf `main` deployed automatisch):
1. Render Service oeffnen (**Backend**)
2. **Settings**
3. **Deploys**
4. **Auto Deploy**: **On**
5. **Branch**: `main`

Dasselbe auch fuer den **Frontend**-Service einstellen.
