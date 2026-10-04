# Quick Start (Windows)

### Terminal 1 — MySQL
```powershell
docker compose up -d mysql
```

### Terminal 2 — Spring Boot
```powershell
cd backend
mvn clean spring-boot:run
```

### Terminal 3 — React/Vite
```powershell
cd frontend
npm install
npm run dev
```

Open http://localhost:5173

If you do not want Docker, use a local MySQL 8.x server and set `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` before starting Spring Boot.
