CREATE DATABASE Dictionary;
DROP database dictionary;
-- ----------------------------
-- Table structure for Audio_US
-- ----------------------------

TRUNCATE TABLE definition;
SET foreign_key_checks = 0;

-- Thực hiện TRUNCATE TABLE
TRUNCATE TABLE English;

-- Bật lại ràng buộc khóa ngoại
SET foreign_key_checks = 1;

-- Tắt ràng buộc khóa ngoại
SET foreign_key_checks = 0;

-- Thực hiện TRUNCATE TABLE
TRUNCATE TABLE Audio_US;

-- Bật lại ràng buộc khóa ngoại
SET foreign_key_checks = 1;


DELETE FROM audio_us WHERE id > 0;

DROP TABLE IF EXISTS `Audio_US`;
CREATE TABLE `Audio_US` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `audio_source` TEXT,
                            PRIMARY KEY (`id`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;
-- ----------------------------
-- Records of Audio_US
-- ----------------------------


-- ----------------------------
-- Table structure for Audio_UK
-- ----------------------------
DROP TABLE IF EXISTS `Audio_UK`;
CREATE TABLE `Audio_UK` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `audio_source` TEXT,
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;
-- ----------------------------
-- Records of Audio_UK
-- ----------------------------



-- ----------------------------
-- Table structure for English`
-- ----------------------------
DROP TABLE IF EXISTS `English`;
CREATE TABLE `English` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `audio_id_us` bigint,
                           FOREIGN KEY (`audio_id_us`) REFERENCES Audio_US(`id`),
                           `audio_id_uk` bigint,
                           FOREIGN KEY (`audio_id_uk`) REFERENCES Audio_UK(`id`),
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;
-- ----------------------------
-- Records of English
-- ----------------------------


-- ----------------------------
-- Table structure for Audio_VN
-- ----------------------------
-- DROP TABLE IF EXISTS `Audio_VN`;
-- CREATE TABLE `Audio_VN` (
--     `id` bigint NOT NULL AUTO_INCREMENT,
--     `audio_source` TEXT,
--     PRIMARY KEY (`id`) USING BTREE
-- ) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;
-- ----------------------------
-- Records of Audio_VN
-- ----------------------------

-- ----------------------------
-- Table structure for VietNamese
-- ----------------------------
DROP TABLE IF EXISTS `VietNamese`;
CREATE TABLE `VietNamese` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `name` VARCHAR(255),
    -- `audio_id_vn` bigint,
--     FOREIGN KEY (`audio_id_vn`) REFERENCES Audio_VN(`id`),
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;
-- ----------------------------
-- Records of VietNamese
-- ----------------------------


-- ----------------------------
-- Table structure for Definition
-- ----------------------------
DROP TABLE IF EXISTS `Definition`;
CREATE TABLE `Definition` (
                              `english_id` bigint NOT NULL AUTO_INCREMENT ,
                              FOREIGN KEY (`english_id`) REFERENCES English(`id`),
                              `vietnamese_id` bigint NOT NULL,
                              `description` varchar(5000),
                              FOREIGN KEY (`vietnamese_id`) REFERENCES VietNamese(`id`),
                              PRIMARY KEY (`english_id`, `vietnamese_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;
-- ----------------------------
-- Records of Definition
-- ----------------------------


-- ----------------------------
-- Table structure for available_tags
-- ----------------------------
DROP TABLE IF EXISTS `brand_tags`;
create table `brand_tags`(
                             `id` int,
                             `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             PRIMARY KEY (`tag_name`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;
-- ----------------------------
-- Records of available_tags
-- ----------------------------
INSERT INTO `brand_tags` VALUES (1,'A2');
INSERT INTO `brand_tags` VALUES (2,'A1');
INSERT INTO `brand_tags` VALUES (3,'B2');
INSERT INTO `brand_tags` VALUES (4,'B1');
INSERT INTO `brand_tags` VALUES (5,'C2');
INSERT INTO `brand_tags` VALUES (6,'C1');


DROP TABLE IF EXISTS `Part_Of_Speech_tags`;
CREATE TABLE `Part_Of_Speech_tags`(
                                      `id`int ,
                                      `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                      PRIMARY KEY (`tag_name`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

INSERT INTO `Part_Of_Speech_tags` VALUES (1,'INDEFINITE_ARTICLE');
INSERT INTO `Part_Of_Speech_tags` VALUES (2,'PREPOSITION');
INSERT INTO `Part_Of_Speech_tags` VALUES (3,'VERBS');
INSERT INTO `Part_Of_Speech_tags` VALUES (4,'NOUN');
INSERT INTO `Part_Of_Speech_tags` VALUES (5,'ADJECTIVE');
INSERT INTO `Part_Of_Speech_tags` VALUES (6,'ADVERB');


-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags`(
                       `id` bigint NOT NULL AUTO_INCREMENT,
                       `english_id` bigint NOT NULL,
                       `brand_tag_name` varchar(255),
                       `Part_Of_Speech_tag_name` varchar(255) ,
                       FOREIGN KEY (`english_id`) REFERENCES english(id),
                       FOREIGN KEY (`brand_tag_name`) REFERENCES brand_tags(`tag_name`),
                       FOREIGN KEY (`Part_Of_Speech_tag_name`) REFERENCES Part_Of_Speech_tags(`tag_name`),
                       PRIMARY KEY (`id`) USING BTREE
);
-- ----------------------------
-- Records of tags
-- ----------------------------










