-- Book Table
CREATE TABLE book (
    id BINARY(16) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL,
    is_available BOOLEAN NOT NULL,
    total_copies INT NOT NULL,
    available_copies INT NOT NULL,
    deleted BOOLEAN NOT NULL
);

-- Borrower Table
CREATE TABLE borrower (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    membership_type VARCHAR(20) NOT NULL,
    max_borrow_limit INT NOT NULL
);

-- Borrow Record Table
CREATE TABLE borrow_record (
    id BINARY(16) PRIMARY KEY,
    book_id BINARY(16) NOT NULL,
    borrower_id BINARY(16) NOT NULL,
    borrow_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE,
    fine_amount DECIMAL(10,2),

    CONSTRAINT fk_borrowrecord_book 
        FOREIGN KEY (book_id) REFERENCES book(id),

    CONSTRAINT fk_borrowrecord_borrower 
        FOREIGN KEY (borrower_id) REFERENCES borrower(id)
);

-- Fine Policy Table
CREATE TABLE fine_policy (
    id BINARY(16) PRIMARY KEY,
    category VARCHAR(50) NOT NULL,
    fine_per_day DECIMAL(10,2) NOT NULL
);