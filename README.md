# CampusFix AI v3 — React + Vite + Spring Boot + MySQL + AI Agents

This version keeps the **CampusFix AI v2 campus architecture** and the original participant's core workflow, but changes the UI stack to the requested learning stack: **React + HTML/JSX + Vite + CSS**.

## Four dashboards
1. **University** — university location reporting and the University Incident Forum.
2. **Hostel** — hostel location reporting and the Hostel Incident Forum.
3. **Specialists** — professional directory, workload and assigned incident queue.
4. **AI / Admin Console** — cross-campus orchestration, agent trace, verification and workload.

### Important forum rule
Facilities such as **Lab Equipment, Roads & Civil, Water Supply, Gym, Dining Hall, etc. are not modeled as physical hostel/university buildings**. They are separate problem forums. A report still has a physical location plus a forum.

## Campus architecture
### Hostels
- B3 → Heroes, Winners, Lords, Balaji, International, National
- Prince → A–F
- Leaders → A–F
- Kings → A–F
- Queens → Annexure 1–3, Padmavati
- Vinayakani → A–F
- International Guest House → Single AC Block

### University
- Blocks 4–33
- Floor and room/space are collected separately.
- University forums include Network, Electrical, AC/Cooling, Plumbing, Security, Furniture, Cleanliness, Roads & Civil, Lab Equipment, AV/Seminar Systems and General University.

## AI agent path
`React UI → REST API → Agent Orchestrator → Intake → Correlation → Investigation → Root-Cause → Resource → Professional Matching → Planning → Action → Verification → MySQL`

The backend includes an **AI provider adapter**. Use `AI_PROVIDER=mock` for deterministic local testing. For hackathon experiments, use `AI_PROVIDER=openai` or `AI_PROVIDER=gemini` and set the corresponding API key. The external AI is currently a copilot/risk-check layer while deterministic campus rules guarantee safe routing and a working demo. This can be upgraded to structured JSON tool-calling for every agent without changing the React UI or database.

> A ChatGPT/consumer subscription and an API key are separate products in practice; put an actual OpenAI API key in `OPENAI_API_KEY` if you want to test the OpenAI endpoint. Gemini requires a Gemini API key in `GEMINI_API_KEY`.

## Run
### 1. Start MySQL
From the project root:
```powershell
docker compose up -d mysql
```
Or install MySQL 8.x locally and create a `campusfix` database/user.

### 2. Start backend
```powershell
cd backend
mvn clean spring-boot:run
```
Backend: `http://localhost:8080`

### 3. Start React/Vite frontend
Open another terminal:
```powershell
cd frontend
npm install
npm run dev
```
Frontend: `http://localhost:5173`

### 4. AI provider (optional)
PowerShell examples:
```powershell
$env:AI_PROVIDER="openai"
$env:OPENAI_API_KEY="YOUR_API_KEY"
mvn spring-boot:run
```
Or:
```powershell
$env:AI_PROVIDER="gemini"
$env:GEMINI_API_KEY="YOUR_API_KEY"
mvn spring-boot:run
```

## Database
MySQL is used because it is straightforward for a hackathon, easy to inspect, and works naturally with Spring Data JPA. Tables are generated/updated by Hibernate on startup.

Core entities: `reports`, `incidents`, `professionals`, `agent_logs`.

## Safety / demo behavior
- No real university professional identities are claimed; seeded names are demo roles.
- AI cannot directly perform irreversible/high-impact actions. The Action Agent records a pending action and verification is required.
- Verification failure is represented by reopening the incident for another investigation cycle.
