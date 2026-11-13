CREATE DATABASE IF NOT EXISTS bankmanagementsystem;
USE bankmanagementsystem;

CREATE TABLE IF NOT EXISTS signup (
  formno VARCHAR(20) PRIMARY KEY, name VARCHAR(100), father_name VARCHAR(100), dob VARCHAR(30),
  gender VARCHAR(20), email VARCHAR(100), address VARCHAR(200), city VARCHAR(60),
  state VARCHAR(60), pincode VARCHAR(20), marital VARCHAR(20)
);
CREATE TABLE IF NOT EXISTS signuptwo (
  formno VARCHAR(20) PRIMARY KEY, religion VARCHAR(40), category VARCHAR(40), income VARCHAR(40),
  education VARCHAR(40), occupation VARCHAR(40), pan VARCHAR(20), aadhar VARCHAR(20),
  seniorcitizen VARCHAR(5), existing_account VARCHAR(5)
);
CREATE TABLE IF NOT EXISTS signupthree (
  formno VARCHAR(20) PRIMARY KEY, account_type VARCHAR(40), cardnumber VARCHAR(20),
  pin VARCHAR(10), facility VARCHAR(200)
);
CREATE TABLE IF NOT EXISTS login (
  formno VARCHAR(20) PRIMARY KEY, cardnumber VARCHAR(20) UNIQUE, pin VARCHAR(10)
);
CREATE TABLE IF NOT EXISTS bank (
  id INT AUTO_INCREMENT PRIMARY KEY,
  cardnumber VARCHAR(20) NOT NULL,
  txn_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  type VARCHAR(20) NOT NULL,          -- 'Deposit' or 'Withdrawal'
  amount BIGINT NOT NULL,
  INDEX (cardnumber)
);
