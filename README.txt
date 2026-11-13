ATM Machine System (fixed)

SETUP
1. Install MySQL, then run:   mysql -u root -p < schema.sql
2. Open src/conn.java and put YOUR MySQL password in PASSWORD
   (or set the DB_PASSWORD environment variable).
3. Run run.bat (Windows) or ./run.sh (Mac/Linux).

USING IT
- Click "Signup", fill the 3 pages. The card number and PIN are shown at the end,
  then you are taken back to the login screen.
- Log in with that card number and PIN.
- The ATM picture shows the menu ON its screen, next to the 8 side buttons.
  Click a side button, click the label on the screen, or use the keypad / keyboard:
    keypad 0-9 = type amount or PIN     green = Enter
    yellow     = Clear                  red   = Cancel / back to menu
  Options: Deposit, Cash Withdrawal, Fast Cash, Mini Statement (last 10),
  Balance Enquiry, Pin Change, Exit (back to login).

INTELLIJ
Mark the "src" folder as Sources Root (the icons folder is inside it), add both
.jar files as libraries, and run bank.management.system.Login.

IMAGES
src/icons/atm.png is the ATM background (it still has the VectorStock bar at the
bottom, because that is part of the picture you gave me). To get a clean look, replace it
with a licensed/downloaded copy of the same picture, same file name, 1000 px wide.
src/icons/logo.jpg is a plain placeholder for the login logo.
