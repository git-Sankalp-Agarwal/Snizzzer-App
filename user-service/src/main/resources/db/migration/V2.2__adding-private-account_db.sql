-- Add the new column
ALTER TABLE users
ADD COLUMN account_type VARCHAR(50) NOT NULL DEFAULT 'PUBLIC';

-- Drop the old columns
ALTER TABLE users
DROP COLUMN is_private_account;

ALTER TABLE users
DROP COLUMN private_account;