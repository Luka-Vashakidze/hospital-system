SELECT
    h.name AS hospital_name,
    d.name AS department_name,
    r.number AS department_room,
    doc.full_name AS doctor_name,
    p.full_name AS patient_name,
    appt.date_time AS appointment_time,
    tr.diagnosis,
    pr.medication,
    ins.policy_number,
    it.name AS insurance_type,
    adm.admitted_date,
    adm.discharge_date
FROM hospitals h
JOIN departments d ON d.hospital_id = h.id
LEFT JOIN rooms r ON r.department_id = d.id
LEFT JOIN doctors doc ON doc.department_id = d.id
LEFT JOIN appointments appt ON appt.department_id = d.id AND appt.doctor_id = doc.id
LEFT JOIN patients p ON appt.patient_id = p.id
LEFT JOIN insurances ins ON p.insurance_id = ins.id
LEFT JOIN insurance_types it ON ins.insurance_type_id = it.id
LEFT JOIN treatments tr ON tr.patient_id = p.id AND tr.doctor_id = doc.id
LEFT JOIN prescriptions pr ON pr.treatment_id = tr.id
LEFT JOIN admissions adm ON adm.patient_id = p.id AND adm.room_id = r.id;

---------------
SELECT
    d.name AS department_name,
    r.number AS room_number,
    r.is_available
FROM departments d
LEFT JOIN rooms r ON r.department_id = d.id;

SELECT
    doc.full_name AS doctor_name,
    appt.id AS appointment_id,
    appt.date_time
FROM appointments appt
RIGHT JOIN doctors doc ON appt.doctor_id = doc.id;

SELECT
    p.full_name AS patient_name,
    it.name AS insurance_type,
    ins.policy_number
FROM patients p
INNER JOIN insurances ins ON p.insurance_id = ins.id
INNER JOIN insurance_types it ON ins.insurance_type_id = it.id;

SELECT
    appt.id AS appointment_id,
    h.name AS hospital_name,
    appt.bill_amount
FROM appointments appt
INNER JOIN departments d ON appt.department_id = d.id
INNER JOIN hospitals h ON d.hospital_id = h.id;

SELECT
    p.full_name AS patient_name,
    appt.id AS appointment_id,
    appt.status
FROM patients p
LEFT JOIN appointments appt ON appt.patient_id = p.id
UNION
SELECT
    p.full_name AS patient_name,
    appt.id AS appointment_id,
    appt.status
FROM patients p
RIGHT JOIN appointments appt ON appt.patient_id = p.id
WHERE p.id IS NULL;

SELECT gender, COUNT(*) AS patient_count
FROM patients
GROUP BY gender;

SELECT status, COUNT(*) AS appointment_count
FROM appointments
GROUP BY status;

SELECT d.name AS department_name, AVG(appt.bill_amount) AS avg_bill
FROM departments d
INNER JOIN appointments appt ON appt.department_id = d.id
GROUP BY d.id, d.name;

SELECT doc.full_name AS doctor_name, SUM(appt.bill_amount) AS total_billed
FROM doctors doc
INNER JOIN appointments appt ON appt.doctor_id = doc.id
GROUP BY doc.id, doc.full_name;

SELECT d.name AS department_name, COUNT(r.id) AS room_count
FROM departments d
LEFT JOIN rooms r ON r.department_id = d.id
GROUP BY d.id, d.name;

SELECT it.name AS insurance_type, AVG(it.coverage_percentage) AS avg_coverage
FROM insurance_types it
GROUP BY it.id, it.name;

SELECT pr.medication, COUNT(pr.id) AS prescription_count
FROM prescriptions pr
GROUP BY pr.medication;

SELECT d.name AS department_name, COUNT(doc.id) AS doctor_count
FROM departments d
LEFT JOIN doctors doc ON doc.department_id = d.id
GROUP BY d.id, d.name
HAVING COUNT(doc.id) >= 2;

SELECT doc.full_name AS doctor_name, SUM(appt.bill_amount) AS total_bills
FROM doctors doc
LEFT JOIN appointments appt ON appt.doctor_id = doc.id
GROUP BY doc.id, doc.full_name
HAVING SUM(appt.bill_amount) > 400;

SELECT p.full_name AS patient_name, COUNT(appt.id) AS appointment_count
FROM patients p
LEFT JOIN appointments appt ON appt.patient_id = p.id
GROUP BY p.id, p.full_name
HAVING COUNT(appt.id) >= 1;

SELECT it.name AS insurance_type, COUNT(ins.id) AS policy_count
FROM insurance_types it
LEFT JOIN insurances ins ON ins.insurance_type_id = it.id
GROUP BY it.id, it.name
HAVING COUNT(ins.id) >= 1;

SELECT d.name AS department_name, COUNT(r.id) AS room_count
FROM departments d
LEFT JOIN rooms r ON r.department_id = d.id
GROUP BY d.id, d.name
HAVING COUNT(r.id) > 1;

SELECT doc.full_name AS doctor_name, COUNT(tr.id) AS treatments_handled
FROM doctors doc
LEFT JOIN treatments tr ON tr.doctor_id = doc.id
GROUP BY doc.id, doc.full_name
HAVING COUNT(tr.id) >= 1;

SELECT p.full_name AS patient_name, COUNT(pr.id) AS prescription_count
FROM patients p
LEFT JOIN treatments tr ON tr.patient_id = p.id
LEFT JOIN prescriptions pr ON pr.treatment_id = tr.id
GROUP BY p.id, p.full_name
HAVING COUNT(pr.id) >= 1;