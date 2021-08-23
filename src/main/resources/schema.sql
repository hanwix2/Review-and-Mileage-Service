CREATE TABLE place
(
    `id`    INT UNSIGNED    NOT NULL    AUTO_INCREMENT,
    `name`  VARCHAR(45)     NULL,
     PRIMARY KEY (id)
);

CREATE TABLE review
(
    `id`           INT UNSIGNED    NOT NULL    AUTO_INCREMENT,
    `place_id`     INT UNSIGNED    NOT NULL,
    `user_id`      INT UNSIGNED    NOT NULL,
    `content`      VARCHAR(200)    NULL,
    `create_time`  DATETIME        NULL,
    `update_time`  DATETIME        NULL,
     PRIMARY KEY (id),
     FOREIGN KEY (place_id) REFERENCES place (id)
     ON DELETE CASCADE
);

CREATE INDEX IX_review ON review(place_id, user_id);

CREATE TABLE user
(
    `id`     INT UNSIGNED    NOT NULL    AUTO_INCREMENT COMMENT 'id',
    `email`  VARCHAR(45)     NOT NULL    COMMENT 'email',
    `name`   VARCHAR(45)     NOT NULL    COMMENT 'name',
     PRIMARY KEY (id)
);

CREATE TABLE mileage_record
(
    `id`        INT UNSIGNED    NOT NULL    AUTO_INCREMENT,
    `user_id`   INT UNSIGNED    NOT NULL,
    `review_id` INT UNSIGNED    NOT NULL,
    `content`   VARCHAR(100)    NOT NULL,
    `amount`    INT             NOT NULL,
    `time`      DATETIME        NOT NULL,
     PRIMARY KEY (id),
     FOREIGN KEY (user_id) REFERENCES user (id)
     ON DELETE CASCADE
);

CREATE INDEX IX_mileage_record ON mileage_record(user_id, review_id);

CREATE TABLE review_image
(
    `id`          INT UNSIGNED    NOT NULL    AUTO_INCREMENT,
    `review_id`   INT UNSIGNED    NOT NULL,
    `image_path`  VARCHAR(200)    NULL        DEFAULT 'image',
     PRIMARY KEY (id),
     FOREIGN KEY (review_id) REFERENCES review (id)
     ON DELETE CASCADE
);
