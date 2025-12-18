# Finance Manager (Java Swing)

A Java Swing desktop application for tracking income and expenses. Built with a clean **MVC architecture** and multiple design patterns (Strategy, Observer, DTO) as part of a COP 4331 group project.

## Key Features
- Add income and expenses (with validation to prevent spending beyond balance)
- Edit and delete transactions
- Monthly summary (total income, total expenses, net)
- Persistence strategies:
  - CSV (default)
  - Serialized storage (alternative)
- Report generation strategies:
  - CSV report
  - Text report
- Table-based UI with dialogs
- Automatic UI refresh via a custom Observer mechanism

## Architecture
### Model
- `FinanceModel`
- `Transaction`, `TransactionType`, `Category`
- `FilterCriteria`, `SummaryDTO`

### View
- `MainFrame`
- `TransactionTablePanel`, `TransactionTableModel`
- `TransactionFormDialog`
- `SummaryDialog`

### Controller
- `AddIncomeController`
- `AddExpenseController`
- `EditTransactionController`
- `DeleteTransactionController`
- `ViewSummaryController`

### Persistence (Strategy Pattern)
- `PersistenceStrategy`
- `CsvPersistence` (default)
- `SerializedPersistence` (alternative)

### Reports (Strategy Pattern)
- `ReportStrategy`
- `CsvReportStrategy`
- `TextReportStrategy`

## Build & Run
### Compile
```bash
javac -d bin ^
  src/cop4331/financemanager/AppMain.java ^
  src/cop4331/financemanager/model/*.java ^
  src/cop4331/financemanager/view/*.java ^
  src/cop4331/financemanager/controller/*.java ^
  src/cop4331/financemanager/persistence/*.java ^
  src/cop4331/financemanager/report/*.java
