# ATM Machine System

A desktop ATM simulator built with **Java Swing** and **MySQL**. Customers can register for a new account, log in with a card number and PIN, and use a graphical ATM, with side buttons, an on-screen menu and a clickable keypad, to deposit, withdraw and check their account.

## Features

- **New account signup** in three steps:
  1. Personal details (name, father's name, date of birth, gender, email, address, etc.)
  2. Additional details (religion, category, income, education, occupation, PAN, Aadhaar, senior citizen, existing account)
  3. Account type and services (Saving / Fixed Deposit / Current / Recurring Deposit; ATM Card, Internet Banking, Mobile Banking, Email & SMS Alert, Cheque Book, E-Statement)
- **Auto-generated card number and 4-digit PIN**, shown at the end of signup
- **Input validation** for email, pincode, PAN and Aadhaar
- **Login** with card number and PIN
- **ATM screen** with a picture of the machine, 8 side buttons and a working keypad (mouse or keyboard)
- **Transactions**
  - Deposit
  - Cash Withdrawal
  - Fast Cash (Rs. 100 / 500 / 1000 / 2000 / 5000 / 10000)
  - Mini Statement (last 10 transactions)
  - Balance Enquiry
  - PIN Change (current PIN, new PIN, confirm)
- **Safe withdrawals**: balance is checked inside a database transaction with row locking, so concurrent withdrawals can't overdraw an account
- Balance is calculated from the transaction history (deposits minus withdrawals), so there is no separate balance column to get out of sync

## Tech Stack

| Part | Technology |
|------|------------|
| Language | Java |
| UI | Swing / AWT |
| Database | MySQL 8 |
| DB driver | `mysql-connector-java-8.0.28.jar` (included) |
| Date picker | `jcalendar-tz-1.3.3-4.jar` (included) |

## Project Structure

```
ATM_Fixed/
├── src/
│   ├── Login.java          # Login screen (entry point)
│   ├── Signup.java         # Signup page 1: personal details
│   ├── SignupTwo.java      # Signup page 2: additional details
│   ├── SignupThree.java    # Signup page 3: account type, services, card + PIN generation
│   ├── Transactions.java   # ATM screen, keypad and menu logic
│   ├── Bank.java           # Account operations: balance, deposit, withdraw, statement, PIN change
│   ├── conn.java           # JDBC connection helper
│   ├── Util.java           # Image loading and message helpers
│   └── icons/              # atm.png (ATM background), logo.jpg (login logo)
├── schema.sql              # Database and table definitions
├── run.bat                 # Build and run on Windows
├── run.sh                  # Build and run on Mac/Linux
├── mysql-connector-java-8.0.28.jar
└── jcalendar-tz-1.3.3-4.jar
```

All classes are in the package `bank.management.system`.

## Database

`schema.sql` creates the `bankmanagementsystem` database with these tables:

| Table | Purpose |
|-------|---------|
| `signup` | Personal details (page 1) |
| `signuptwo` | Additional details (page 2) |
| `signupthree` | Account type, card number, PIN, services |
| `login` | Card number and PIN used to authenticate |
| `bank` | Every transaction: card number, date, type (`Deposit` / `Withdrawal`), amount |

## Getting Started

### Prerequisites

- JDK 8 or newer
- MySQL Server running locally

### 1. Create the database

```bash
mysql -u root -p < schema.sql
```

### 2. Configure the connection

`src/conn.java` reads its settings from environment variables, falling back to defaults:

| Variable | Default |
|----------|---------|
| `DB_URL` | `jdbc:mysql://localhost:3306/bankmanagementsystem` |
| `DB_USER` | `root` |
| `DB_PASSWORD` | set in `conn.java` |

Set your own MySQL password, either by exporting `DB_PASSWORD` or by editing `PASSWORD` in `src/conn.java`:

```bash
# Mac/Linux
export DB_PASSWORD=your_password

# Windows (cmd)
set DB_PASSWORD=your_password
```

### 3. Build and run

```bash
# Windows
run.bat

# Mac/Linux
chmod +x run.sh
./run.sh
```

Both scripts compile `src/*.java` into `out/` and launch `bank.management.system.Login`.

### Running from IntelliJ IDEA

1. Mark the `src` folder as **Sources Root** (the `icons` folder lives inside it).
2. Add both `.jar` files as project libraries.
3. Run `bank.management.system.Login`.

## How to Use

1. On the login screen, click **Signup** and complete the three pages.
2. Note the **card number and PIN** shown at the end, then you're returned to the login screen.
3. Sign in with that card number and PIN.
4. On the ATM screen, pick an option by clicking a side button, clicking the label on the screen, or using the keypad.

| Key | Action |
|-----|--------|
| `0`-`9` | Type an amount or PIN |
| Green | Enter |
| Yellow | Clear |
| Red | Cancel / back to menu |

## Notes and Known Limitations

- This is a learning/demo project, **not production banking software**.
- PINs are stored in plain text in the database. Hash them (e.g. with bcrypt or PBKDF2) before any real use.
- Card numbers and PINs are randomly generated and are not checked for uniqueness at creation time.
- Amounts are whole rupees only.
- `src/icons/atm.png` still carries a stock-image watermark bar at the bottom. Replace it with a licensed copy (same file name, 1000 px wide) for a clean look.
- Don't commit your real database password. Use the `DB_PASSWORD` environment variable instead.
