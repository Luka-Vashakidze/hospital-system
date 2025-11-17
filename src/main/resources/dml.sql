USE mydb;

INSERT INTO hospitals (id, name, address, phone, email, created_at, updated_at)
VALUES
    (1, 'Gagua hospital', 'tbilisi str', '5551000', 'gagua@clinic.com', '2005-01-15', NOW()),
    (2, 'Lakeside Clinic', '45 Lake Rd', '555-2000', 'info@lakeside.com', '2010-04-10', NOW());

INSERT INTO departments (id, hospital_id, code, name, description)
VALUES
    (1, 1, 'CARD', 'Cardiology', 'Heart and vascular care'),
    (2, 1, 'NEUR', 'Neurology', 'Brain and nerve care');

INSERT INTO rooms (id, department_id, number, floor, capacity, room_type, is_available)
VALUES
    (1, 1, 'C101', 1, 2, 'ICU', 1),
    (2, 2, 'N201', 2, 1, 'Consultation', 1);

INSERT INTO doctors (id, department_id, full_name, specialty, phone, email, hired_date)
VALUES
    (1, 1, 'Alice Carter', 'Cardiologist', '555-3001', 'alice.carter@citycare.com', '2018-03-01'),
    (2, 2, 'Boris Lane', 'Neurologist', '555-3002', 'boris.lane@citycare.com', '2016-07-12');

INSERT INTO insurance_types (id, name, coverage_percentage, description)
VALUES
    (1, 'Basic', 50.00, 'Covers essential services'),
    (2, 'Premium', 80.00, 'Extensive coverage plan');

INSERT INTO insurances (id, insurance_type_id, policy_number, insured, expiry_date)
VALUES
    (1, 1, 'POL-1001', 1, '2026-12-31'),
    (2, 2, 'POL-2001', 1, '2027-06-30');

INSERT INTO patients (id, full_name, birth_date, gender, phone, insurance_id)
VALUES
    (1, 'Nino Smith', '1985-02-10', 'F', '555-4001', 1),
    (2, 'George King', '1990-11-22', 'M', '555-4002', 2);

INSERT INTO appointments (id, patient_id, department_id, date_time, purpose, status, bill_amount, doctor_id)
VALUES
    (1, 1, 1, '2025-11-01 09:00:00', 'Follow-up', 'Scheduled', 250.00, 1),
    (2, 2, 2, '2025-11-02 14:30:00', 'MRI Review', 'Scheduled', 480.00, 2);

INSERT INTO treatments (id, patient_id, doctor_id, diagnosis, treatment_start_date, treatment_end_date, status)
VALUES
    (1, 1, 1, 'Hypertension', '2025-10-15', NULL, 'Active'),
    (2, 2, 2, 'Headache', '2025-09-20', '2025-10-05', 'Completed');

INSERT INTO prescriptions (id, treatment_id, medication, dosage_mg, frequency, duration_days, instructions, prescribed_date)
VALUES
    (1, 1, 'some medication', 10, 'Once daily', 30, 'Take each morning', '2025-10-16'),
    (2, 2, 'some other medication', 50, 'As needed', 10, 'Use when needed', '2025-09-21');

UPDATE hospitals
SET phone = '555-1200'
WHERE id = 1;

UPDATE departments
SET description = 'Some neurological diagnostics'
WHERE id = 2;

UPDATE doctors
SET specialty = 'Some Cardiologist'
WHERE id = 1;

UPDATE rooms
SET is_available = 0
WHERE id = 1;

UPDATE insurance_types
SET coverage_percentage = 88.00
WHERE id = 2;

UPDATE insurances
SET expiry_date = '2028-01-31'
WHERE id = 2;

UPDATE patients
SET phone = '555-4010'
WHERE id = 1;

UPDATE appointments
SET status = 'Completed', bill_amount = 275.00
WHERE id = 1;

UPDATE treatments
SET treatment_end_date = '2025-11-10', status = 'Completed'
WHERE id = 1;

UPDATE prescriptions
SET dosage_mg = 15
WHERE id = 1;

DELETE FROM prescriptions
WHERE id IN (1, 2);

DELETE FROM treatments
WHERE id IN (1, 2);

DELETE FROM appointments
WHERE id IN (1, 2);

DELETE FROM rooms
WHERE id IN (1, 2);

DELETE FROM doctors
WHERE id IN (1, 2);

DELETE FROM patients
WHERE id IN (1, 2);

DELETE FROM insurances
WHERE id IN (1, 2);

DELETE FROM insurance_types
WHERE id IN (1, 2);

DELETE FROM departments
WHERE id IN (1, 2);

DELETE FROM hospitals
WHERE id IN (1, 2);

ALTER TABLE hospitals
ADD COLUMN priority_level INT NULL;

ALTER TABLE departments
ADD COLUMN floor INT NULL;

ALTER TABLE doctors
ADD COLUMN salary DECIMAL(10,2) NULL;

ALTER TABLE patients
ADD COLUMN notes VARCHAR(255) NULL;

ALTER TABLE insurances
ADD COLUMN comments VARCHAR(255) NULL;