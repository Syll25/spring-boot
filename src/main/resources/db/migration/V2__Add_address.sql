CREATE TABLE address (
    id SERIAL PRIMARY KEY,
    street VARCHAR(255),
    city VARCHAR(255),
    zip_code VARCHAR(255)
);

ALTER TABLE users
ADD COLUMN address_id BIGINT,
ADD CONSTRAINT fk_address
FOREIGN KEY (address_id)
REFERENCES address(id);
