-- Insert Users
-- Passwords are pre-hashed. The raw password for both is: password
INSERT INTO users (name, email, password, role, status)
VALUES ('System Admin', 'admin@finflow.io', '$2a$12$t0ooZS2lJbNv6MJRtvxXR.d1Mt0Mrb6sUei2lqNB4GPaWGgQoXaOK', 'ADMIN', 'ACTIVE');

INSERT INTO users (name, email, password, role, status)
VALUES ('Test Viewer', 'viewer@finflow.io', '$2a$12$t0ooZS2lJbNv6MJRtvxXR.d1Mt0Mrb6sUei2lqNB4GPaWGgQoXaOK', 'VIEWER', 'ACTIVE');


-- Insert Financial Records
-- We use a subquery to grab the Admin's ID automatically so the foreign key matches
INSERT INTO financial_records (amount, type, category, date, description, user_id)
VALUES (5000.00, 'INCOME', 'Salary', '2026-03-20', 'Monthly Salary', (SELECT id FROM users WHERE email = 'admin@finflow.io'));

INSERT INTO financial_records (amount, type, category, date, description, user_id)
VALUES (1200.00, 'INCOME', 'Freelance', '2026-03-30', 'Web Development Project', (SELECT id FROM users WHERE email = 'admin@finflow.io'));

INSERT INTO financial_records (amount, type, category, date, description, user_id)
VALUES (1500.00, 'EXPENSE', 'Rent', '2026-03-25', 'April Apartment Rent', (SELECT id FROM users WHERE email = 'admin@finflow.io'));

INSERT INTO financial_records (amount, type, category, date, description, user_id)
VALUES (45.00, 'EXPENSE', 'Software', '2026-03-28', 'Cloud Hosting Subscription', (SELECT id FROM users WHERE email = 'admin@finflow.io'));

INSERT INTO financial_records (amount, type, category, date, description, user_id)
VALUES (300.00, 'EXPENSE', 'Travel', '2026-04-03', 'Flight Tickets', (SELECT id FROM users WHERE email = 'admin@finflow.io'));

INSERT INTO financial_records (amount, type, category, date, description, user_id)
VALUES (120.50, 'EXPENSE', 'Groceries', '2026-04-04', 'Supermarket Run', (SELECT id FROM users WHERE email = 'admin@finflow.io'));