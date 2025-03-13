CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    user_roles VARCHAR(50) NOT NULL,
    CONSTRAINT fk_user_roles_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Step 2: Insert default value 'PUBLIC' for existing users
INSERT INTO user_roles (user_id, user_roles)
SELECT id, 'USER' FROM users;

-- Step 3: Optional - Add an index for faster lookups
CREATE INDEX idx_user_roles ON user_roles(user_id);