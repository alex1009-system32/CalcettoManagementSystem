# Calcetto Management System

A professional JavaFX-based desktop application designed to manage tournaments efficiently. The system handles everything from tournament creation and player registration to automated team generation and match scheduling.

## Features

- **Tournament Management**: Create and track multiple tournaments with specific durations and rules.
- **Player & Team Management**: Register players and automatically generate balanced teams.
- **Automated Matchmaking**: Intelligent algorithms for creating match pairings and managing tournament rounds.
- **Score Tracking**: Real-time point updates and leaderboard management.
- **Modern UI/UX**: Clean, responsive interface built with JavaFX and modular CSS.
- **Persistent Storage**: Robust data management using SQLite and JDBC.

## Tech Stack

- **Language**: Java 21+
- **UI Framework**: JavaFX 21
- **Database**: SQLite with JDBC
- **Build Tool**: Maven
- **Libraries**:
  - `SQLite-JDBC` for database connectivity.
  - `JavaFaker` for Randomly generating Team Names

## Project Structure

```textsrc/main/
src/main/
├── java/ ... /calcettomanagmentsystem/
│   ├── core/
│   │   ├── connection/
│   │   ├── dao/
│   │   │   └── impl/
│   │   ├── model/
│   │   └── repo/
│   │       └── impl/
│   ├── service/
│   │   └── management/
│   └── ui/
│       ├── components/
│       ├── controller/
│       │   ├── components/
│       │   ├── modal/
│       │   └── view/
│       └── modal/
│
└── resources/ ... /calcettomanagmentsystem/
    ├── config/
    ├── css/
    │   ├── components/
    │   ├── modal/
    │   └── view/
    ├── fxml/
    │   ├── components/
    │   ├── modal/
    │   └── view/
    └── schemas/
```

## Project Layers

```mermaid
flowchart TD
    classDef layer fill:#f5f5f5,stroke:#333,stroke-width:2px;
    classDef ui_bg fill:#f5f5dc,stroke:#8b8682,stroke-width:1px;
    classDef service_bg fill:#e8f5e9,stroke:#2e7d32,stroke-width:1px;
    classDef dao_bg fill:#e3f2fd,stroke:#1565c0,stroke-width:1px;
    classDef core fill:#fff3e0,stroke:#ef6c00,stroke-width:1px;
    classDef model fill:#ffffff,stroke:#01579b,stroke-dasharray: 5 5;
    classDef db_node fill:#fff9c4,stroke:#fbc02d,stroke-width:2px;

    subgraph UI_Layer ["UI / Presentation Layer (JavaFX)"]
        APP[App.java]
        NAV[FXMLNavigator.java]
        
        subgraph Views ["FXML & Controllers"]
            direction TB
            V_SELECT[selectTournament-view.fxml]
            V_CREATE[createTournament-view.fxml]
            V_START[startTournament-view.fxml]
            V_ROUND[roundTournament-view.fxml]
            
            C_SELECT[selectTournamentController]
            C_CREATE[createTournamentController]
            C_START[startTournamentController]
            C_ROUND[roundTournamentController]
        end
        
        subgraph Styling ["CSS Architecture"]
            CSS_GLOBAL[global-styles.css]
            CSS_VIEWS[View-Specific Styles]
        end
    end

    subgraph Service_Layer ["Service & Business Logic"]
        MGR[ServiceManager.java]
        
        subgraph Services ["Application Services"]
            SVC_T[TournamentService]
            SVC_P[PlayerService]
            SVC_TM[TeamService]
            SVC_M[MatchService]
            SVC_MKR[MakerService]
        end
    end

    subgraph Core_Layer ["Core Tournament Logic"]
        CORE_TM[TeamMaker.java]
        CORE_MM[MatchMaker.java]
        CORE_SH[TeamShuffler.java]
        CORE_WE[WinnerExtractor.java]
    end

    subgraph Persistence_Layer ["Data Access Layer (DAO & JDBC)"]
        direction TB
        subgraph Repositories ["Repository Abstraction"]
            REPO_T[TournamentRepository]
            REPO_P[PlayerRepository]
            REPO_TM[TeamRepository]
            REPO_M[MatchRepository]
        end
        
        subgraph DAOs ["SQLite DAOs"]
            DAO_T[SQLiteTournamentDao]
            DAO_P[SQLitePlayerDao]
            DAO_TM[SQLiteTeamDao]
            DAO_M[SQLiteMatchDao]
        end
        
        DB_CONN[SQLiteDB.java]
        SQL_R[SQLReader.java]
        SQLITE[(calcetto.db)]
    end

    subgraph Domain_Layer ["Domain Models (POJOs)"]
        MODEL_T[Tournament]
        MODEL_P[Player]
        MODEL_TM[Team]
        MODEL_M[Match]
    end

    APP --> NAV
    NAV --> V_SELECT & V_CREATE & V_START & V_ROUND
    
    V_SELECT --- C_SELECT
    V_CREATE --- C_CREATE
    V_START --- C_START
    V_ROUND --- C_ROUND
    
    C_SELECT & C_CREATE & C_START & C_ROUND --> MGR
    
    MGR --> SVC_T & SVC_P & SVC_TM & SVC_M & SVC_MKR
    
    SVC_MKR --> CORE_TM & CORE_MM
    CORE_MM --> CORE_SH & CORE_WE
    
    SVC_T --> REPO_T
    SVC_P --> REPO_P
    SVC_TM --> REPO_TM
    SVC_M --> REPO_M
    
    REPO_T --> DAO_T
    REPO_P --> DAO_P
    REPO_TM --> DAO_TM
    REPO_M --> DAO_M
    
    DAO_T & DAO_P & DAO_TM & DAO_M --> DB_CONN
    DB_CONN --> SQL_R
    SQL_R --> SQLITE

    DAO_T -. maps to .-> MODEL_T
    DAO_P -. maps to .-> MODEL_P
    class UI_Layer,Service_Layer,Core_Layer,Persistence_Layer,Domain_Layer layer
    class APP,NAV,V_SELECT,V_CREATE,V_START,V_ROUND,C_SELECT,C_CREATE,C_START,C_ROUND,CSS_GLOBAL ui_bg
    class MGR,SVC_T,SVC_P,SVC_TM,SVC_M,SVC_MKR service_bg
    class REPO_T,REPO_P,REPO_TM,REPO_M,DAO_T,DAO_P,DAO_TM,DAO_M,DB_CONN,SQL_R dao_bg
    class CORE_TM,CORE_MM,CORE_SH,CORE_WE core
    class SQLITE db_node
    class MODEL_T,MODEL_P,MODEL_TM,MODEL_M model
```

## DB Notation

```mermaid
erDiagram
    TOURNAMENT ||--o{ PLAYER : "assigns"
    TOURNAMENT ||--o{ MATCH : "schedules"
    TEAM ||--o{ PLAYER : "kader"
    TEAM ||--o{ TEAM_MATCH : "participates"
    MATCH ||--o{ TEAM_MATCH : "includes"

    TOURNAMENT {
        int tid PK
        string tournament_name
        string start_date
        int duration
        int pre_round
        int current_round
        int max_team_size
    }

    TEAM {
        int tid PK
        string team_name
    }

    PLAYER {
        int pid PK
        string pname
        string pemail
        int tid FK "Link to TEAM"
        int trid FK "Link to TOURNAMENT"
    }

    MATCH {
        int mid PK
        int round
        int tid FK "Link to TOURNAMENT"
    }

    TEAM_MATCH {
        int team_id PK, FK "Link to TEAM"
        int match_id PK, FK "Link to MATCH"
        float points
    }
```

## Installation

### Prerequisites
- JDK 21 or higher
- Maven 3.8+

### Steps
1. **Clone the repository**:
   ```bash
   git clone https://github.com/alex1009-system32/CalcettoManagementSystem.git
   cd CalcettoManagementSystem
   ```

2. **Build the project**:
   ```bash
   mvn clean install
   ```

3. **Run the application**:
   ```bash
   mvn javafx:run
   ```

## Usage

1. **Start Application**: Launch the app via the main class `App.java`.
2. **Create Tournament**: Navigate to "Create Tournament", enter the name, start date, and max team size.
3. **Add Players**: Register players for the active tournament.
4. **Generate Teams**: Use the automated shuffler to create balanced teams.
5. **Manage Matches**: Start the tournament to generate the first round of matches and record results.
