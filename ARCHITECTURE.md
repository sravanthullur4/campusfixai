# CampusFix AI v3 Architecture

React/Vite/HTML-like JSX/CSS dashboards
↓
Spring Boot REST API
↓
Agent Orchestrator
↓
Intake → Correlation → Investigation → Root-Cause → Resource → Professional Matching → Planning → Action → Verification
↓
MySQL incident/report/professional/agent-log state

External AI adapter (OpenAI or Gemini) sits beside the orchestrator as an AI reasoning provider. Mock mode keeps the application runnable without keys.

## Why MySQL instead of NoSQL
The workflow needs relationships: many reports can map to one incident; one incident can have one assigned professional; each incident has many agent logs. Relational modeling makes those relationships easy to query and demonstrate.

## Upgrade path
1. Replace rule-based domain classification with structured AI JSON.
2. Let the Correlation Agent retrieve candidate incidents from MySQL and ask the model whether they represent the same root incident.
3. Add tool functions for professional lookup, incident updates and verification evidence.
4. Add a vector store only when semantic retrieval becomes necessary; do not add it for the one-day demo unless needed.
5. Add authentication/roles after the core workflow is stable.
